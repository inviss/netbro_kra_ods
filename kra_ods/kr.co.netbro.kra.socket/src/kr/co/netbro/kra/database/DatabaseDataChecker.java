package kr.co.netbro.kra.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
@Creatable
public class DatabaseDataChecker {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private ExecutorService serverThread = Executors.newSingleThreadExecutor();
	
	@Inject
	private IEventBroker eventBroker;
	
	@PostConstruct
	public void serverConnect() {
		
	}
	
	/**
	 * 일정 간격으로 DB 조회를 한다.
	 * @author Administrator
	 *
	 */
	public class DatabaseChecker implements Runnable {
		
		@Override
		public void run() {
			while(true) {
				
				
				
				try {
					Thread.sleep(5000L);
				} catch (Exception e) {}
			}
		}
	}
	
	@PreDestroy
	public void serverClose() {
		try {
			if(!serverThread.isShutdown()) {
				serverThread.shutdownNow();
			}
		} catch (Exception e) {}
	}
	
	/**
	 * 
	 * @param captureYn
	 */
	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/CHECKER") final String captureYn) {
		if(logger.isDebugEnabled()) {
			logger.debug("Rate Capture: "+captureYn);
		}
	}
}
