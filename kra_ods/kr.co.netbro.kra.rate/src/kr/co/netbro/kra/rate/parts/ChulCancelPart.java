package kr.co.netbro.kra.rate.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.swt.widgets.Monitor;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.entity.Cancel;
import kr.co.netbro.kra.model.DecidedRate;
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
		column.getColumn().setText("\uACBD\uC8FC"); //경주
		column.getColumn().setWidth(150);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Cancel) element).getStr01()+"";
			}
		});
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("");
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Cancel) element).getStr02()+"";
			}
		});
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("");
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Cancel) element).getStr03()+"";
			}
		});
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("\uB9D0\uC774\uB984"); //말이름
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Cancel) element).getStr04()+"";
			}
		});
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("\uAC10\uB3C5"); //감독
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Cancel) element).getStr05()+"";
			}
		});
		
		Monitor monitor = Display.getCurrent().getPrimaryMonitor();
		int screenWidth = monitor.getBounds().width;
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("\uC0AC\uC720"); //사유
		column.getColumn().setWidth(screenWidth - widths);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Cancel) element).getStr06()+"";
			}
		});
	}
	
	@Focus
	public void setFocus() {
	}

	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/cancel") final List<Cancel> cancels) {
		if(logger.isDebugEnabled()) {
			logger.debug("cancels: "+cancels);
		}
	}
}
