package test.json;

import java.util.Calendar;

import kr.co.netbro.common.utils.DateUtils;
import kr.co.netbro.kra.model.RaceType;

public class CalendarTest {

	public static void main(String[] args) {
		System.out.println(RaceType.BOK.name());
		/*
		int tYear = 2016;
        int tMonth = 10;
        int tDate = 21;
        System.out.println(String.format("%02d", 1));
        Calendar cal = Calendar.getInstance();
        cal.set(tYear, tMonth-1, tDate);
        System.out.println("입력된 날짜 : " + DateUtils.getFmtDateString(cal.getTime(), "yyyy-MM-dd"));
        cal.add(Calendar.DATE, -7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        System.out.println("입력된 날짜의 이전주의 목요일 : " + DateUtils.getFmtDateString(cal.getTime(), "yyyy-MM-dd"));
        */
	}

}
