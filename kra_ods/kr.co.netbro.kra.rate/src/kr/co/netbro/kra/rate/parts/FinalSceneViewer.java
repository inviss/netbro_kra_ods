package kr.co.netbro.kra.rate.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.model.DecidedRate;
import kr.co.netbro.kra.model.MessageDef;
import kr.co.netbro.kra.model.RaceType;
import kr.co.netbro.kra.rate.resource.Registries;

public class FinalSceneViewer extends Canvas {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private DecidedRate finalInfo = null;
	private final int WIDHT = 960;
	private final int HEIGHT = 620;
	
	public FinalSceneViewer(Composite parent) {
		super(parent, SWT.NONE);
		//setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				paintHeaderAndBody(e.gc);
			}
		});
	}

	public DecidedRate getDecidedRate() {
		checkWidget();
		return finalInfo;
	}

	public void setDecidedRate(DecidedRate finalInfo) {
		checkWidget();
		this.finalInfo = finalInfo;
		redraw();
	}

	private void paintHeaderAndBody(GC gc) {
		
		Color WHITE 	= Registries.getInstance().getColor("white");
		Color YELLOW 	= Registries.getInstance().getColor("yellow");
		Color GREEN 	= Registries.getInstance().getColor("green");
		Color BLUE 		= Registries.getInstance().getColor("blue");
		Color ORANGE 	= Registries.getInstance().getColor("orange");
		Color PINK 		= Registries.getInstance().getColor("pink");
		Color GREEN2 	= Registries.getInstance().getColor("green2");
		
		String[] dan = getDecidedRate().getResult().get(RaceType.DAN).split("\\|"); // 단승
		String[] yon = getDecidedRate().getResult().get(RaceType.YON).split("\\|"); // 연승

		/*
		 * 1~3위까지 단.연승식의 마번 및 배당률을 할당한다.
		 * '0' 이 아닐경우에만 추가한다.
		 */
		List<String> tmpList = new ArrayList<String>();
		if(StringUtils.isNotBlank(getDecidedRate().getFirstDone()) && !getDecidedRate().getFirstDone().equals("0")) {
			tmpList.add(getDecidedRate().getFirstDone());
		}
		if(StringUtils.isNotBlank(getDecidedRate().getSecondDone()) && !getDecidedRate().getSecondDone().equals("0")) {
			tmpList.add(getDecidedRate().getSecondDone());
		}
		if(StringUtils.isNotBlank(getDecidedRate().getThirdDone()) && !getDecidedRate().getThirdDone().equals("0")) {
			tmpList.add(getDecidedRate().getThirdDone());
		}
		String[] rateT = tmpList.toArray(new String[0]);

		List<String[]> danYonList = new ArrayList<String[]>();
		for(int i=0; i<rateT.length; i++) {
			String[] t = rateT[i].split("\\|");

			String[] rateArr = new String[4];
			for(int j=0; j<t.length; j++) {
				rateArr[0] = String.valueOf(i+1);
				rateArr[1] = t[j];
				if(getDecidedRate().getResult().get(RaceType.DAN).equals("1|0|0.8")) {
					rateArr[2] = "0.8";
				} else {
					rateArr[2] = rateFromDanYon(dan, t[j]);
				}
				if(getDecidedRate().getResult().get(RaceType.YON).equals("1|0|0.8")) {
					rateArr[3] = "0.8";
				} else {
					rateArr[3] = rateFromDanYon(yon, t[j]);
				}

				danYonList.add(rateArr);
			}
		}

		/*
		 * 복, 쌍, 복연, 삼복, 삼쌍식에 대한 final 데이타를 추출한다.
		 * 주의: 페이징 처리를 지원해야할 듯...
		 */
		List<String[]> bokList = doubleData(RaceType.BOK, getDecidedRate().getResult(), false);
		List<String[]> ssangList = doubleData(RaceType.SSANG, getDecidedRate().getResult(), false);
		List<String[]> bokyonList = doubleData(RaceType.BOKYON, getDecidedRate().getResult(), false);
		List<String[]> sambokList = doubleData(RaceType.SAMBOK, getDecidedRate().getResult(), false);
		List<String[]> samssangList = doubleData(RaceType.SAMSSANG, getDecidedRate().getResult(), false);

		/*************************************** GC drawing start *********************************************/
		setBackground(Registries.getInstance().getColor("bg_"+getDecidedRate().getZone())); // 경기장별 바탕색 지정
		
		// 경기장별 바탕 이미지 설정
		Image image = Registries.getInstance().getImage("final_"+finalInfo.getZone());
		gc.drawImage(image, 0, 0);
		
		// TOP 경기번호 폰트
		gc.setFont(Registries.getInstance().getFont("tv46"));

		// 경기번호 (1)
		drawStringRight(gc, String.valueOf(getDecidedRate().getRace()), (WIDHT / 2) - 20, 70);
		// "경주", "(Race)"
		drawStringCenterTitle(gc, new Font[]{Registries.getInstance().getFont("tv38"), Registries.getInstance().getFont("tv18")}, MessageDef.RACE, (WIDHT / 2) + 80, 65);

		if(getDecidedRate().isFinal()) {
			drawStringRightTitle(gc, new Font[]{Registries.getInstance().getFont("tv34"), Registries.getInstance().getFont("tv18")}, new Color[]{YELLOW, YELLOW}, MessageDef.FINAL_OFFICIAL_DIVIDENDS, 790, 56);
		} else {
			drawStringRightTitle(gc, new Font[]{Registries.getInstance().getFont("tv24"), Registries.getInstance().getFont("tv15")}, new Color[]{YELLOW, YELLOW}, MessageDef.FINAL_UNOFFICIAL_DIVIDENDS, 800, 43);
			drawStringRightTitle(gc, new Font[]{Registries.getInstance().getFont("tv10"), Registries.getInstance().getFont("tv10")}, new Color[]{YELLOW, YELLOW}, MessageDef.FINAL_UNOFFICIAL_DIVIDENDS_DETAIL, 900, 62);
		}

		if(bokList.isEmpty() && ssangList.isEmpty() && bokyonList.isEmpty() && sambokList.isEmpty() && samssangList.isEmpty()) {
			String[] ZONE_NAME = { MessageDef.REGION_SEOUL[MessageDef.KOR], MessageDef.REGION_JEJU[MessageDef.KOR], MessageDef.REGION_PUSAN[MessageDef.KOR] };
			gc.setFont(Registries.getInstance().getFont("tv36"));
			gc.setForeground(WHITE);

			drawStringCenter(gc, ZONE_NAME[getDecidedRate().getZone()] + " "+ getDecidedRate().getRace() + MessageDef.FINAL_CANCELD_1[MessageDef.KOR], WIDHT / 2, HEIGHT / 2);
			drawStringCenter(gc, MessageDef.FINAL_CANCELD_2[MessageDef.KOR], WIDHT / 2, HEIGHT / 2 + 50);

			return;
		}

		// 1 ~ 3위가 존재한다면
		int y = 110;
		if(!danYonList.isEmpty()) {
			drawStringCenterTitle(gc, new Font[]{Registries.getInstance().getFont("tv28"), Registries.getInstance().getFont("tv15")}, new Color[] {GREEN, GREEN}, MessageDef.FINAL_PLACING, 164, 110);

			drawStringCenterTitle(gc, new Font[]{Registries.getInstance().getFont("tv28"), Registries.getInstance().getFont("tv15")}, new Color[] {BLUE, BLUE}, MessageDef.FINAL_TYPE_DAN, 580, 110);

			drawStringCenterTitle(gc, new Font[]{Registries.getInstance().getFont("tv28"), Registries.getInstance().getFont("tv15")}, new Color[] {BLUE, BLUE}, MessageDef.FINAL_TYPE_YON, 860, 110);
		}
		
		y = 155;
		for(int i=0; i < danYonList.size(); i++) {
			String[] plasing = null;
			String[] dy = danYonList.get(i);
			switch(dy[0]) {
			case "1" : 
				plasing = MessageDef.FINAL_1ST;
				break;
			case "2" :
				plasing = MessageDef.FINAL_2ND;
				break;
			case "3" :
				plasing = MessageDef.FINAL_3RD;
				break;
			}
			drawStringPlace(gc, new Font[]{Registries.getInstance().getFont("tv26"), Registries.getInstance().getFont("tv15"), Registries.getInstance().getFont("tv10")}, new Color[] {GREEN, GREEN}, plasing, 
					110, y);
			
			gc.setFont(Registries.getInstance().getFont("tv24"));
			gc.setForeground(WHITE);
			
			drawStringCenter(gc, dy[1], 320, y);
			
			drawStringRight(gc, dy[2], 600, y);
			drawStringRight(gc, dy[3], 880, y);
			
			y += 40;
		}
		
		y += 5;
		
		/*********** 복승 ****************/
		gc.setFont(Registries.getInstance().getFont("tv28"));
		drawStringCenterTitle(gc, new Font[]{Registries.getInstance().getFont("tv26"), Registries.getInstance().getFont("tv15")}, new Color[]{BLUE, BLUE}, MessageDef.FINAL_TYPE_BOK, 164, y);
		for(String[] bok : bokList) {
			if(true) { // split 값 체크 (페이징 처리인듯)
				drawStringRight(gc, BLUE, bok[2], 600, y);
				drawStringRight(gc, BLUE, bok[3], 880, y);
			}
		}
		
		y += 40;
		/*********** 쌍승 ****************/
		gc.setFont(Registries.getInstance().getFont("tv28"));
		drawStringCenterTitle(gc, new Font[]{Registries.getInstance().getFont("tv26"), Registries.getInstance().getFont("tv15")}, new Color[]{ORANGE, ORANGE}, MessageDef.FINAL_TYPE_SSANG, 164, y);
		for(String[] ssang : ssangList) {
			if(true) { // split 값 체크 (페이징 처리인듯)
				drawStringRight(gc, ORANGE, ssang[2], 600, y);
				drawStringRight(gc, ORANGE, ssang[3], 880, y);
			}
		}
		
		y += 40;
		/*********** 복연승 **************/
		gc.setFont(Registries.getInstance().getFont("tv28"));
		drawStringCenterTitle(gc, new Font[]{Registries.getInstance().getFont("tv26"), Registries.getInstance().getFont("tv15")}, new Color[]{GREEN, GREEN}, MessageDef.FINAL_TYPE_BOKYON, 164, y);
		for(int i = 0; i < bokyonList.size(); i++) {
			String[] bokyon = bokyonList.get(i);
			if(Integer.parseInt(bokyon[4]) == 1) { // split 값 체크 (페이징 처리인듯)
				drawStringRight(gc, GREEN, bokyon[2], 600, y);
				drawStringRight(gc, GREEN, bokyon[3], 880, y);
			} else {
				int x1 = i % 2 == 0 ? 340 : 640;
				int x2 = i % 2 == 0 ? 580 : 880;
				
				int yy = y + 38 * (i / 2);
				gc.setForeground(GREEN);
				
				Point pt = gc.stringExtent(bokyon[2]);
				gc.drawString(bokyon[2], x1 - pt.x, yy - pt.y, true);
				
				gc.setForeground(WHITE);
				drawStringRight(gc, bokyon[3], x2, yy);
			}
		}
		
		y += 80;
		/*********** 삼복승 **************/
		gc.setFont(Registries.getInstance().getFont("tv28"));
		gc.setForeground(YELLOW);
		drawStringCenterTitle(gc, new Font[]{Registries.getInstance().getFont("tv26"), Registries.getInstance().getFont("tv15")}, new Color[]{YELLOW, YELLOW}, MessageDef.FINAL_TYPE_SAMBOK, 164, y);
		
		for(int i = 0; i < sambokList.size(); i++) {
			String[] sambok = sambokList.get(i);
			if(Integer.parseInt(sambok[5]) == 1) {
				drawStringRight(gc, YELLOW, sambok[3], 600, y);
				drawStringRight(gc, YELLOW, sambok[4], 880, y);
			} else {
				int x1 = i % 2 == 0 ? 340 : 640;
				int x2 = i % 2 == 0 ? 580 : 880;
				
				int yy = y + 38 * (i / 2);
				gc.setForeground(YELLOW);
				
				Point pt = gc.stringExtent(sambok[3]);
				gc.drawString(sambok[4], x1 - pt.x, yy - pt.y, true);
				
				gc.setForeground(WHITE);
				drawStringRight(gc, sambok[3], x2, yy);
			}
		}
		
		y += 40;
		/*********** 삼쌍승 **************/
		gc.setFont(Registries.getInstance().getFont("tv28"));
		gc.setForeground(GREEN2);
		drawStringCenterTitle(gc, new Font[]{Registries.getInstance().getFont("tv26"), Registries.getInstance().getFont("tv15")}, new Color[]{GREEN2, GREEN2}, MessageDef.FINAL_TYPE_SAMSSANG, 164, y);
		
		for(int i = 0; i < samssangList.size(); i++) {
			String[] samssang = samssangList.get(i);
			if(Integer.parseInt(samssang[5]) == 1) {
				drawStringRight(gc, GREEN2, samssang[3], 600, y);
				drawStringRight(gc, GREEN2, samssang[4], 880, y);
			} else {
				int x1 = i % 2 == 0 ? 340 : 640;
				int x2 = i % 2 == 0 ? 580 : 880;
				
				int yy = y + 38 * (i / 2);
				gc.setForeground(GREEN);
				
				Point pt = gc.stringExtent(samssang[3]);
				gc.drawString(samssang[4], x1 - pt.x, yy - pt.y, true);
				
				gc.setForeground(WHITE);
				drawStringRight(gc, samssang[4], x2, yy);
			}
		}
		
		if(getDecidedRate().isMessage()) {
			Image overImage = null;
			if(StringUtils.isNotBlank(getDecidedRate().getCancel())) {
				overImage = Registries.getInstance().getImage("over_"+finalInfo.getZone());
				gc.drawImage(overImage, 0, 0, overImage.getBounds().width, overImage.getBounds().height, 500, 480, 440, 44);
			} else {
				overImage = Registries.getInstance().getImage("over_"+finalInfo.getZone());
				gc.drawImage(overImage, 0, 0, overImage.getBounds().width, overImage.getBounds().height, 100, 480, 760, 60);
			}
		}
		
		if(StringUtils.isNotBlank(getDecidedRate().getCancel())) {
			drawStringCenterTitle(gc, new Font[]{Registries.getInstance().getFont("tv26"), Registries.getInstance().getFont("tv15")}, new Color[]{PINK, PINK}, MessageDef.FINAL_SCRATCHED, 120, 500);
			gc.setFont(Registries.getInstance().getFont("tv28"));
			gc.setForeground(WHITE);
			gc.drawString(getDecidedRate().getCancel(), 230, 500, true);
		}

	}
	
	private void drawStringPlace(GC gc, Font[] font, Color[] color, String[] message, int x, int y) {
		gc.setFont(font[0]);
		gc.setForeground(color[0]);

		Point pt = gc.stringExtent(message[0]);
		gc.drawString(message[0], x, y - pt.y, true);

		gc.setFont(font[1]);
		gc.setForeground(color[1]);

		pt = gc.stringExtent(message[1]);
		gc.drawString(message[1].substring(0,  2), x + pt.x + 10, y - pt.y, true);
		
		gc.setFont(font[2]);
		gc.drawString(message[1].substring(2,  4), x + pt.x + 30, y - pt.y, true);
		
		gc.setFont(font[1]);
		gc.drawString(message[1].substring(4,  5), x + pt.x + 50, y - pt.y, true);
	}

	private List<String[]> doubleData(RaceType raceType, Map<RaceType, String> results, boolean split) {
		List<String[]> arrList = new ArrayList<String[]>();

		String[] strArr = results.get(raceType).split("\\|");
		int size = Integer.parseInt(strArr[0]);
		if(logger.isInfoEnabled()) {
			logger.info(raceType.getName()+" size: "+size);
		}

		int index = 1;
		for(int i=0; i < size; i++) {
			String[] t = null;
			if(raceType == RaceType.SAMBOK || raceType == RaceType.SAMSSANG) {
				t = new String[6];

				t[0] = strArr[index++];
				t[1] = strArr[index++];
				t[2] = strArr[index++];
				t[3] = t[0]+"-"+t[1]+"-"+t[2];
				t[4] = strArr[index++];
				t[5] = String.valueOf(split(size, false));
				if(logger.isDebugEnabled()) {
					logger.debug(raceType.getName()+" - t[0]: "+t[0]+", t[1]: "+t[1]+", t[2]: "+t[2]+", t[3]: "+t[3]+", t[4]: "+t[4]);
				}
			} else {
				t = new String[5];

				t[0] = strArr[index++];
				t[1] = strArr[index++];
				t[2] = t[0]+"-"+t[1];
				t[3] = strArr[index++];
				t[4] = String.valueOf(split(size, false));
				if(logger.isDebugEnabled()) {
					logger.debug(raceType.getName()+" - t[0]: "+t[0]+", t[1]: "+t[1]+", t[2]: "+t[2]+", t[3]: "+t[3]);
				}
			}
			arrList.add(t);
		}

		return arrList;
	}

	private int split(int size, boolean split) {
		if (size == 1) {
			return 1;
		}
		if (split) {
			return ((size + 1) / 2);
		} else {
			return size;
		}
	}

	private String rateFromDanYon(String[] line, String num) {
		for (int i = 1; i < line.length - 1; i += 2) {
			if (line[i].equals(num)) {
				return line[(i + 1)];
			}
		}
		return null;
	}

	private void drawStringRight(GC gc, String str, int x, int y) {
		gc.setForeground(Registries.getInstance().getColor("white"));
		if ((str != null) && (str.length() > 0)) {
			Point pt = gc.stringExtent(str);
			gc.drawString(str, x - pt.x, y - pt.y, true);
		}
	}
	
	private void drawStringRight(GC gc, Color color, String str, int x, int y) {
		gc.setForeground(color);
		if ((str != null) && (str.length() > 0)) {
			Point pt = gc.stringExtent(str);
			gc.drawString(str, x - pt.x, y - pt.y, true);
		}
	}

	private void drawStringRightTitle(GC gc, Font[] fonts, Color[] colors, String[] message, int x, int y) {
		gc.setFont(fonts[0]);
		gc.setForeground(colors[0]);

		Point pt = gc.stringExtent(message[0]);
		gc.drawString(message[0], x - pt.x, y - pt.y, true);

		if(StringUtils.isNotBlank(message[1])) {
			gc.setFont(fonts[1]);
			gc.setForeground(colors[1]);

			pt = gc.stringExtent(message[1]);
			gc.drawString(message[1], x, y - pt.y, true);
		}
	}

	private void drawStringCenterTitle(GC gc, Font[] font, Color[] color, String[] message, int x, int y) {
		gc.setFont(font[0]);
		gc.setForeground(color[0]);

		Point pt = gc.stringExtent(message[0]);
		gc.drawString(message[0], x - pt.x, y - pt.y, true);

		gc.setFont(font[1]);
		gc.setForeground(color[1]);

		pt = gc.stringExtent(message[1]);
		gc.drawString(message[1], x, y - pt.y, true);
	}
	
	private void drawStringCenterTitle(GC gc, Font[] font, String[] message, int x, int y) {
		gc.setFont(font[0]);
		gc.setForeground(Registries.getInstance().getColor("white"));

		Point pt = gc.stringExtent(message[0]);
		gc.drawString(message[0], x - pt.x, y - pt.y, true);

		gc.setFont(font[1]);
		gc.setForeground(Registries.getInstance().getColor("white"));

		pt = gc.stringExtent(message[1]);
		gc.drawString(message[1], x, y - pt.y, true);
	}

	private void drawStringCenter(GC gc, String message, int x, int y) {
		Point pt = gc.stringExtent(message);
		gc.drawString(message, x - pt.x , y - pt.y, true);
	}

}
