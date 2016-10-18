package kr.co.netbro.kra.rate.parts;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.model.FinalInfo;
import kr.co.netbro.kra.model.MessageDef;
import kr.co.netbro.kra.model.RaceType;

public class FinalSceneViewer extends Canvas {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private FontRegistry fontRegistry;
	private ResourceManager resourceManager;

	//private final Color fg = new Color(Display.getDefault(), 180, 180, 230);
	//private final Color[] bg = { new Color(Display.getDefault(), 12, 38, 89), new Color(Display.getDefault(), 17, 3, 36), new Color(Display.getDefault(), 21, 41, 22) };
	//private final Color[] highlight = { new Color(Display.getDefault(), 22, 64, 172), new Color(Display.getDefault(), 60, 30, 120), new Color(Display.getDefault(), 50, 120, 50) };
	//private static final String[][] NAME = { MessageDef.FINAL_TYPE_BOK, MessageDef.FINAL_TYPE_SSANG, MessageDef.FINAL_TYPE_BOKYON, MessageDef.FINAL_TYPE_SAMBOK, MessageDef.FINAL_TYPE_SAMSSANG };
	//private static final Color[] NAME_COLOR = { ColorDef.BLUE, ColorDef.ORANGE, ColorDef.GREEN, ColorDef.YELLOW, ColorDef.GREEN2 };
	private final Color[] NAME_COLOR = { new Color(Display.getDefault(), 120, 255, 255), new Color(Display.getDefault(), 255, 186, 2), 
			new Color(Display.getDefault(), 173, 255, 107), new Color(Display.getDefault(), 255, 240, 24), new Color(Display.getDefault(), 27, 229, 0) };

	private FinalInfo finalInfo = null;

