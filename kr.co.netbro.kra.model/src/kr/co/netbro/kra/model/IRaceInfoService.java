package kr.co.netbro.kra.model;

public interface IRaceInfoService {
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
