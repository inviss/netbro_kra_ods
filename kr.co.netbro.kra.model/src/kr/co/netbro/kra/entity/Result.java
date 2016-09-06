package kr.co.netbro.kra.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

import kr.co.netbro.kra.method.BaseObject;

@SuppressWarnings("serial")
@NamedNativeQueries({
	//@NamedNativeQuery(name="", query="SELECT NUM, RC , ORD1 , ORD2 , ORD3 , ORD4 , ORD5 , ORD22, ORD33, ORD44, ORD55, WIN , PLC1, PLC2, PLC3, QNL , EXA , QPL1, QPL2, QPL3, TLA, TRI, TO_CHAR(RC_DATE,'YYYY.MM.DD')||' ('||TO_CHAR(RC_DATE,'DY')||')'  DT, WATER FROM (SELECT ROWNUM AS NUM, DECODE(X.MEET, '1', '서', '2', '제', '3', '부')|| '-'||X.RC_NO RC, IPSRACE.GET_RC_ORD (X.MEET,X.RC_DATE,X.RC_NO,1) ORD1, IPSRACE.GET_RC_ORD (X.MEET,X.RC_DATE,X.RC_NO,2) ORD2, IPSRACE.GET_RC_ORD (X.MEET,X.RC_DATE,X.RC_NO,3) ORD3, IPSRACE.GET_RC_ORD (X.MEET,X.RC_DATE,X.RC_NO,4) ORD4, IPSRACE.GET_RC_ORD (X.MEET,X.RC_DATE,X.RC_NO,5) ORD5, IPSRACE.GET_RC_DIFF(X.MEET,X.RC_DATE,X.RC_NO,2) ORD22, IPSRACE.GET_RC_DIFF(X.MEET,X.RC_DATE,X.RC_NO,3) ORD33, IPSRACE.GET_RC_DIFF(X.MEET,X.RC_DATE,X.RC_NO,4) ORD44, IPSRACE.GET_RC_DIFF(X.MEET,X.RC_DATE,X.RC_NO,5) ORD55, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_NEW(X.MEET,X.RC_DATE,X.RC_NO,'WIN')) WIN, DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_ODDS_ORD(X.MEET,X.RC_DATE,X.RC_NO,'PLC', 1)) PLC1, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_ORD(X.MEET,X.RC_DATE,X.RC_NO,'PLC', 2)) PLC2, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_ORD(X.MEET,X.RC_DATE,X.RC_NO,'PLC', 3)) PLC3, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_NEW(X.MEET,X.RC_DATE,X.RC_NO,'QNL')) QNL, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_NEW(X.MEET,X.RC_DATE,X.RC_NO,'EXA')) EXA, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_ORD(X.MEET,X.RC_DATE,X.RC_NO,'QPL', 1)) QPL1 , DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_ORD(X.MEET,X.RC_DATE,X.RC_NO,'QPL', 2)) QPL2, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_ORD(X.MEET,X.RC_DATE,X.RC_NO,'QPL', 3)) QPL3, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_NEW(X.MEET,X.RC_DATE,X.RC_NO,'TLA')) TLA, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_NEW(X.MEET,X.RC_DATE,X.RC_NO,'TRI')) TRI, X.RC_DATE, (SELECT /*+INDEX_DESC(A IDX_RC_STAT_PK)*/ WATER_RATE FROM RC_STAT A WHERE MEET = X.MEET AND RC_DATE = TRUNC(SYS_DATE) AND WATER_RATE > 0 AND ROWNUM = 1) WATER FROM (SELECT A.MEET, A.RC_DATE, A.RC_NO, DECODE(SUM(DECODE(A.ORD, 99, 1, 98, 1, 0)), 0, 0, 1) AS NORACE_FLAG, TO_CHAR(B.ST_TIME, 'HH24:MI') ST_TIME FROM IPSRACE.RC_RESULT A, IPSRACE.RC_STAT B WHERE A.MEET = B.MEET AND A.RC_DATE = B.RC_DATE AND A.RC_NO = B.RC_NO AND IPSRACE.GET_RC_HEADHEAT(B.MEET, B.RC_DATE, B.RC_NO) = '0' AND A.RC_DATE = (SELECT /*+INDEX_DESC(B IDX_RC_STAT_02)*/ B.RC_DATE FROM IPSRACE.RC_SELL A, IPSRACE.RC_STAT B WHERE A.SEL_MEET = ? AND B.MEET = A.MEET AND B.RC_DATE  = A.RC_DATE AND A.RC_DATE  = TO_DATE(?, 'YYYYMMDD') AND B.RC_NO = A.RC_NO AND ROWNUM = 1) AND EXISTS (SELECT * FROM IPSRACE.RC_SELLWHERE MEET = A.MEET AND RC_DATE  = A.RC_DATE AND RC_NO = A.RC_NO AND SEL_MEET = ?) AND A.FINAL_BIT >= TO_NUMBER('1')GROUP BY A.MEET , A.RC_DATE , A.RC_NO, TO_CHAR(B.ST_TIME, 'HH24:MI')) XORDER BY ST_TIME, MEET)")
	@NamedNativeQuery(name="Rate.Result", query="SELECT NUM, RC , ORD1 , ORD2 , ORD3 , ORD4 , ORD5 , ORD22, ORD33, ORD44, ORD55, WIN , PLC1, PLC2, PLC3, QNL , EXA , QPL1, QPL2, QPL3, TLA, TRI, TO_CHAR(RC_DATE,'YYYY.MM.DD')||' ('||TO_CHAR(RC_DATE,'DY')||')'  DT, WATER FROM (SELECT ROWNUM AS NUM, DECODE(X.MEET, '1', '서', '2', '제', '3', '부')|| '-'||X.RC_NO RC, IPSRACE.GET_RC_ORD (X.MEET,X.RC_DATE,X.RC_NO,1) ORD1, IPSRACE.GET_RC_ORD (X.MEET,X.RC_DATE,X.RC_NO,2) ORD2, IPSRACE.GET_RC_ORD (X.MEET,X.RC_DATE,X.RC_NO,3) ORD3, IPSRACE.GET_RC_ORD (X.MEET,X.RC_DATE,X.RC_NO,4) ORD4, IPSRACE.GET_RC_ORD (X.MEET,X.RC_DATE,X.RC_NO,5) ORD5, IPSRACE.GET_RC_DIFF(X.MEET,X.RC_DATE,X.RC_NO,2) ORD22, IPSRACE.GET_RC_DIFF(X.MEET,X.RC_DATE,X.RC_NO,3) ORD33, IPSRACE.GET_RC_DIFF(X.MEET,X.RC_DATE,X.RC_NO,4) ORD44, IPSRACE.GET_RC_DIFF(X.MEET,X.RC_DATE,X.RC_NO,5) ORD55, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_NEW(X.MEET,X.RC_DATE,X.RC_NO,'WIN')) WIN, DECODE(X.NORACE_FLAG, '1', NULL  , IPSRACE.GET_ODDS_ORD(X.MEET,X.RC_DATE,X.RC_NO,'PLC', 1)) PLC1, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_ORD(X.MEET,X.RC_DATE,X.RC_NO,'PLC', 2)) PLC2, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_ORD(X.MEET,X.RC_DATE,X.RC_NO,'PLC', 3)) PLC3, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_NEW(X.MEET,X.RC_DATE,X.RC_NO,'QNL')) QNL, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_NEW(X.MEET,X.RC_DATE,X.RC_NO,'EXA')) EXA, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_ORD(X.MEET,X.RC_DATE,X.RC_NO,'QPL', 1)) QPL1 , DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_ORD(X.MEET,X.RC_DATE,X.RC_NO,'QPL', 2)) QPL2, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_ORD(X.MEET,X.RC_DATE,X.RC_NO,'QPL', 3)) QPL3, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_NEW(X.MEET,X.RC_DATE,X.RC_NO,'TLA')) TLA, DECODE(X.NORACE_FLAG, '1', NULL, IPSRACE.GET_ODDS_NEW(X.MEET,X.RC_DATE,X.RC_NO,'TRI')) TRI, X.RC_DATE, (SELECT /*+INDEX_DESC(A IDX_RC_STAT_PK)*/ WATER_RATE FROM RC_STAT A WHERE MEET = X.MEET AND RC_DATE = TRUNC(SYS_DATE) AND WATER_RATE > 0 AND ROWNUM = 1) WATER FROM (SELECT A.MEET, A.RC_DATE, A.RC_NO, DECODE(SUM(DECODE(A.ORD, 99, 1, 98, 1, 0)), 0, 0, 1) AS NORACE_FLAG, TO_CHAR(B.ST_TIME, 'HH24:MI') ST_TIME FROM IPSRACE.RC_RESULT A, IPSRACE.RC_STAT B WHERE A.MEET = B.MEET AND A.RC_DATE = B.RC_DATE AND A.RC_NO = B.RC_NO AND IPSRACE.GET_RC_HEADHEAT(B.MEET, B.RC_DATE, B.RC_NO) = '0' AND A.RC_DATE = (SELECT /*+INDEX_DESC(B IDX_RC_STAT_02)*/ B.RC_DATE FROM IPSRACE.RC_SELL A, IPSRACE.RC_STAT B WHERE A.SEL_MEET = ? AND B.MEET = A.MEET AND B.RC_DATE  = A.RC_DATE AND A.RC_DATE  = TO_DATE(?, 'YYYYMMDD') AND B.RC_NO = A.RC_NO AND ROWNUM = 1) AND EXISTS (SELECT * FROM IPSRACE.RC_SELLWHERE MEET = A.MEET AND RC_DATE  = A.RC_DATE AND RC_NO = A.RC_NO AND SEL_MEET = ?) AND A.FINAL_BIT >= TO_NUMBER('1')GROUP BY A.MEET , A.RC_DATE , A.RC_NO, TO_CHAR(B.ST_TIME, 'HH24:MI')) XORDER BY ST_TIME, MEET)")
})
@Entity
public class Result extends BaseObject {
	
