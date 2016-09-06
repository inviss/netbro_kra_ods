package kr.co.netbro.kra.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

import kr.co.netbro.kra.method.BaseObject;

@SuppressWarnings("serial")
@NamedNativeQueries({
	@NamedNativeQuery(name="Rate.Cancel", query="SELECT ROWNUM AS NUM, STR01, STR02, STR03, STR04, STR05, STR06 FROM (SELECT DECODE(A.MEET,'1','서울','2','제주','3','부경') STR01, A.RC_NO STR02, A.CHUL_NO STR03, SUBSTRB(TRIM(DECODE(B.MEET, A.MEET, B.HR_NAME, DECODE(B.MEET, '1', '[서]'||B.HR_NAME, '3', '[부]'||B.HR_NAME, B.HR_NAME))), 1, 10) STR04, NULL STR05, SUBSTRB(TRIM(A.REASON ), 1, 28) STR06 FROM PUNISH A, HR B, HR_TR C WHERE B.MEET = C.MEET AND decode(a.meet, '1', 1, '2', 2, '3', 1) = decode(b.meet, '1', 1, '2', 2, '3', 1) AND A.RC_DATE = TO_DATE(?, 'YYYYMMDD') AND EXISTS (SELECT * FROM RC_SELL WHERE MEET = A.MEET AND RC_DATE = A.RC_DATE AND RC_NO = A.RC_NO AND SEL_MEET = ? AND A.PR_GUBUN = '0' AND RTRIM(A.KIND) IN ('경주제외','출발제외','출주취소', '출전제외', '출전취소') AND A.PR_NO = B.HR_NO AND C.HR_NO = A.PR_NO AND C.ST_DATE = (SELECT MAX(ST_DATE) FROM HR_TR WHERE MEET = B.MEET AND HR_NO = A.PR_NO AND ST_DATE <= A.RC_DATE)) ORDER BY 2,3)")
	//@NamedNativeQuery(name="Rate.Cancel", query="SELECT ROWNUM AS NUM, STR01, STR02, STR03, STR04, STR05, STR06  FROM (SELECT DECODE(A.MEET,'1','서울','2','제주','3','부경') STR01,A.RC_NO STR02, A.CHUL_NO STR03, SUBSTRB(TRIM(DECODE(B.MEET, A.MEET, B.HR_NAME, DECODE(B.MEET, '1', '[서]'||B.HR_NAME, '3', '[부]'||B.HR_NAME, B.HR_NAME))), 1, 10) STR04, SUBSTRB(TRIM(IPSRACE.GET_TR_NAME(C.MEET, C.TR_NO)), 1, 6 ) STR05, SUBSTRB(TRIM(A.REASON ), 1, 28) STR06 FROM IPSRACE.PUNISH A, IPSRACE.HR B, IPSRACE.HR_TR C WHERE B.MEET = C.MEET  and decode(a.meet, '1', 1, '2', 2, '3', 1) = decode(b.meet, '1', 1, '2', 2, '3', 1) AND A.RC_DATE = TO_DATE(?, 'YYYYMMDD') AND EXISTS (SELECT * FROM IPSRACE.RC_SELL WHERE MEET = A.MEET AND RC_DATE  = A.RC_DATE AND RC_NO = A.RC_NO AND SEL_MEET = ?) AND A.PR_GUBUN = '0' AND RTRIM(A.KIND) IN ('경주제외','출발제외','출주취소', '출전제외', '출전취소') AND A.PR_NO = B.HR_NO AND C.HR_NO = A.PR_NO AND C.ST_DATE = (SELECT MAX(ST_DATE) FROM IPSRACE.HR_TR WHERE MEET = B.MEET AND HR_NO = A.PR_NO AND ST_DATE &lt;= A.RC_DATE) ORDER BY 2,3)")
})
@Entity
public class Cancel extends BaseObject {
	
	@Id
    private int num;
    private String str01;
    private String str02;
    private String str03;
    private String str04;
    private String str05;
    private String str06;
    
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
    
}