package kr.co.netbro.kra.model;

public interface IRaceInfoService {
	public void putRaceInfo(RaceInfo raceInfo);
	public RaceInfo getRaceInfo(int raceType);
	public void allRaceClear();

}
