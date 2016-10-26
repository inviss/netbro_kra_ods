package kr.co.netbro.kra.socket.maker;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.common.utils.GSON;
import kr.co.netbro.common.utils.Utility;
import kr.co.netbro.kra.dto.KRARate;
import kr.co.netbro.kra.model.RaceInfo;
import kr.co.netbro.kra.model.RaceType;

@Creatable
public class JSONDataMaker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	public void makeRaceFile(RaceInfo raceInfo) {
		try {
			KRARate rate = new KRARate();
			rate.setZoneName(raceInfo.getZoneName());
			rate.setNum(raceInfo.getRaceNum());
			rate.setType(raceInfo.getGameType());
			rate.setTypeName(raceInfo.getTypeName());
			rate.setTime(raceInfo.getTime());
			rate.setMoney(raceInfo.getMoney());

			rate.setData(makeData(raceInfo));
			
			// 승식별 파일명을 찾는다.
			String jsonName = "";
			for(RaceType type : RaceType.values()) {
				if(rate.getType() == type.getType()) {
					jsonName = type.name();
				}
			}
			Utility.stringToFile(GSON.toString(rate), "X:/kra", jsonName+".json");
			logger.debug(GSON.toString(rate));
		} catch (Exception e) {
			logger.error("json create error", e.getMessage());
		}
	}

	public String[][] makeData(RaceInfo raceInfo) {
		int dataPtr = 25;

		String[][] rateData = null;
		int type = raceInfo.getGameType();
		char[] data = raceInfo.getRaceinfo();
		if(type == 1 || type == 2) {
			rateData = new String[2][raceInfo.getHorseNum() + 1];
			for (int i = 0; i < raceInfo.getHorseNum(); i++) {
				rateData[0][(i + 1)] = String.valueOf(i + 1);
				rateData[1][(i + 1)] = toRate(data, dataPtr + i * 5, 5);
			}
		} else if(type == 4) {
			rateData = new String[raceInfo.getHorseNum() + 1][raceInfo.getHorseNum() + 1];
			for (int i = 1; i < raceInfo.getHorseNum() + 1; i++) {
				rateData[0][i] = (rateData[i][0] = String.valueOf(i));
				for (int j = 1; j < raceInfo.getHorseNum() + 1; j++) {
					if (i != j) {
						rateData[i][j] = toRate(data, dataPtr, 5);
						dataPtr += 5;
					} else {
						rateData[i][j] = String.valueOf(i);
					}
				}
			}
		} else if(type == 3 || type == 5) {
			rateData = new String[raceInfo.getHorseNum() + 1][raceInfo.getHorseNum() + 1];
			for (int i = 1; i < raceInfo.getHorseNum() + 1; i++) {
				rateData[i][i] = (rateData[0][i] = String.valueOf(i));
				for (int j = i + 1; j < raceInfo.getHorseNum() + 1; j++) {
					rateData[i][j] = toRate(data, dataPtr, 5);
					dataPtr += 5;
				}
			}
		}

		return rateData;
	}

	public static int toInt(char[] data, int offset, int len) {
		return Integer.parseInt(new String(data, offset, len));
	}

	protected static String toRate(char[] data, int offset, int len) {
		if (data[offset] == '-') {
			return new String(data, offset, len);
		}

		String str = new String(data, offset, len - 1);
		try {
			return Integer.parseInt(str) + "." + data[(offset + len - 1)];
		} catch (Exception ex) {}

		return new String(data, offset, len);
	}
}
