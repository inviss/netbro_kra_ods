package kr.co.netbro.kra.rate.parts;

import kr.co.netbro.kra.model.RaceInfo;

import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;

public class TopRateViewer extends RateViewer {

	@Override
	public String[][] makeData(RaceInfo raceInfo) {
		String s = new String(raceInfo.getRaceinfo());
		String[] line = s.split("\r\n");
		String[][] rateData = new String[line.length - 1][];

		for (int i = 0; i < rateData.length; i++) {
			rateData[i] = line[(i + 1)].trim().split("( )+");
			rateData[i][3] = toRate(rateData[i][3].toCharArray(), 0, 5);
		}
		return rateData;
	}

	@Override
	public void paintBody(GC gc, String[][] rateData, RaceInfo raceInfo) {
		int vgap = 20;
		row = 15;
		FontMetrics fm = gc.getFontMetrics();
		for (int i = 0; i < rateData.length; i++) {
			int x = i / row * 150 + X_POINT;
			int y = this.Y_POINT + vgap + i % this.row * vgap;

			gc.setForeground(cb);
			gc.drawString(rateData[i][0], x + 10, y);
			gc.drawString(rateData[i][1], x + 30, y);
			gc.drawString(rateData[i][2], x + 50, y);

			gc.setForeground(cb);
			String s = rateData[i][3];
			if (s.equals(raceInfo.getMinimum())) {
				gc.setForeground(ye);
				gc.fillRectangle(x + 83, y - 13, 40, vgap - 4);
			}
			gc.setForeground(bl);
			gc.drawString(s, x + 90 - fm.getAverageCharWidth(), y);
		}
	}

}