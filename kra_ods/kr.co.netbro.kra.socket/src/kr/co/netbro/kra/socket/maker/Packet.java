package kr.co.netbro.kra.socket.maker;

import kr.co.netbro.kra.model.FinalInfo;

public class Packet {
	public int type = -1;
	public int zone;
	public int race;
	public int horse;
	public static final int CARDINFO = 111;
	public static final int START = 222;
	public static final int STOP = 333;
	public static final int PING = 0;
	public static final int RESULT = 9;
	public static final int INVALID = -1;
	public static final int UNKNOWN = -2;
	public static final int UNKNOWN_GAME = -3;
	public static final int SHORT = -18;
	private char[] data;
	public boolean[] valid;
	public FinalInfo finalData;

	public Packet() {
		
		this.type = -1;
		this.data = null;
		this.horse = 7;
		this.valid = new boolean[9];
	}

	/*
	 * len : buf length
	 */
	public int makeData(byte[] buf, int offset, int len) {
		try {
			//char[] c = new String(buf, offset, len).toCharArray();
			
			if (len < 16) {
				this.type = -18;
				return len;
			}
			//                                      >  >(12)                                            >  >(28)                                            >  >(44)
			//[0, 1, 0, 1, 0, 0, 0, 16, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 1, 115, 0, 0, 1, 115, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0...]
			//[0, 1, 0, 1, 0, 0, 0, 16, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, 16 , 0, 0, 0,   0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, -35, 0, 0, 0, -35, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -35, 
			if ((buf[(offset + 12)] == 0) && (buf[(offset + 13)] == 2)) {
				this.type = 0;
				return 16;
			}
			
			if ((buf[(offset + 12)] != 0) || (buf[(offset + 13)] != 1)) {
				return len;
			}
			
			if (len < 48) {
				this.type = -18;
				return len;
			}
			//                                                              >  >  >  >                                            >  >  >  >  | 64byte(end)
			//[0, 1, 0, 1, 0, 0, 0, 16, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 1, 115, 0, 0, 1, 115, 0, 1, 0, ..., 0, 0, 0, 0, 0, 0, 1, 115, 49...]
			//                                                                                                                  >  >  >  >                                                                                                                  >  >  >  >
			//[0, 1, 0, 1, 0, 0, 0, 16, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, 16 , 0, 0, 0,   0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, -35, 0, 0, 0, -35, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -35,
			for (int i = 0; i < 4; i++) {
				byte m = buf[(offset + 4 + i)];
				byte n = buf[(offset + 44 + i)];
				if (buf[(offset + 4 + i)] != buf[(offset + 44 + i)]) {
					this.type = -1;
					return len;
				}
			}
			//                                                              <  <  <  <
			//[0, 1, 0, 1, 0, 0, 0, 16, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 1, 115, 0, 0, 1, 115, 0, 1, 0
			//                                                                                                                  <  <  <  <                                                                                                                  
			//[0, 1, 0, 1, 0, 0, 0, 16, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, 16 , 0, 0, 0,   0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, -35, 0, 0, 0, -35, 0, 1
			// -35 & 0xff = 221
			int size = 0;
			int pow = 1;
			for (int i = 7; i >= 4; i--) {
				int m = buf[(offset + i)];
				int n = (0xFF & buf[(offset + i)]);
				size += pow * (0xFF & buf[(offset + i)]);
				pow *= 256;
			}
			
			int plen = 48 + size; // 269, 419, 435
			if (len < plen) {
				this.type = -18;

				return len;
			}
			
			int doff = offset + 48;
			this.zone = (buf[(doff + 2)] - 48);
			if (buf[doff] == 50) { // 2
				if (buf[(doff + 21)] == 50) {
					this.type = 222;
				} else if (buf[(doff + 21)] == 51) {
					this.type = 333;
				} else {
					if (buf[(doff + 21)] == 49) {
						this.type = 111;
					} else {
						this.type = -2;
					}
				}
				
				this.race = ((buf[(doff + 24)] - 48) * 10 + (buf[(doff + 25)] - 48));
				this.horse = ((buf[(doff + 26)] - 48) * 10 + (buf[(doff + 27)] - 48));
				for (int i = 0; i < 9; i++) {
					this.valid[i] = buf[doff + 28 + i] != 48;
				}
				this.valid[6] = this.valid[5];
				this.valid[8] = this.valid[7];
			} else if (buf[doff] == 49) { // 1
				//11104510120160702300500195
				this.type = ((buf[(doff + 19)] - 48) * 10 + (buf[(doff + 20)] - 48));
				this.race = ((buf[(doff + 7)] - 48) * 10 + (buf[(doff + 8)] - 48));
				if (isRate()) { // {1, 2, 3, 4, 5, 6, 52, 10, 54} = rate, 9 = final
					this.data = ODSRateMaker.makeData(buf, doff, plen + offset - doff); // 269 + 32 - 80
				} else if (this.type == 9) { // final
					this.finalData = ODSRateMaker.makeFinal(buf, doff, plen + offset - doff);
				} else {
					this.type = -3;
				}
			} else {
				this.type = -2;
			}
			
			return plen;
		} catch (Exception e) {
			e.printStackTrace();
			this.type = -1;
		}
		return len;
	}