	@Id
    private int num;
    private String rc;
    private String ord01;
    private String ord02;
    private String ord03;
    private String ord04;
    private String ord05;
    private String ord22;
    private String ord33;
    private String ord44;
    private String ord55;
    
    private String win;
    private String plc1;
    private String plc2;
    private String plc3;
    private String qnl;
    private String exa;
    private String opl1;
    private String opl2;
    private String opl3;
    
    private String tla;
    private String tri;
    private String dt;
    private String water; // 현재 함수율
    
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getRc() {
		return rc;
	}
	public void setRc(String rc) {
		this.rc = rc;
	}
	public String getOrd01() {
		return ord01;
	}
	public void setOrd01(String ord01) {
		this.ord01 = ord01;
	}
	public String getOrd02() {
		return ord02;
	}
	public void setOrd02(String ord02) {
		this.ord02 = ord02;
	}
	public String getOrd03() {
		return ord03;
	}
	public void setOrd03(String ord03) {
		this.ord03 = ord03;
	}
	public String getOrd04() {
		return ord04;
	}
	public void setOrd04(String ord04) {
		this.ord04 = ord04;
	}
	public String getOrd05() {
		return ord05;
	}
	public void setOrd05(String ord05) {
		this.ord05 = ord05;
	}
	public String getOrd22() {
		return ord22;
	}
	public void setOrd22(String ord22) {
		this.ord22 = ord22;
	}
	public String getOrd33() {
		return ord33;
	}
	public void setOrd33(String ord33) {
		this.ord33 = ord33;
	}
	public String getOrd44() {
		return ord44;
	}
	public void setOrd44(String ord44) {
		this.ord44 = ord44;
	}
	public String getOrd55() {
		return ord55;
	}
	public void setOrd55(String ord55) {
		this.ord55 = ord55;
	}
	public String getWin() {
		return win;
	}
	public void setWin(String win) {
		this.win = win;
	}
	public String getPlc1() {
		return plc1;
	}
	public void setPlc1(String plc1) {
		this.plc1 = plc1;
	}
	public String getPlc2() {
		return plc2;
	}
	public void setPlc2(String plc2) {
		this.plc2 = plc2;
	}
	public String getPlc3() {
		return plc3;
	}
	public void setPlc3(String plc3) {
		this.plc3 = plc3;
	}
	public String getQnl() {
		return qnl;
	}
	public void setQnl(String qnl) {
		this.qnl = qnl;
	}
	public String getExa() {
		return exa;
	}
	public void setExa(String exa) {
		this.exa = exa;
	}
	public String getOpl1() {
		return opl1;
	}
	public void setOpl1(String opl1) {
		this.opl1 = opl1;
	}
	public String getOpl2() {
		return opl2;
	}
	public void setOpl2(String opl2) {
		this.opl2 = opl2;
	}
	public String getOpl3() {
		return opl3;
	}
	public void setOpl3(String opl3) {
		this.opl3 = opl3;
	}
	public String getTla() {
		return tla;
	}
	public void setTla(String tla) {
		this.tla = tla;
	}
	public String getTri() {
		return tri;
	}
	public void setTri(String tri) {
		this.tri = tri;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public String getWater() {
		return water;
	}
	public void setWater(String water) {
		this.water = water;
	}
    
}