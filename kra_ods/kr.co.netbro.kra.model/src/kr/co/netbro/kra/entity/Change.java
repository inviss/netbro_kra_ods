package kr.co.netbro.kra.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

import kr.co.netbro.kra.method.BaseObject;

@SuppressWarnings("serial")
@NamedNativeQueries({
	//@NamedNativeQuery(name="Rate.Change", query="SELECT ROWNUM AS NUM, STR01, STR02, STR03, STR04, STR05, STR06, STR07, STR14 FROM (SELECT ROWNUM AS NUM, A.* FROM (SELECT DECODE(A.MEET,'1','서울','2','제주','3','부경') STR01, A.RC_NO STR02, A.CHUL_NO STR03, IPSRACE.GET_JK_NAME(A.MEET, A.JK_BEF) STR04,TRIM(TO_CHAR(a.bef_budam,'90.0')) STR14, IPSRACE.GET_JK_NAME(A.MEET, A.JK_AFT) STR05, TO_CHAR(DECODE(A.SEQ,(SELECT MAX(SEQ) FROM IPSRACE.JK_CHG WHERE RC_DATE = A.RC_DATE AND RC_NO = A.RC_NO AND CHUL_NO = A.CHUL_NO AND MEET = A.MEET), B.WG_BUDAM, (SELECT BEF_BUDAM FROM IPSRACE.JK_CHG WHERE RC_DATE = A.RC_DATE AND RC_NO = A.RC_NO AND CHUL_NO = A.CHUL_NO AND SEQ = A.SEQ + 1 AND MEET = A.MEET)), 'FM90.0') STR06, SUBSTRB(A.REASON, 1, 28) STR07 FROM IPSRACE.JK_CHG A, IPSRACE.RC_RESULT B WHERE A.MEET = B.MEET AND A.RC_DATE = B.RC_DATE AND A.RC_NO = B.RC_NO AND A.CHUL_NO = B.CHUL_NO AND EXISTS (SELECT * FROM IPSRACE.RC_SELL WHERE MEET = A.MEET AND RC_DATE  = A.RC_DATE AND RC_NO = A.RC_NO AND SEL_MEET = ?)  AND A.RC_DATE = TO_DATE(?, 'YYYYMMDD')  ORDER BY A.RC_NO DESC, A.CHUL_NO) A)"
	@NamedNativeQuery(name="Rate.Change", query="SELECT ROWNUM AS NUM, STR01, STR02, STR03, STR04, STR05, STR06, STR07, STR14 FROM (SELECT ROWNUM AS NUM, A.* FROM (SELECT DECODE(A.MEET,'1','서울','2','제주','3','부경') STR01, A.RC_NO STR02, A.CHUL_NO STR03, null STR04,TRIM(TO_CHAR(a.bef_budam,'90.0')) STR14, null STR05, TO_CHAR(DECODE(A.SEQ,(SELECT MAX(SEQ) FROM JK_CHG WHERE RC_DATE = A.RC_DATE AND RC_NO = A.RC_NO AND CHUL_NO = A.CHUL_NO AND MEET = A.MEET), B.WG_BUDAM, (SELECT BEF_BUDAM FROM JK_CHG WHERE RC_DATE = A.RC_DATE AND RC_NO = A.RC_NO AND CHUL_NO = A.CHUL_NO AND SEQ = A.SEQ + 1 AND MEET = A.MEET)), 'FM90.0') STR06, SUBSTRB(A.REASON, 1, 28) STR07 FROM JK_CHG A, RC_RESULT B WHERE A.MEET = B.MEET AND A.RC_DATE = B.RC_DATE AND A.RC_NO = B.RC_NO AND A.CHUL_NO = B.CHUL_NO AND EXISTS (SELECT * FROM RC_SELL WHERE MEET = A.MEET AND RC_DATE  = A.RC_DATE AND RC_NO = A.RC_NO AND SEL_MEET = ?)  AND A.RC_DATE = TO_DATE(?, 'YYYYMMDD')  ORDER BY A.RC_NO DESC, A.CHUL_NO) A)"
			, resultClass = Change.class)
})
@Entity
public class Change extends BaseObject {
	
	@Id
    private int num;
    private String str01;
    private String str02;
    private String str03;
    private String str04;
    private String str05;
    private String str06;
    private String str07;
    private String str14;
    
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getStr01() {
		return str01;
	}
	public void setStr01(String str01) {
		this.str01 = str01;
	}
	public String getStr02() {
		return str02;
	}
	public void setStr02(String str02) {
		this.str02 = str02;
	}
	public String getStr03() {
		return str03;
	}
	public void setStr03(String str03) {
		this.str03 = str03;
	}
	public String getStr04() {
		return str04;
	}
	public void setStr04(String str04) {
		this.str04 = str04;
	}
	public String getStr05() {
		return str05;
	}
	public void setStr05(String str05) {
		this.str05 = str05;
	}
	public String getStr06() {
		return str06;
	}
	public void setStr06(String str06) {
		this.str06 = str06;
	}
	public String getStr07() {
		return str07;
	}
	public void setStr07(String str07) {
		this.str07 = str07;
	}
	public String getStr14() {
		return str14;
	}
	public void setStr14(String str14) {
		this.str14 = str14;
	}
    
}