package kr.co.netbro.kra.model;

import java.util.HashMap;
import java.util.Map;

public enum RaceType {
	
	DAN(1, "단승식"), YON(2, "연승식"), BOKYON (3, "복연승식"), SSANG(4, "쌍승식"), 
	BOK(5, "복승식"), SAMBOK(6, "삼복승식"), FINAL(9, "확정"), SAMSSANG(10, "삼쌍승식"),
	//BOK(5, "복승식"), SAMBOK(6, "삼복승식"), SAMSSANG(10, "삼쌍승식"),
	SAMBOK_TOP60(52, "삼복TOP"), SAMSSANG_TOP60(54, "삼쌍TOP"),
	GISU_CHANGE(60, "기수변경"), CHUL_CANCEL(61, "출전취소"), RACE_RATE(62, "경기성적"), DEAD_HEAT(63, "동착결과");
	
	private int type;
	private String name;
	private static Map<Integer, String> mMap;
	
	public static final String[] SHORT_TYPE_NAME = { "단", "연", "복연", "쌍", "복", "삼복", "삼쌍", "삼복T", "삼쌍T" };
	
	private RaceType(int type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public int getType() {
		return type;
	}
	
	public static RaceType getType(int type) {
		for (RaceType t : RaceType.values()) {
            if(t.type == type) return t;
        }
		return null;
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
		for (RaceType t : RaceType.values()) {
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
        for (RaceType t : RaceType.values()) {
            mMap.put(t.type, t.name);
        }
    }
}
