package kr.co.netbro.kra.model;

public class RaceInfo {
	int zone = 0;
	String zoneName = "";
	int gameType = 0;
	int raceNum = 0;
	String time = "";
	int horseNum = 0;
	String money = "";
	String minimum = "";
	String typeName = "";
	char[] raceinfo = null;
	
	public RaceInfo() {}
	
	RaceInfo(int zone, String zoneName, int gameType, int raceNum, String time, int horseNum, 
			String money, String minimum, String typeName) {
		this.zone = zone;
		this.zoneName = zoneName;
		this.gameType = gameType;
		this.raceNum = raceNum;
		this.time = time;
		this.horseNum = horseNum;
		this.money = money;
		this.minimum = minimum;
		this.typeName = typeName;
	}

	public int getZone() {
		return zone;
	}

	public void setZone(int zone) {
		this.zone = zone;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public int getRaceNum() {
		return raceNum;
	}

	public void setRaceNum(int raceNum) {
		this.raceNum = raceNum;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getHorseNum() {
		return horseNum;
	}

	public void setHorseNum(int horseNum) {
		this.horseNum = horseNum;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public char[] getRaceinfo() {
		return raceinfo;
	}

	public void setRaceinfo(char[] raceinfo) {
		this.raceinfo = raceinfo;
	}
}
