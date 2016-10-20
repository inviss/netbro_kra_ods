package kr.co.netbro.kra.model;

import java.util.HashMap;
import java.util.Map;

public class FixedInfo {
	
	private String reqDate;
	private String reqTime;
	private Integer zone;
	private String zoneName;
	private Integer race;
	private String status;
	private String firstDone;
	private String secondDone;
	private String thirdDone;
	private String displayTime;
	private Integer delayTime;
	private boolean isFinal;
	private String cancel;
	private String canString;
	private boolean message;
	private Map<RaceType, String> result = new HashMap<RaceType, String>();
	
	public boolean isMessage() {
		return message;
	}
	public void setMessage(boolean message) {
		this.message = message;
	}
	public String getCanString() {
		return canString;
	}
	public void setCanString(String canString) {
		this.canString = canString;
	}
	public String getCancel() {
		return cancel;
	}
	public void setCancel(String cancel) {
		this.cancel = cancel;
	}
	public boolean isFinal() {
		return isFinal;
	}
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	public Integer getZone() {
		return zone;
	}
	public void setZone(Integer zone) {
		this.zone = zone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map<RaceType, String> getResult() {
		return result;
	}
	public void setResult(Map<RaceType, String> result) {
		this.result = result;
	}
	public void addResult(RaceType type, String data) {
		this.result.put(type, data);
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public Integer getRace() {
		return race;
	}
	public void setRace(Integer race) {
		this.race = race;
	}
	public String getFirstDone() {
		return firstDone;
	}
	public void setFirstDone(String firstDone) {
		this.firstDone = firstDone;
	}
	public String getSecondDone() {
		return secondDone;
	}
	public void setSecondDone(String secondDone) {
		this.secondDone = secondDone;
	}
	public String getThirdDone() {
		return thirdDone;
	}
	public void setThirdDone(String thirdDone) {
		this.thirdDone = thirdDone;
	}
	public String getDisplayTime() {
		return displayTime;
	}
	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}
	public Integer getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

}
