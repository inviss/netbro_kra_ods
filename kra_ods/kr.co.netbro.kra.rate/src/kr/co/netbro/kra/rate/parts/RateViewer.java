package kr.co.netbro.kra.rate.parts;

import kr.co.netbro.kra.model.RaceInfo;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RateViewer {

	final Logger logger = LoggerFactory.getLogger(getClass());

	//protected Color cr = new Color(Display.getCurrent(), 255, 100, 100);
	//protected Color cg = new Color(Display.getCurrent(), 0, 200, 0);
	//protected Color cb = new Color(Display.getCurrent(), 100, 100, 255);
	//protected Color bl = new Color(Display.getCurrent(), 0, 0, 0);
	//protected Color ye = new Color(Display.getCurrent(), 255, 255, 0);

	protected int X_POINT = 30;
	protected int Y_POINT = 100;

	protected int row;
	protected int column;

	public FontRegistry fontRegistry = new FontRegistry(Display.getCurrent());

	public RateViewer() {
		fontRegistry.put("code", new FontData[]{new FontData("Dialog", 10, SWT.NORMAL)});
	}
	
	public void dispose() {
		//if(cr != null) cr.dispose();
		//if(cg != null) cg.dispose();
		//if(cb != null) cb.dispose();
		//if(bl != null) bl.dispose();
		//if(ye != null) ye.dispose();
	}

	public void paintHeader(PaintEvent e, RaceInfo raceInfo) {
		GC gc = e.gc;
		if(raceInfo != null) {
			gc.setForeground(e.display.getSystemColor(SWT.COLOR_YELLOW));
			gc.setFont(fontRegistry.get("code"));
			gc.drawString(raceInfo.getZoneName()+" 제 "+raceInfo.getRaceNum()+" 경주 "+raceInfo.getTypeName(), X_POINT, 30);
			String timeStr = "";
			if(raceInfo.getTime().equals("xx")) {
				timeStr = "마감";
			} else {
				if(raceInfo.getTime().equals("yy")) {
					timeStr = "분전";
				} else {
					timeStr = "마감 "+raceInfo.getTime()+"분전";
				}
			}
			gc.drawString(timeStr, X_POINT, 50);
			gc.drawString("매출 "+raceInfo.getMoney()+"원", X_POINT, 70);
		}
	}

	public void paintBody(PaintEvent e, String[][] rateData, RaceInfo raceInfo) {
		GC gc = e.gc;
		if(raceInfo != null) {
			int hgap = 45;
			int vgap = 20;
			int max = raceInfo.getHorseNum()+1;

			FontMetrics fm = gc.getFontMetrics();
			for (int i = 0; i < rateData.length; i++) {
				for(int j = 0; j < max; j++) {
					String s = rateData[i][j];
					if(s != null) {
						if(i == 0 || j == 0 || (raceInfo.getGameType() > 2 && i == j)) {
							if (i == 0) {
								gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLUE));
							} else if (j == 0) {
								gc.setForeground(e.display.getSystemColor(SWT.COLOR_RED));
							} else {
								gc.setForeground(e.display.getSystemColor(SWT.COLOR_GREEN));
							}
							int tx = (hgap - fm.getAverageCharWidth()) / 2;
							gc.drawString(s, X_POINT + i * hgap + tx, Y_POINT + j * vgap);
						} else {
							if(s.equals(raceInfo.getMinimum())) {
								gc.setForeground(e.display.getSystemColor(SWT.COLOR_YELLOW));
								gc.fillRectangle(X_POINT + i * hgap + 5, Y_POINT + j * vgap - 13, hgap - 2, vgap - 2);
							}
							gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));
							gc.drawString(s, X_POINT + i * hgap, Y_POINT + j * vgap);
						}
					}
				}
			}
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
