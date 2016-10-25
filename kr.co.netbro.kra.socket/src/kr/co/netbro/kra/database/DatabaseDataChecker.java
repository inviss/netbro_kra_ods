package kr.co.netbro.kra.database;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.common.utils.DateUtils;
import kr.co.netbro.kra.entity.Cancel;
import kr.co.netbro.kra.entity.Change;
import kr.co.netbro.kra.entity.Final;
import kr.co.netbro.kra.entity.Result;
import kr.co.netbro.kra.model.IRaceInfoService;

@SuppressWarnings("restriction")
@Creatable
public class DatabaseDataChecker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private ExecutorService serverThread = Executors.newSingleThreadExecutor();

	@Inject @Preference(nodePath="kra.config.socket", value="zone") Integer zone;
	@Inject @Preference(nodePath="kra.config.database", value="grade") String gradeDate;
	@Inject @Preference(nodePath="kra.config.database", value="change") String changeDate;
	
	@Inject
	private IEventBroker eventBroker;
	@Inject
	private IRaceInfoService raceInfoService;
	
	private volatile String currentDate;
	
	@PostConstruct
	public void serverConnect() {
		try {
			serverThread.execute(new DatabaseChecker());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 일정 간격으로 DB 조회를 한다.
	 * @author Administrator
	 *
	 */
	public class DatabaseChecker implements Runnable {

		@Override
		public void run() {
			while(true) {
				// 현재일과 지난주 마지막 경기요일을 체크한다. 경마장별로 마지막 경기요일 체크 필요.
				executeDateCheck();
				
				// 출전 취소
				List<Cancel> cancels = raceInfoService.findCancels(String.format("%02d", zone.intValue()), changeDate);
				if(!cancels.isEmpty())
					eventTransfer("ODS_RACE/cancel", cancels);

				// 선수 변경
				List<Change> changes = raceInfoService.findChanges(String.format("%02d", zone.intValue()), changeDate);
				if(!changes.isEmpty())
					eventTransfer("ODS_RACE/change", changes);

				// 경주 성적
				List<Final> finals = raceInfoService.findFinals(String.format("%02d", zone.intValue()), gradeDate);
				if(!finals.isEmpty())
					eventTransfer("ODS_RACE/grade", finals);

				// 동착 결과
				List<Result> results = raceInfoService.findResults(String.format("%02d", zone.intValue()), gradeDate);
				if(!results.isEmpty())
					eventTransfer("ODS_RACE/heat", results);

				try {
					Thread.sleep(5000L);
				} catch (Exception e) {}
			}
		}
	}
	
	private void eventTransfer(String eventId, List<?> data) {
		eventBroker.post(eventId, data);
	}

	public void executeDateCheck() {
		Calendar cal = Calendar.getInstance();
		/*
		 * 현재일이 설정이 안되어 있거나
		 * 서버 일자와 맞지 않다면 검색일 조건을 모두 현재일 기준으로 초기화 한다.
		 * 예) 사용자가 검색일을 조정했다면 기본 검색 조건일로 재조정 하도록 한다.
		 */
		if(StringUtils.isBlank(currentDate) || !currentDate.equals(DateUtils.getFmtDateString(cal.getTime(), "yyyyMMdd"))) {
			currentDate = DateUtils.getFmtDateString(cal.getTime(), "yyyyMMdd");
			
			changeDate = currentDate;
			if(zone == null && zone == 0) {
				zone = 1;
			}
			switch(zone) {
			case 1 : // 서울
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				break;
			case 2 : // 제주
				cal.add(Calendar.DATE, -7);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
				break;
			case 3 : // 부경
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				break;
			}
			gradeDate = DateUtils.getFmtDateString(cal.getTime(), "yyyyMMdd");
			
			eventBroker.post("ODS_DATE/CHANGE", changeDate);
			eventBroker.post("ODS_DATE/GRADE", gradeDate);
		} else {
			// 사용자가 검색일을 조정했을경우
			if(StringUtils.isBlank(changeDate)) {
				changeDate = DateUtils.getFmtDateString(cal.getTime(), "yyyyMMdd");
				eventBroker.post("ODS_DATE/CHANGE", changeDate);
			}
			
			if(StringUtils.isBlank(gradeDate)) {
				if(zone == null && zone == 0) {
					zone = 1;
				}
				switch(zone) {
				case 1 : // 서울
					cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
					break;
				case 2 : // 제주
					cal.add(Calendar.DATE, -7);
					cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
					break;
				case 3 : // 부경
					cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
					break;
				}
				gradeDate = DateUtils.getFmtDateString(cal.getTime(), "yyyyMMdd");
				eventBroker.post("ODS_DATE/GRADE", gradeDate);
			}
		}
	}

	@PreDestroy
	public void serverClose() {
		try {
			if(!serverThread.isShutdown()) {
				serverThread.shutdownNow();
			}
		} catch (Exception e) {}
	}
	

	/**
	 * 
	 * @param captureYn
	 */
	@Inject @Optional
	public void  getGradeEvent(@UIEventTopic("ODS_DB/GRADE") final String date) {
		if(logger.isDebugEnabled()) {
			logger.debug("Grade date: "+date);
		}
		// 요청일과 기존요일이 같지 않다면
		if(!date.equals(gradeDate)) {
			gradeDate = date;
		}

	}

	@Inject @Optional
	public void  getChangeEvent(@UIEventTopic("ODS_DB/CHANGE") final String date) {
		if(logger.isDebugEnabled()) {
			logger.debug("Change date: "+date);
		}
		// 요청일과 기존요일이 같지 않다면
		if(!date.equals(changeDate)) {
			changeDate = date;
		}
	}
}