	public FinalSceneViewer(Composite parent) {
		super(parent, SWT.NONE);
		resourceManager = new LocalResourceManager(JFaceResources.getResources(), this);

		fontRegistry = new FontRegistry(Display.getDefault());
		//fontRegistry.put("tv7", resourceManagercreateFont(FontDescriptor.createFrom("tvtest", 7, SWT.NONE));
		fontRegistry.put("tv10", new FontData[]{new FontData("tvtest", 10, SWT.NONE)});
		fontRegistry.put("tv12", new FontData[]{new FontData("tvtest", 12, SWT.NONE)});
		fontRegistry.put("tv15", new FontData[]{new FontData("tvtest", 15, SWT.NONE)});
		fontRegistry.put("tv17", new FontData[]{new FontData("tvtest", 17, SWT.NONE)});
		fontRegistry.put("tv18", new FontData[]{new FontData("tvtest", 18, SWT.NONE)});
		fontRegistry.put("tv19", new FontData[]{new FontData("tvtest", 19, SWT.NONE)});
		fontRegistry.put("tv20", new FontData[]{new FontData("tvtest", 20, SWT.NONE)});
		fontRegistry.put("tv21", new FontData[]{new FontData("tvtest", 21, SWT.NONE)});
		fontRegistry.put("tv22", new FontData[]{new FontData("tvtest", 22, SWT.NONE)});
		fontRegistry.put("tv23", new FontData[]{new FontData("tvtest", 23, SWT.NONE)});
		fontRegistry.put("tv24", new FontData[]{new FontData("tvtest", 24, SWT.NONE)});
		fontRegistry.put("tv25", new FontData[]{new FontData("tvtest", 25, SWT.NONE)});
		fontRegistry.put("tv26", new FontData[]{new FontData("tvtest", 26, SWT.NONE)});
		fontRegistry.put("tv27", new FontData[]{new FontData("tvtest", 27, SWT.NONE)});
		fontRegistry.put("tv28", new FontData[]{new FontData("tvtest", 28, SWT.NONE)});
		fontRegistry.put("tv29", new FontData[]{new FontData("tvtest", 29, SWT.NONE)});
		fontRegistry.put("tv30", new FontData[]{new FontData("tvtest", 30, SWT.NONE)});
		fontRegistry.put("tv32", new FontData[]{new FontData("tvtest", 32, SWT.NONE)});
		fontRegistry.put("tv33", new FontData[]{new FontData("tvtest", 33, SWT.NONE)});
		fontRegistry.put("tv34", new FontData[]{new FontData("tvtest", 34, SWT.NONE)});
		fontRegistry.put("tv36", new FontData[]{new FontData("tvtest", 36, SWT.NONE)});
		fontRegistry.put("tv38", new FontData[]{new FontData("tvtest", 38, SWT.NONE)});
		fontRegistry.put("tv40", new FontData[]{new FontData("tvtest", 40, SWT.NONE)});
		fontRegistry.put("tv42", new FontData[]{new FontData("tvtest", 42, SWT.NONE)});
		fontRegistry.put("tv44", new FontData[]{new FontData("tvtest", 44, SWT.NONE)});
		fontRegistry.put("tv46", new FontData[]{new FontData("tvtest", 46, SWT.NONE)});
		fontRegistry.put("tv48", new FontData[]{new FontData("tvtest", 48, SWT.NONE)});
		fontRegistry.put("tv50", new FontData[]{new FontData("tvtest", 50, SWT.NONE)});
		fontRegistry.put("tv52", new FontData[]{new FontData("tvtest", 52, SWT.NONE)});
		fontRegistry.put("ts16", new FontData[]{new FontData("tsTv95", 16, SWT.NONE)});
		fontRegistry.put("ts18", new FontData[]{new FontData("tsTv95", 18, SWT.NONE)});
		fontRegistry.put("ts20", new FontData[]{new FontData("tsTv95", 20, SWT.NONE)});
		fontRegistry.put("ts22", new FontData[]{new FontData("tsTv95", 22, SWT.NONE)});
		fontRegistry.put("ts26", new FontData[]{new FontData("tsTv95", 26, SWT.NONE)});

		setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				paintHeaderAndBody(e.gc);
			}
		});
	}

	public FinalInfo getFinalInfo() {
		checkWidget();
		return finalInfo;
	}

	public void setFinalInfo(FinalInfo finalInfo) {
		checkWidget();
		this.finalInfo = finalInfo;
		redraw();
	}

	private void paintHeaderAndBody(GC gc) {
		String[] dan = getFinalInfo().getResult().get(RaceType.DAN).split("\\|"); // 단승
		String[] yon = getFinalInfo().getResult().get(RaceType.YON).split("\\|"); // 연승

		/*
		 * 1~3위까지 단.연승식의 마번 및 배당률을 할당한다.
		 * '0' 이 아닐경우에만 추가한다.
		 */
		List<String> tmpList = new ArrayList<String>();
		if(StringUtils.isNotBlank(getFinalInfo().getFirstDone()) && !getFinalInfo().getFirstDone().equals("0")) {
			tmpList.add(getFinalInfo().getFirstDone());
		}
		if(StringUtils.isNotBlank(getFinalInfo().getSecondDone()) && !getFinalInfo().getSecondDone().equals("0")) {
			tmpList.add(getFinalInfo().getSecondDone());
		}
		if(StringUtils.isNotBlank(getFinalInfo().getThirdDone()) && !getFinalInfo().getThirdDone().equals("0")) {
			tmpList.add(getFinalInfo().getThirdDone());
		}
		String[] rateT = tmpList.toArray(new String[0]);

		List<String[]> danYonList = new ArrayList<String[]>();
		for(int i=0; i<rateT.length; i++) {
			String[] t = rateT[i].split("\\|");

			String[] rateArr = new String[4];
			for(int j=0; j<t.length; j++) {
				rateArr[0] = String.valueOf(i+1);
				rateArr[1] = t[j];
				if(getFinalInfo().getResult().get(RaceType.DAN).equals("1|0|0.8")) {
					rateArr[2] = "0.8";
				} else {
					rateArr[2] = rateFromDanYon(dan, t[j]);
				}
				if(getFinalInfo().getResult().get(RaceType.YON).equals("1|0|0.8")) {
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
		List<String[]> bokList = doubleData(RaceType.BOK, getFinalInfo().getResult(), false);
		List<String[]> ssangList = doubleData(RaceType.SSANG, getFinalInfo().getResult(), false);
		List<String[]> bokyonList = doubleData(RaceType.BOKYON, getFinalInfo().getResult(), false);
		List<String[]> sambokList = doubleData(RaceType.SAMBOK, getFinalInfo().getResult(), false);
		List<String[]> samssangList = doubleData(RaceType.SAMSSANG, getFinalInfo().getResult(), false);

		/*************************************** GC drawing start *********************************************/

		Color bgColor = null;
		switch(getFinalInfo().getZone()) {
		case 1:
			bgColor = resourceManager.createColor(new RGB(12, 38, 89));
			break;
		case 2:
			bgColor = resourceManager.createColor(new RGB(17, 3, 36));
			break;
		case 3:
			bgColor = resourceManager.createColor(new RGB(21, 41, 22));
			break;
		}
		setBackground(bgColor); // 경기장별 바탕색 지정

		// 경기장별 바탕 이미지 설정
		gc.drawImage(loadImage("images/final_"+finalInfo.getZone()+".jpg"), 960, 540);

		// TOP 경기번호 폰트
		gc.setFont(fontRegistry.get("tv46"));

		// 경기번호 (1)
		drawStringRight(gc, String.valueOf(getFinalInfo().getRace()), (960 / 2) - 20, 70);
		// "경주", "(Race)"
		drawStringCenterTitle(gc, new Font[]{fontRegistry.get("tv38"), fontRegistry.get("tv18")}, MessageDef.RACE, (960 / 2) + 80, 65);

		if(getFinalInfo().isFinal()) {
			drawStringRightTitle(gc, new Font[]{fontRegistry.get("tv34"), fontRegistry.get("tv18")}, MessageDef.FINAL_OFFICIAL_DIVIDENDS, 790, 56);
		} else {
			drawStringRightTitle(gc, new Font[]{fontRegistry.get("tv24"), fontRegistry.get("tv16")}, MessageDef.FINAL_UNOFFICIAL_DIVIDENDS, 800, 43);
			drawStringRightTitle(gc, new Font[]{fontRegistry.get("tv10"), fontRegistry.get("tv10")}, MessageDef.FINAL_UNOFFICIAL_DIVIDENDS_DETAIL, 900, 62);
		}

		if(bokList.isEmpty() && ssangList.isEmpty() && bokyonList.isEmpty() && sambokList.isEmpty() && samssangList.isEmpty()) {
			String[] ZONE_NAME = { MessageDef.REGION_SEOUL[MessageDef.KOR], MessageDef.REGION_JEJU[MessageDef.KOR], MessageDef.REGION_PUSAN[MessageDef.KOR] };
			gc.setFont(fontRegistry.get("tv36"));
			gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

			drawStringCenter(gc, ZONE_NAME[getFinalInfo().getZone()] + " "+ getFinalInfo().getRace() + MessageDef.FINAL_CANCELD_1[MessageDef.KOR], 960 / 2, 540 / 2);
			drawStringCenter(gc, MessageDef.FINAL_CANCELD_2[MessageDef.KOR], 960 / 2, 540 / 2 + 50);

			return;
		}

		// 1 ~ 3위가 존재한다면
		if(rateT != null && rateT.length > 0) {
			drawStringCenterTitle(gc, new Font[]{fontRegistry.get("tv36"), fontRegistry.get("tv18")}, new Color[] {getDisplay().getSystemColor(SWT.COLOR_GREEN), getDisplay().getSystemColor(SWT.COLOR_GREEN)}, MessageDef.FINAL_PLACING, 164, 110);

			drawStringCenterTitle(gc, new Font[]{fontRegistry.get("tv36"), fontRegistry.get("tv18")}, new Color[] {getDisplay().getSystemColor(SWT.COLOR_BLUE), getDisplay().getSystemColor(SWT.COLOR_BLUE)}, MessageDef.FINAL_TYPE_DAN, 580, 110);

			drawStringCenterTitle(gc, new Font[]{fontRegistry.get("tv36"), fontRegistry.get("tv18")}, new Color[] {getDisplay().getSystemColor(SWT.COLOR_BLUE), getDisplay().getSystemColor(SWT.COLOR_BLUE)}, MessageDef.FINAL_TYPE_YON, 860, 110);
		}
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
				t = new String[5];

				t[0] = strArr[index++];
				t[1] = strArr[index++];
				t[2] = strArr[index++];
				t[3] = t[0]+"-"+t[1]+"-"+t[2];
				t[4] = strArr[index++];
				if(logger.isDebugEnabled()) {
					logger.debug(raceType.getName()+" - t[0]: "+t[0]+", t[1]: "+t[1]+", t[2]: "+t[2]+", t[3]: "+t[3]+", t[4]: "+t[4]);
				}
			} else {
				t = new String[4];

				t[0] = strArr[index++];
				t[1] = strArr[index++];
				t[2] = t[0]+"-"+t[1];
				t[3] = strArr[index++];
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

	protected void drawStringRight(GC gc, String str, int x, int y) {
		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		if ((str != null) && (str.length() > 0)) {
			Point pt = gc.stringExtent(str);
			gc.drawString(str, x - pt.x, y - pt.y);
		}
	}

	protected void drawStringRightTitle(GC gc, Font[] fonts, String[] message, int x, int y) {
		gc.setFont(fonts[0]);
		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_YELLOW));

		Point pt = gc.stringExtent(message[0]);
		gc.drawString(message[0], x - pt.x, y - pt.y);

		if(StringUtils.isNotBlank(message[1])) {
			gc.setFont(fonts[1]);
			gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_YELLOW));

			pt = gc.stringExtent(message[1]);
			gc.drawString(message[1], x, y - pt.y);
		}
	}

	protected void drawStringCenterTitle(GC gc, Font[] font, Color[] color, String[] message, int x, int y) {
		gc.setFont(font[0]);
		gc.setForeground(color[0]);

		Point pt = gc.stringExtent(message[0]);
		gc.drawString(message[0], x - pt.x, y - pt.y);

		gc.setFont(font[1]);
		gc.setForeground(color[1]);

		pt = gc.stringExtent(message[1]);
		gc.drawString(message[1], x, y - pt.y);
	}
	
	protected void drawStringCenterTitle(GC gc, Font[] font, String[] message, int x, int y) {
		gc.setFont(font[0]);
		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		Point pt = gc.stringExtent(message[0]);
		gc.drawString(message[0], x - pt.x, y - pt.y);

		gc.setFont(font[1]);
		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		pt = gc.stringExtent(message[1]);
		gc.drawString(message[1], x, y - pt.y);
	}

	protected void drawStringCenter(GC gc, String message, int x, int y) {
		gc.drawString(message, x - gc.getFontMetrics().getAverageCharWidth() / 2 , y);
	}

	private Image loadImage(String url) {
		Image image = null;
		try {
			Bundle bundle = FrameworkUtil.getBundle(getClass());
			URL imgUrl = FileLocator.find(bundle, new Path(url), null);
			ImageDescriptor imgDescriptor = ImageDescriptor.createFromURL(imgUrl);
			image = resourceManager.createImage(imgDescriptor);
		} catch (Exception e) {
			logger.error("Image Load error", e);
		}

		return image;
	}

}
