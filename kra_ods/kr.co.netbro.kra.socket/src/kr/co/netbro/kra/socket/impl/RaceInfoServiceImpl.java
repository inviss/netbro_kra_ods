package kr.co.netbro.kra.socket.impl;

import java.util.HashMap;
import java.util.Map;

import kr.co.netbro.kra.model.IRaceInfoService;
import kr.co.netbro.kra.model.RaceInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaceInfoServiceImpl implements IRaceInfoService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private volatile Map<Integer, RaceInfo> infoMap = new HashMap<Integer, RaceInfo>();

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
