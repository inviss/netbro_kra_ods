package kr.co.netbro.kra.socket;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.common.utils.DateUtils;
import kr.co.netbro.kra.model.DecidedRate;
import kr.co.netbro.kra.model.IRaceInfoService;
import kr.co.netbro.kra.model.RaceInfo;
import kr.co.netbro.kra.model.RaceType;
import kr.co.netbro.kra.model.RaceZone;
import kr.co.netbro.kra.socket.maker.JSONDataMaker;
import kr.co.netbro.kra.socket.maker.Util;

@Creatable
public class EventDataReceiver {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private IEventBroker eventBroker;

	@Inject
	private IRaceInfoService raceInfoService;
	@Inject
	private JSONDataMaker jsonDataMaker;

	@Inject @Preference(nodePath="kra.config.socket", value="clientIP") String clientIP;
	@Inject @Preference(nodePath="kra.config.socket", value="zone") Integer zone;
	@Inject @Preference(nodePath="kra.config.socket", value="final_path") String finalPath;
	
	public void finalReceived(DecidedRate finalInfo) {
		if(finalInfo != null) {
			eventBroker.post("ODS_RACE/final", finalInfo);
		}
	}

	public void eventReceived(char[] c) {

		if(c != null) {
			//char[] c = ODSRateMaker.makeData(data, 0, data.length);

			RaceInfo raceInfo = new RaceInfo();
			raceInfo.setZone(toInt(c, 0, 1));
			raceInfo.setZoneName(RaceZone.getZoneName(raceInfo.getZone()));
			raceInfo.setGameType(toInt(c, 1, 2));
			raceInfo.setRaceNum(toInt(c, 3, 2));
			raceInfo.setTime(new String(c, 5, 2));
			raceInfo.setHorseNum(toInt(c, 7, 2));
			raceInfo.setMoney(Util.format(Long.parseLong(new String(c, 9, 11))));
			raceInfo.setMinimum(toRate(c, 20, 5));
			raceInfo.setTypeName(RaceType.getTypeName(raceInfo.getGameType()));

			if((raceInfo.getGameType() == RaceType.SAMBOK_TOP60.getType()
					|| raceInfo.getGameType() == RaceType.SAMSSANG_TOP60.getType())
					&& raceInfo.getHorseNum() > 0) {
				raceInfo.setTypeName(raceInfo.getTypeName()+raceInfo.getHorseNum());
			}
			raceInfo.setRaceinfo(c);

			if(logger.isDebugEnabled()) {
				logger.debug("game type: "+raceInfo.getGameType()+", typeName: "+raceInfo.getTypeName());
			}

			// 하나의 승식을 설정하여 최근변경일자 및 데이타 전송 클라이언트의 IP를 보여준다.
			if(raceInfo.getZone() == zone && raceInfo.getGameType() == RaceType.BOK.getType()) {
				raceInfo.setClientIP(clientIP);
				raceInfo.setUpdateTime(DateUtils.getToday("yyyy.MM.dd HH:mm:ss"));
			}
			
			logger.debug("config zone: "+zone+", race zone: "+raceInfo.getZone());
			if(raceInfo.getZone() == zone) {
				// 단승, 연승, 삼복TOP
				if(raceInfo.getGameType() == RaceType.DAN.getType() ||
						raceInfo.getGameType() == RaceType.YON.getType() ||
						raceInfo.getGameType() == RaceType.SAMBOK_TOP60.getType()) {
					eventBroker.post("ODS_RACE/1252", raceInfo);
				} else {
					eventBroker.post("ODS_RACE/"+raceInfo.getGameType(), raceInfo);
				}
			}

			// 설정화면에는 zone 에 관계없이 정보를 보내야 함.
			eventBroker.post("ODS_RACE/STATUS", raceInfo);

			// UI에서 화면 탭 클릭시 사용할 데이타를 맵에 저장함.
			raceInfoService.putRaceInfo(raceInfo);
			
			if(raceInfo.getZone() == zone && (
					raceInfo.getGameType() == 1 || raceInfo.getGameType() == 2 || raceInfo.getGameType() == 3|| raceInfo.getGameType() == 4
			)) {
				jsonDataMaker.makeRaceFile(raceInfo);
			}
		}
	}

	private int toInt(char[] data, int offset, int len) {
		return Integer.parseInt(toChar(data, offset, len));
	}

	private String toChar(char[] data, int offset, int len) {
		return new String(data, offset, len);
	}

	private String toRate(char[] data, int offset, int len) {
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
