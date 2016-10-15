package kr.co.netbro.kra.socket.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.model.IRaceInfoService;
import kr.co.netbro.kra.model.RaceInfo;
import kr.co.netbro.race.database.IRateODSService;

public class RaceInfoServiceImpl implements IRaceInfoService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private volatile Map<Integer, RaceInfo> infoMap = new HashMap<Integer, RaceInfo>();
	
	private IRateODSService raceODSService;
	
	
	public void setRaceODSService(IRateODSService raceODSService) {
		this.raceODSService = raceODSService;
	}
	
	public void unSetRateODSService(IRateODSService raceODSService) {
		this.raceODSService = null;
	}

	@Override
	public void saveFinalRace(String fpath, byte[] data) {
		FileWriter fw = null;
		try {
			//fw = new FileWriter("../masa/data/final.dat");
			if (data != null) {
				
			}
		} catch (Exception e) {
			logger.error("saveFinalRace error", e);
		} finally {
			if(fw != null)
				try {
					fw.close();
				} catch (IOException e) {}
		}
	}
	
	@Override
	public void putRaceInfo(RaceInfo raceInfo) {
		synchronized (infoMap) {
			if(infoMap.containsKey(raceInfo.getGameType())) infoMap.remove(raceInfo.getGameType());
			infoMap.put(raceInfo.getGameType(), raceInfo);
		}
	}

	@Override
	public RaceInfo getRaceInfo(int raceType) {
		return infoMap.get(raceType);
	}
	
	@Override
	public void allRaceClear() {
		infoMap.clear();
	}

}
