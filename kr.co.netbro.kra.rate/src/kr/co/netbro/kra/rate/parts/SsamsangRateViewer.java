package kr.co.netbro.kra.rate.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import kr.co.netbro.kra.model.RaceInfo;

public class SsamsangRateViewer extends RateViewer {
	
	@Override
	public String[][] makeData(RaceInfo raceInfo) {
		char[] data = raceInfo.getRaceinfo();
		
		String s = new String(data);
		String[] line = s.split("\r\n");
		String[][] rateData = new String[line.length - 1][];

		//int horseNum = (data[7] - '0') * 10 + (data[8] - '0');

		this.row = 17;
		this.column = 16;

		int offset = 27;
		for (int i = 0; i < rateData.length; i++) {
			for (int j = 0; j < this.row; i++) {
				rateData[i] = new String[this.column];
				for (int k = 0; k < this.column; k++) {
					rateData[i][k] = new String(data, offset, 2);

					offset += 3;
					if (rateData[i][(k++)].startsWith("#")) {
						rateData[i][k] = new String(data, offset, 5);
					} else {
						rateData[i][k] = toRate(data, offset, 5);
					}
					offset += 6;
				}
				offset += 2;
				j++;
			}
			
			if (rateData.length > i) {
				rateData[i] = new String[0];
				offset += 2;
			}
		}
		
		setWidth((rateData.length / (this.row + 1) + 1) * (this.column * 30) + 50);
		setHeight(200);
		
		return rateData;
	}
	
	@Override
	public void paintBody(GC gc, String[][] rateData, RaceInfo raceInfo) {
		int vgap = 22;

		int x = 0;
		int y = 0;

		FontMetrics fm = gc.getFontMetrics();
		for (int i = 0; i < rateData.length; i++) {
			x = 30 + i / (this.row + 1) * (this.column * 35);
			y = this.Y_POINT + vgap + i % (this.row + 1) * vgap;
			for (int j = 0; j < rateData[i].length; j++) {
				gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
				
				boolean isRate = false;
				if (!rateData[i][j].startsWith("#")) {
					gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
					gc.drawString(rateData[i][j], x, y);
					isRate = true;
				}
				x += 20;

				j++;
				String s = rateData[i][j];
				
				if (s.equals(raceInfo.getMinimum())) {
					gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_YELLOW));
					gc.fillRectangle(x - 7, y - 3, 40, vgap - 4);
				}
				
				gc.setForeground(isRate ? Display.getDefault().getSystemColor(SWT.COLOR_BLACK) : Display.getDefault().getSystemColor(SWT.COLOR_RED));
				
				x += 45;
				gc.drawString(s, x - fm.getAverageCharWidth() - 35, y);
			}
		}
	}
	
}
