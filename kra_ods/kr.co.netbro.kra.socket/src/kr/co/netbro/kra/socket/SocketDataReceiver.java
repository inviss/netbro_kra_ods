package kr.co.netbro.kra.socket;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import kr.co.netbro.kra.model.RaceInfo;
import kr.co.netbro.kra.socket.maker.Packet;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
@Creatable
public class SocketDataReceiver {
	final Logger logger = LoggerFactory.getLogger(getClass());

	private ServerSocket server;
	private Socket socket = null;

	private ExecutorService serverThread = Executors.newSingleThreadExecutor();
	private ExecutorService clientThread = Executors.newSingleThreadExecutor();
	
	@Inject @Preference(nodePath="kra.config.socket", value="port") 
	private Integer port;
	@Inject @Preference(nodePath="kra.config.socket", value="timeout") 
	private Integer timeout;
	@Inject @Preference(nodePath="kra.config.socket", value="capture") 
	private boolean capture;
	@Inject
	EventDataReceiver dataReceiver;
	
	@Inject
	private IEventBroker eventBroker;
	
	private FileOutputStream fos = null;
	
	@PostConstruct
	public void serverConnect() {
		port = (port == null || port == 0) ? 8000 : port;
		timeout = (timeout == null || timeout == 0) ? 25000 : timeout;
		if(!checkServerPort(port)) {
			try {
				server = new ServerSocket(port);
				serverThread.execute(new SocketReceiver(server));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class SocketReceiver implements Runnable {
		ServerSocket server = null;

		public SocketReceiver(ServerSocket server) {
			this.server = server;
		}

		@Override
		public void run() {
			while(true) {
				if(server == null || server.isClosed()) break;
				
				if(logger.isInfoEnabled()) {
					logger.info("port "+port+" receiving stream...and the waiting time is "+timeout+" seconds.");
				}
				try {
					socket = server.accept();
				} catch (SocketException e) {
					logger.warn("Server Socket is Closing..", e.getMessage());
				} catch (Exception e) {
					logger.error("Server Socket Error", e);
					try {
						Thread.sleep(1000L);
					} catch (Exception e2) {}
					continue;
				}

				if(socket == null || socket.isClosed()) break;
				clientThread.execute(new SocketParseData(socket));
			}
		}

	}

	public class SocketParseData implements Runnable {

		private BufferedInputStream bis = null;
		byte[] buf = new byte[65536];
		private long totalBytes = 0L;
		private Socket socket;

		public SocketParseData(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				socket.setSoTimeout(timeout);
				String ip = socket.getInetAddress().getHostAddress();        
				if(logger.isDebugEnabled()) {
					logger.debug(ip+"로 부터 연결됨");
				}
				bis = new BufferedInputStream(socket.getInputStream());
				//bis = new BufferedInputStream(new FileInputStream("D:/tmp/netbro/20160702_133105.dat"));
				int c = 0;
				int bufIndex = 0;
				int offset = 0;
				boolean loop = true;
				
				while(loop) {
					int type = 0;
					try {
						if(bufIndex == 0 || c <= 0) {
							c = bis.read(buf);
							
							bufIndex = 0;
							this.totalBytes += c;
							
							if(c < 0) {
								loop = false;
								break;
							}
							if(logger.isInfoEnabled()) {
								logger.info(c+ "byte 수신");
							}
							
							// 캡쳐 버튼을 클릭했다면 녹화시작
							if(capture) {
								if(fos != null)
									fos.write(buf, 0, c);
							}
						}

						Packet p = new Packet();
						int plen = p.makeData(buf, bufIndex, c);
						
						int pType = p.type;
						if (pType == 0) {
							RaceInfo raceInfo = new RaceInfo();
							raceInfo.setGameType(0);
							eventBroker.post("ODS_RACE/STATUS", raceInfo);
						} else if (pType == 9) {
							
						} else if (p.isRate()) {
							byte[] raceinfo = new byte[(plen+bufIndex-(bufIndex+48))];
							System.arraycopy(buf, bufIndex+48, raceinfo, 0, (plen+bufIndex-(bufIndex+48)));
							
							dataReceiver.eventReceived(raceinfo);
						}
						
						if (pType == -18) {
							if ((bufIndex > 1) || (c < 8192)) {
								byte[] nbuf = new byte[65536];
								System.arraycopy(buf, bufIndex, nbuf, 0, plen);
								System.arraycopy(nbuf, 0, buf, 1, plen);
								int nc = bis.read(buf, 1 + plen, buf.length - (1 + plen));
								this.totalBytes += nc;
								
								bufIndex = 1;
								c = plen + nc;
								if(logger.isInfoEnabled()) {
									logger.info("이어붙인 결과 start="+bufIndex+", length="+c);
								}
							} else {
								if(logger.isInfoEnabled()) {
									logger.info("이어붙이기 실패, data 폐기="+plen);
								}
								bufIndex = 0;
								c = 0;
							}
						} else {
							if (c <= plen) {
								bufIndex = 0;
								c = 0;
							} else {
								bufIndex += plen;
								c -= plen;
							}
							if(logger.isInfoEnabled()) {
								logger.info(plen+" byte 사용, 남은 버퍼: "+c+" byte");
							}
						}

					} catch (Exception e) {
						logger.error("stream data pasing error", e);
						loop = false;
						break;
					}
				}
			} catch (SocketException e1) {
				logger.error("SocketException error", e1);
			} catch (IOException e1) {
				logger.error("IOException error", e1);
			} catch (Exception e1) {
				logger.error("Exception error", e1);
			} finally {
				try {
					if(bis != null) bis.close();
					if(socket != null && !socket.isClosed()) {
						socket.close();
						socket = null;
					}
				} catch (Exception e) {}
			}

		}

	}

	private boolean checkServerPort(Integer port) {
		Socket check = null;
		try {
			check = new Socket("localhost", port);
		} catch (IOException ex) {
			return false;
		} finally {
			try {
				if(check != null) {
					if(!check.isClosed()) check.close();
					check = null;
				}
			} catch (Exception e) {}
		}
		return true;
	}

	@PreDestroy
	public void serverClose() {
		try {
			if(!serverThread.isShutdown()) {
				serverThread.shutdownNow();
			}
			if(!clientThread.isShutdown()) {
				clientThread.shutdownNow();
			}
			if(server != null && !server.isClosed()) server.close();
		} catch (Exception e) {}
	}
	
	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/CAPTURE") final String captureYn) {
		if(logger.isDebugEnabled()) {
			logger.debug("Rate Capture: "+captureYn);
		}
		if(captureYn != null && captureYn.equals("Y")) {
			if(fos == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
				String filename = sdf.format(new Date(System.currentTimeMillis()));
				
				File f = new File("D:/tmp/netbro", filename+".dat");
				if(!f.getParentFile().exists()) f.getParentFile().mkdirs();
				try {
					fos = new FileOutputStream(f);
				} catch (FileNotFoundException e) {
					logger.error("capture file create error", e);
				}
			}
		} else {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {}
				fos = null;
			}
		}
	}
}