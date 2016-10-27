package kr.co.netbro.kra.model;

import java.util.HashMap;
import java.util.Map;

public enum RaceZone {
	//SEOUL(1, "서울"), JEJU(2, "제주"), BUSAN(3, "부경");
	SEOUL(1, "\uC11C\uC6B8"), JEJU(2, "\uC81C\uC8FC"), BUSAN(3, "\uBD80\uACBD");
	
	private static Map<Integer, String> mMap;
	private int zone;
	private String name;
	
	private RaceZone(int zone, String name) {
		this.zone = zone;
		this.name = name;
	}

	public int getZone() {
		return zone;
	}

	public String getName() {
		return name;
	}
	
	public static String[] getValues() {
		if(mMap == null) {
			initializeMapping();
		}
		return mMap.values().toArray(new String[0]);
	}
	
	public static String getZoneName(int zone) {
		if(mMap == null) {
			initializeMapping();
		}
		if(mMap.containsKey(zone)) {
			return mMap.get(zone);
		}
		return null;
	}
	
	private static void initializeMapping() {
        mMap = new HashMap<Integer, String>();
        for (RaceZone t : RaceZone.values()) {
            mMap.put(t.zone, t.name);
        }
    }
}
