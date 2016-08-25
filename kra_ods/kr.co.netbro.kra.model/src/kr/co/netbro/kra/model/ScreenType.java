package kr.co.netbro.kra.model;

import java.util.HashMap;
import java.util.Map;

public enum ScreenType {
	
	NONE(0, "---없음---"), DANYON(1, "단연승식"), D_BOK(2, "단복승식"), Y_BOKYON (3, "연복연승식"), SSANG(4, "쌍승식"), 
	SAMBOK(5, "삼복승식"), SAMBOKTOP(6, "삼복TOP"), SAMSSANGTOP(7, "삼쌍TOP"),
	M_CANCEL(8, "출전취소"), M_CHANGE(9, "선수변경"), M_RESULT(10, "경주성적표");
	
	private int type;
	private String name;
	private static Map<Integer, String> mMap;
	
	private ScreenType(int type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public int getType() {
		return type;
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
	
	public static int getTypeOrdinal(int type) {
		for (ScreenType t : ScreenType.values()) {
            if(t.type == type) return t.ordinal();
        }
		return -1;
	}
	
	public static String getTypeName(int type) {
		if(mMap == null) {
			initializeMapping();
		}
		if(mMap.containsKey(type)) {
			return mMap.get(type);
		}
		return null;
	}
	
	private static void initializeMapping() {
        mMap = new HashMap<Integer, String>();
        for (ScreenType t : ScreenType.values()) {
            mMap.put(t.type, t.name);
        }
    }
}
