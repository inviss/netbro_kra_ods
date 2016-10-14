package kr.co.netbro.kra.rate.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.swt.widgets.Monitor;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.model.IRaceInfoService;
import kr.co.netbro.kra.model.RaceInfo;

public class ChulCancelPart {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	@Optional
	private IEventBroker eventBroker;

	@Optional
	@Inject
	private IRaceInfoService raceInfoService;
	
	private TableViewer tableViewer;

	@PostConstruct
	public void createPartControl(final Composite parent) {
		
		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		int widths = 0;
		TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.CENTER);
		column.getColumn().setText("경주");
		column.getColumn().setWidth(150);
		widths += column.getColumn().getWidth();
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("");
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("");
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("말이름");
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("감독");
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		
		Monitor monitor = Display.getCurrent().getPrimaryMonitor();
		int screenWidth = monitor.getBounds().width;
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("사유");
		column.getColumn().setWidth(screenWidth - widths);
	}
	
	@Focus
	public void setFocus() {
	}

	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/9") final RaceInfo raceInfo) {
		int type = raceInfo.getGameType();
		if(logger.isDebugEnabled()) {
			logger.debug("GisuChange->type: "+type);
		}
	}
}
