package kr.co.netbro.kra.rate.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.model.DecidedRate;
import kr.co.netbro.kra.model.IRaceInfoService;
import kr.co.netbro.kra.rate.dialogs.FinalSceneDialog;

public class FixedResultPart {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	@Optional
	private IEventBroker eventBroker;

	@Optional
	@Inject
	private IRaceInfoService raceInfoService;

	private TableViewer tableViewer;
	private Shell shell;


	@PostConstruct
	public void createPartControl(final Shell shell, final Composite parent) {
		this.shell = shell;
		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		int widths = 0;
		int columnIndex = 0;
		TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.CENTER);
		column.getColumn().setText("날짜");
		column.getColumn().setWidth(150);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DecidedRate) element).getReqDate();
			}
		});
		columnIndex++;

		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("시간");
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DecidedRate) element).getReqTime();
			}
		});
		columnIndex++;

		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("확정");
		column.getColumn().setWidth(80);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DecidedRate) element).getStatus();
			}
		});
		columnIndex++;

		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("경마장");
		column.getColumn().setWidth(50);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DecidedRate) element).getZoneName();
			}
		});
		columnIndex++;

		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("경주");
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return "제"+((DecidedRate) element).getRace()+"경기";
			}
		});
		columnIndex++;

		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("1착");
		column.getColumn().setWidth(50);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DecidedRate) element).getFirstDone();
			}
		});
		columnIndex++;

		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("2착");
		column.getColumn().setWidth(50);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DecidedRate) element).getSecondDone();
			}
		});
		columnIndex++;

		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("3착");
		column.getColumn().setWidth(50);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DecidedRate) element).getThirdDone();
			}
		});
		columnIndex++;

		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("표출시간");
		column.getColumn().setWidth(80);
		widths += column.getColumn().getWidth();
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DecidedRate) element).getDelayTime()+"";
			}
		});
		columnIndex++;

		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("데이터보기");
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();

		final int btn1Index = columnIndex++;
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				TableItem item = (TableItem)cell.getItem();

				final Composite buttonPane = new Composite(tableViewer.getTable(), SWT.NONE);
				buttonPane.setLayout(new FillLayout());

				final Button button = new Button(buttonPane, SWT.NONE);
				//final DecidedRate[] decideRate = new DecidedRate[1];
				button.setText("\uB370\uC774\uD130\uBCF4\uAE30"); // 데이터보기
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Dialog dialog = new FinalSceneDialog(shell);
						dialog.open();
					}
				});

				final TableEditor editor = new TableEditor(tableViewer.getTable());
				editor.grabHorizontal  = true;
				editor.grabVertical = true;
				editor.setEditor(buttonPane, item, btn1Index);
				editor.layout();
			}
		});

		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("\uD654\uBA74\uBCF4\uAE30"); //화면보기
		column.getColumn().setWidth(100);
		widths += column.getColumn().getWidth();

		final int btn2Index = columnIndex++;
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				final TableItem item = (TableItem)cell.getItem();

				final Composite buttonPane = new Composite(tableViewer.getTable(), SWT.NONE);
				buttonPane.setLayout(new FillLayout());

				final Button button = new Button(buttonPane, SWT.NONE);
				button.setText("\uD654\uBA74\uBCF4\uAE30"); // 화면보기
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Dialog dialog = new FinalSceneDialog(shell, (DecidedRate)item.getData());
						dialog.open();
					}
				});

				final TableEditor editor = new TableEditor(tableViewer.getTable());
				editor.grabHorizontal  = true;
				editor.grabVertical = true;
				editor.setEditor(buttonPane, item, btn2Index);
				editor.layout();
			}
		});

		Monitor monitor = Display.getCurrent().getPrimaryMonitor();
		int screenWidth = monitor.getBounds().width;

		column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText("");
		column.getColumn().setWidth(screenWidth - widths);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return "";
			}
		});
	}

	private void openDialogBox(int type, final DecidedRate DecidedRate) {
		Dialog dialog = null;
		if(type == 0) { // 수신내용
			dialog = new FinalSceneDialog(shell);
		} else {		// 확정 배당률
			dialog = new FinalSceneDialog(shell, DecidedRate);
		}
		dialog.open();
	}

	@Focus
	public void setFocus() {
	}

	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/final") final DecidedRate DecidedRate) {
		String zone = DecidedRate.getZoneName();
		if(logger.isDebugEnabled()) {
			logger.debug("Final->zone: "+zone);
		}
		if(tableViewer != null) {
			tableViewer.add(DecidedRate);
		}
	}
}
