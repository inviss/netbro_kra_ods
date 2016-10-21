package kr.co.netbro.kra.model;

import java.util.List;

import kr.co.netbro.kra.entity.Cancel;
import kr.co.netbro.kra.entity.Change;
import kr.co.netbro.kra.entity.Final;
import kr.co.netbro.kra.entity.Result;

public interface IRaceInfoService {

	/**
	 * 
	 * @param zone
	 * @param date
	 * @return
	 */
	List<Cancel> findCancels(String zone, String date);
	
	/**
	 * 
	 * @param zone
	 * @param date
	 * @return
	 */
	List<Change> findChanges(String zone, String date);
	
	/**
	 * 
	 * @param zone
	 * @param date
	 * @return
	 */
	List<Final> findFinals(String zone, String date);
	
	/**
	 * 
	 * @param zone
	 * @param date
	 * @return
	 */
	List<Result> findResults(String zone, String date);

	/**
	 * 확정경기를 파일로 저장한다.
	 * 기존에 파일이 존재하면 끝부분까지 seek 하여 append 하도록 한다.
	 * @param data
	 */
	public void saveFinalRace(String fpath, byte[] data);

	/**
	 * 
	 * @param raceInfo
	 */
	public void putRaceInfo(RaceInfo raceInfo);
	public RaceInfo getRaceInfo(int raceType);
	public void allRaceClear();

}
