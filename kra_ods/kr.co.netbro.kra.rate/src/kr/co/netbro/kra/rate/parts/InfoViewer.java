package kr.co.netbro.kra.rate.parts;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.model.RaceInfo;

public class InfoViewer {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	public FontRegistry fontRegistry = new FontRegistry(Display.getCurrent());

	public InfoViewer() {
		fontRegistry.put("code", new FontData[]{new FontData("Dialog", 10, SWT.NORMAL)});
	}
	
	public void paintHeader(PaintEvent e, RaceInfo raceInfo) {
		
	}
	
	public void paintBody(PaintEvent e, String[][] rateData, RaceInfo raceInfo) {
		
	}
	
	public void dispose() {
		
	}
}
