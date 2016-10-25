package kr.co.netbro.kra.rate.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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
import org.eclipse.swt.widgets.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.entity.Result;
import kr.co.netbro.kra.model.IRaceInfoService;
import kr.co.netbro.kra.model.RaceInfo;

public class DeadHeatPart {
	
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
		column.getColumn().setText("마번");
		column.getColumn().setWidth(50);
		widths += column.getColumn().getWidth();
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("복식");
		column.getColumn().setWidth(80);
		widths += column.getColumn().getWidth();
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("쌍식");
		column.getColumn().setWidth(80);
		widths += column.getColumn().getWidth();
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("복연식");
		column.getColumn().setWidth(80);
		widths += column.getColumn().getWidth();
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("연식");
		column.getColumn().setWidth(80);
		widths += column.getColumn().getWidth();
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("단식");
		column.getColumn().setWidth(80);
		widths += column.getColumn().getWidth();
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("삼복");
		column.getColumn().setWidth(80);
		widths += column.getColumn().getWidth();
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("삼쌍");
		column.getColumn().setWidth(80);
		widths += column.getColumn().getWidth();
		
		Monitor monitor = Display.getCurrent().getPrimaryMonitor();
		int screenWidth = monitor.getBounds().width;
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("");
		column.getColumn().setWidth(screenWidth - widths);

	}
	
	@Focus
	public void setFocus() {
	}

	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/heat") final List<Result> results) {
		if(logger.isDebugEnabled()) {
			logger.debug("DeadHeat->results: "+results);
		}
	}
}
