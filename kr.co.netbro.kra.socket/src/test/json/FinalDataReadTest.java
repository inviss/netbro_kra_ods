package test.json;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import kr.co.netbro.kra.model.DecidedRate;
import kr.co.netbro.kra.socket.maker.ODSRateMaker;

public class FinalDataReadTest {

	public static void main(String[] args) {
		try {
			//byte[] buf = new byte[65536];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:/tmp/odsrate/eclipse/workspace/files/final/20161027/1_20160702_1_0.dat"));
			
			 byte[] buf = new byte[bis.available()];
			 
			int c = bis.read(buf);
			System.out.println("length: "+c);
			
			DecidedRate decidedRate = ODSRateMaker.makeFinal(buf, 0, c);
			System.out.println("zone: "+decidedRate.getZone()+", zone_name: "+decidedRate.getZoneName()+", req_date: "+decidedRate.getReqDate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
