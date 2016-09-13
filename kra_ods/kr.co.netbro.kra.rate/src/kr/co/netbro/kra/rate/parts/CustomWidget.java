package kr.co.netbro.kra.rate.parts;

import kr.co.netbro.kra.model.RaceInfo;
import kr.co.netbro.kra.model.RaceType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomWidget extends Canvas {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private String text;
	private Point textExtent;
	private PaintListener paintListener;
	private DisposeListener disposeListener;
	private RateViewer viewer;

	public CustomWidget(Composite parent) {
		super(parent, SWT.NONE);
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		checkWidget();
		return textExtent != null ? textExtent : new Point(1, 1);
	}

	public String getText() {
		checkWidget();
		return text;
	}

	public void setText(String text) {
		checkWidget();
		this.text = text;
		redraw();
	}

	public void setData(final RaceInfo raceInfo) {
		checkWidget();
		if(paintListener != null) {
			removePaintListener(paintListener);
			removeDisposeListener(disposeListener);
		}
		
		paintListener = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				
				if(logger.isDebugEnabled()) {
					logger.debug("raceType=" + raceInfo.getGameType());
				}
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
				String[][] rateData = viewer.makeData(raceInfo);
				
				GC gc = e.gc;
				viewer.paintHeader(gc, raceInfo);
				viewer.paintBody(gc, rateData, raceInfo);
			}
			
		};
		
		disposeListener = new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if(viewer != null) viewer.dispose();
			}
		};
		
		addPaintListener(paintListener);
		addDisposeListener(disposeListener);
		
		redraw();
	}
	
}