	public char[] getData() {
		return this.data;
	}
	
	public FinalInfo getFinalData() {
		return finalData;
	}
	
	public boolean isRate() {
		int rateIndex = getRateIndex(this.type);
		return (rateIndex >= 0) && (rateIndex < 9);
	}

	public int getRateIndex() {
		return getRateIndex(this.type);
	}

	public static int getRateIndex(int packetType) {
		int rateIndex = -1;
		switch (packetType) {
			case 1: 
				rateIndex = 0;
				break;
			case 2: 
				rateIndex = 1;
				break;
			case 3: 
				rateIndex = 2;
				break;
			case 4: 
				rateIndex = 3;
				break;
			case 5: 
				rateIndex = 4;
				break;
			case 6: 
				rateIndex = 5;
				break;
			case 52: 
				rateIndex = 6;
				break;
			case 10: 
				rateIndex = 7;
				break;
			case 54: 
				rateIndex = 8;
				break;
			case 9: 
				rateIndex = 9;
		}
		return rateIndex;
	}

	public static int getGameType(int rateIndex) {
		int gameType = -1;
		switch (rateIndex) {
			case 0: 
				gameType = 1;
				break;
			case 1: 
				gameType = 2;
				break;
			case 2: 
				gameType = 3;
				break;
			case 3: 
				gameType = 4;
				break;
			case 4: 
				gameType = 5;
				break;
			case 5: 
				gameType = 6;
				break;
			case 6: 
				gameType = 52;
				break;
			case 7: 
				gameType = 10;
				break;
			case 8: 
				gameType = 54;
				break;
			case 9: 
				gameType = 9;
		}
		return gameType;
	}

	public int getLogType() {
		switch (this.type) {
			case 9: 
			case 222: 
			case 333: 
				return 1;
			case -3: 
			case -2: 
			case -1: 
				return 8;
		}
		return 2;
	}

	public String toString() {
		switch (this.type) {
			case 0: 
				return "Packet-PING";
			case -1: 
				return "Packet-INVALID 인식불가 (버퍼비움)";
			case -2: 
				return "Packet-UNKNOWN 약속되지 않은 MESSAGE_ID (현재 데이터 버림)";
			case -3: 
				return "Packet-UNKNOWN_GAME";
			case -18: 
				return "Packet-SHORT 데이터가 짧음 (이어붙이기 최대 8192)";
			case 111: 
				return "Packet-CARDINFO";
		}
		
		String str = "Packet-";
		if (this.type == 222) {
			str = str + "경주시작 ";
		} else if (this.type == 333) {
			str = str + "경주마감 ";
		} else if (this.type == 9) {
			str = str + "확정배당률 (" + (this.finalData != null ? "정상) " : "예외) ");
		} else if (isRate()) {
			str = str + "배당률 ";
		}
		
		str = str + this.race + "경주 ";
		if (this.zone == 1) {
			str = str + "____________________ 서울 ";
		} else if (this.zone == 2) {
			str = str + "_____________ 부산 ";
		} else if (this.zone == 3) {
			str = str + "_______ 제주 ";
		} else {
			str = str + "기타 ";
		}
		
		if (isRate()) {
			//str = str + "승식=" + Constants.TYPE_NAME[getRateIndex(this.type)];
		} else if (this.type == 222) {
			str = str + ", Start 유효승식=" + Util.getValidString(this.valid);
		}
		
		return str;
	}
}
