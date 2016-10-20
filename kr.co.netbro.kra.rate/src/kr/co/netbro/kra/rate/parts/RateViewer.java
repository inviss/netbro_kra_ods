package kr.co.netbro.kra.rate.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.model.RaceInfo;
import kr.co.netbro.kra.rate.resource.Registries;

public class RateViewer {

	final Logger logger = LoggerFactory.getLogger(getClass());

	protected int X_POINT = 30;
	protected int Y_POINT = 100;

	protected int row;
	protected int column;
	
	public int width = 0;
	public int height = 0;

	public void paintHeader(GC gc, RaceInfo raceInfo) {
		if(raceInfo != null) {
			gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			gc.setFont(Registries.getInstance().getFont("dialog10"));
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

	public void paintBody(GC gc, String[][] rateData, RaceInfo raceInfo) {
		if(raceInfo != null) {
			int hgap = 45;
			int vgap = 22;
			int max = raceInfo.getHorseNum()+1;

			FontMetrics fm = gc.getFontMetrics();
			for (int i = 0; i < rateData.length; i++) {
				for(int j = 0; j < max; j++) {
					gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
					String s = rateData[i][j];
					if(s != null) {
						if(i == 0 || j == 0 || (raceInfo.getGameType() > 2 && i == j)) {
							if (i == 0) {
								gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
							} else if (j == 0) {
								gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
							} else {
								gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
							}
							int tx = (hgap - fm.getAverageCharWidth()) / 2;
							gc.drawString(s, X_POINT + i * hgap + tx, Y_POINT + j * vgap);
						} else {
							if(s.equals(raceInfo.getMinimum())) {
								gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));
								gc.fillRectangle(X_POINT + i * hgap - 7, Y_POINT + j * vgap - 3, hgap - 2, vgap - 2);
							}
							gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
