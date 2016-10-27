package kr.co.netbro.kra.rate.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.model.RaceInfo;
import kr.co.netbro.kra.model.RaceType;
import kr.co.netbro.kra.model.RaceZone;

public class RaceStatusWidget extends Canvas {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private final boolean[][] raceTypes = new boolean[RaceZone.values().length][RaceType.SHORT_TYPE_NAME.length];

	private RaceInfo raceInfo;

	public RaceStatusWidget(Composite parent) {
		super(parent, SWT.NONE);
		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				paintRaceStatus(e.gc);
			}
		});
	}

	public RaceInfo getRaceInfo() {
		checkWidget();
		return raceInfo;
	}

	public void setRaceInfo(RaceInfo raceInfo) {
		checkWidget();
		this.raceInfo = raceInfo;
		raceReceiveStatus(raceInfo);
		redraw();
	}

	protected void paintRaceStatus(GC gc) {
		int x = 0;
		int y = 10;
		for(String tName : RaceType.SHORT_TYPE_NAME) {
			if(tName.length() == 1)
				x += 0;
			else if(tName.length() == 2)
				x += -4;
			else
				x += -6;

			gc.drawString(tName, x, y);

			if(tName.length() == 1)
				x += 30;
			else if(tName.length() == 2)
				x += 34;
			else
				x += 36;
		}
		y += 15;
		for(int i=0; i < raceTypes.length; i++) {
			x = 0;
			for(int j=0; j < raceTypes[i].length; j++) {
				if(raceTypes[i][j]) {
					gc.drawString("\u2605", x, y); // ★
				} else {
					gc.drawString("\u2606", x, y); // ☆
				}
				x += 30;
			}
			y += 15;
		}
	}

	private void raceReceiveStatus(RaceInfo raceInfo) {
		if(raceInfo.getGameType() == RaceType.FINAL.getType() || raceInfo.getGameType() > 60) {
		} else {
			if(raceInfo.getGameType() == 0) {
				for(int i=0; i < raceTypes.length; i++) {
					for(int j=0; j < raceTypes[i].length; j++) {
						raceTypes[i][j] = false;
					}
				}
			} else {
				int ordinal = RaceType.getTypeOrdinal(raceInfo.getGameType());
				int zone = raceInfo.getZone() -1;
				logger.debug("zone : "+zone+", ordinal: "+ordinal);
				raceTypes[zone][ordinal] = true;
			}
		}
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		checkWidget();
		return new Point(300, 80);
	}

}
