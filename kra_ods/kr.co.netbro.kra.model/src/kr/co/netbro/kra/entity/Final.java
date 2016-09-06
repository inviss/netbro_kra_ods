package kr.co.netbro.kra.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

import kr.co.netbro.kra.method.BaseObject;

@SuppressWarnings("serial")
@NamedNativeQueries({
	//@NamedNativeQuery(name="Rate.Final", query="SELECT NUM , STR01 , STR02 , STR021 , STR03 , STR04 , STR05 , STR06 , STR07 , STR08 , STR09 , STR10 , STR11 , STR12 , STR13 , STR14 , STR15 , STR16 FROM (SELECT  Y.* , ROWNUM NUM FROM (SELECT X.RC_NO  STR01 , DECODE(X.MEET, '1', '서', '2', '제', '3', '부') STR02 , DECODE(X.NORACE_FLAG, '1', '취소', IPSRACE.GET_RESULT_123(X.MEET,X.RC_DATE,X.RC_NO)) STR021 , DECODE(X.NORACE_FLAG, '1', '취소', IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'QNL','H')) STR03 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'QNL','D')) STR04 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'EXA','H')) STR05 , DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'EXA','D')) STR06 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'QPL','H')) STR07 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'QPL','D')) STR08 ,DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'PLC','H')) STR09 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'PLC','D')) STR10, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'WIN','H')) STR11, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'WIN','D')) STR12 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'TLA','H')) STR13 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'TLA','D')) STR14 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'TRI','H')) STR15 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'TRI','D')) STR16 FROM (SELECT A.MEET , A.RC_DATE , A.RC_NO , DECODE(SUM(DECODE(A.ORD,99,1,98,1,0)),0,0,1) AS NORACE_FLAG , TO_CHAR(B.ST_TIME, 'HH24:MI') ST_TIME  FROM IPSRACE.RC_RESULT A , IPSRACE.RC_STAT   B , IPSRACE.RC_SELL   C WHERE A.MEET = B.MEET AND A.RC_DATE = B.RC_DATE AND A.RC_NO = B.RC_NO AND A.MEET = C.MEET AND A.RC_DATE = C.RC_DATE AND A.RC_NO = C.RC_NO AND A.RC_DATE = TO_DATE(?, 'YYYYMMDD') AND C.SEL_MEET = ? AND A.FINAL_BIT >= TO_NUMBER('1') AND IPSRACE.GET_RC_HEADHEAT(b.MEET,b.RC_DATE,b.RC_NO) = '1' GROUP BY A.MEET , A.RC_DATE , A.RC_NO, TO_CHAR(B.ST_TIME, 'HH24:MI')) X ORDER BY ST_TIME, MEET) Y)")
	@NamedNativeQuery(name="Rate.Final", query="SELECT NUM , STR01 , STR02 , STR021 , STR03 , STR04 , STR05 , STR06 , STR07 , STR08 , STR09 , STR10 , STR11 , STR12 , STR13 , STR14 , STR15 , STR16 FROM (SELECT  Y.* , ROWNUM NUM FROM (SELECT X.RC_NO  STR01 , DECODE(X.MEET, '1', '서', '2', '제', '3', '부') STR02 , DECODE(X.NORACE_FLAG, '1', '취소', IPSRACE.GET_RESULT_123(X.MEET,X.RC_DATE,X.RC_NO)) STR021 , DECODE(X.NORACE_FLAG, '1', '취소', IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'QNL','H')) STR03 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'QNL','D')) STR04 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'EXA','H')) STR05 , DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'EXA','D')) STR06 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'QPL','H')) STR07 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'QPL','D')) STR08 ,DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'PLC','H')) STR09 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'PLC','D')) STR10, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'WIN','H')) STR11, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'WIN','D')) STR12 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'TLA','H')) STR13 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'TLA','D')) STR14 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'TRI','H')) STR15 , DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_RESULT(X.MEET,X.RC_DATE,X.RC_NO,'TRI','D')) STR16 FROM (SELECT A.MEET , A.RC_DATE , A.RC_NO , DECODE(SUM(DECODE(A.ORD,99,1,98,1,0)),0,0,1) AS NORACE_FLAG , TO_CHAR(B.ST_TIME, 'HH24:MI') ST_TIME  FROM IPSRACE.RC_RESULT A , IPSRACE.RC_STAT   B , IPSRACE.RC_SELL   C WHERE A.MEET = B.MEET AND A.RC_DATE = B.RC_DATE AND A.RC_NO = B.RC_NO AND A.MEET = C.MEET AND A.RC_DATE = C.RC_DATE AND A.RC_NO = C.RC_NO AND A.RC_DATE = TO_DATE(?, 'YYYYMMDD') AND C.SEL_MEET = ? AND A.FINAL_BIT >= TO_NUMBER('1') AND IPSRACE.GET_RC_HEADHEAT(b.MEET,b.RC_DATE,b.RC_NO) = '1' GROUP BY A.MEET , A.RC_DATE , A.RC_NO, TO_CHAR(B.ST_TIME, 'HH24:MI')) X ORDER BY ST_TIME, MEET) Y)")
})
@Entity
public class Final extends BaseObject {
	
	@Id
    private int num;
    private String str01;
    private String str02;
    private String str03;
    private String str04;
    private String str05;
    private String str06;
    private String str07;
    private String str08;
    private String str09;
    private String str10;
    private String str11;
    private String str12;
    private String str13;
    private String str14;
    private String str15;
    private String str16;
    
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
	public String getStr08() {
		return str08;
	}
	public void setStr08(String str08) {
		this.str08 = str08;
	}
	public String getStr09() {
		return str09;
	}
	public void setStr09(String str09) {
		this.str09 = str09;
	}
	public String getStr10() {
		return str10;
	}
	public void setStr10(String str10) {
		this.str10 = str10;
	}
	public String getStr11() {
		return str11;
	}
	public void setStr11(String str11) {
		this.str11 = str11;
	}
	public String getStr12() {
		return str12;
	}
	public void setStr12(String str12) {
		this.str12 = str12;
	}
	public String getStr13() {
		return str13;
	}
	public void setStr13(String str13) {
		this.str13 = str13;
	}
	public String getStr14() {
		return str14;
	}
	public void setStr14(String str14) {
		this.str14 = str14;
	}
	public String getStr15() {
		return str15;
	}
	public void setStr15(String str15) {
		this.str15 = str15;
	}
	public String getStr16() {
		return str16;
	}
	public void setStr16(String str16) {
		this.str16 = str16;
	}
    
}