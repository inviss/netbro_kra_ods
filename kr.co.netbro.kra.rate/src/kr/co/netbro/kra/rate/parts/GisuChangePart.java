package kr.co.netbro.kra.rate.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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
import org.eclipse.swt.widgets.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.entity.Change;
import kr.co.netbro.kra.model.IRaceInfoService;

public class GisuChangePart {
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
		column.getColumn().setText("\uACBD\uC8FC"); // 경주
		column.getColumn().setWidth(150);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Change) element).getStr01()+"";
			}
		});
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("");
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Change) element).getStr02()+"";
			}
		});
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("");
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Change) element).getStr03()+"";
			}
		});
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("\uBCC0\uACBD\uC804"); // 변경전
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Change) element).getStr04()+"";
			}
		});
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("\uC911\uB7C9"); // 중량
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Change) element).getStr05()+"";
			}
		});
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("\uBCC0\uACBD\uD6C4"); //변경후
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Change) element).getStr06()+"";
			}
		});
		
		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("\uC911\uB7C9"); //중량
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Change) element).getStr07()+"";
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
				return ((Change) element).getStr14()+"";
			}
		});

	}
	
	@Focus
	public void setFocus() {
	}

	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/gisu") final List<Change> changes) {
		if(logger.isDebugEnabled()) {
			logger.debug("GisuChange->: "+changes);
		}
	}
}
