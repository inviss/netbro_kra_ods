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

public class KraRateWidget extends Canvas {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private RaceInfo raceInfo;
	private Point point;

	public KraRateWidget(Composite parent) {
		super(parent, SWT.NONE);
		setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		
		addPaintListener(new PaintListener() {
			@Override
            public void paintControl(PaintEvent e) {
				if(raceInfo != null)
					paintHeaderAndBody(e.gc);
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
		redraw();
	}

	protected void paintHeaderAndBody(GC gc) {
		if(logger.isDebugEnabled()) {
			logger.debug("raceType=" + raceInfo.getGameType());
		}
		
		RateViewer viewer = null;
		if(RaceType.SAMBOK_TOP60.getType() == raceInfo.getGameType()
				|| RaceType.SAMSSANG_TOP60.getType() == raceInfo.getGameType()) {
			viewer = new TopRateViewer();
		} else if(RaceType.SAMBOK.getType() == raceInfo.getGameType()) {
			viewer = new SambokRateViewer();
		} else if(RaceType.SAMSSANG.getType() == raceInfo.getGameType()) {
			viewer = new SsamsangRateViewer();
		} else {
			viewer = new RateViewer();
		}
		
		viewer.paintHeader(gc, getRaceInfo());
		viewer.paintBody(gc, viewer.makeData(getRaceInfo()), getRaceInfo());
		
		point = computeSize(viewer.getWidth(), viewer.getHeight(), true);
	}
	
	public Point getPoint() {
		return this.point;
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		checkWidget();
		Point initialSize = super.computeSize (wHint, wHint, changed);
		return initialSize;
	}

}
