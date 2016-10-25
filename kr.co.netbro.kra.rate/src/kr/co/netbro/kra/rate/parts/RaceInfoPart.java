package kr.co.netbro.kra.rate.parts;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.model.RaceInfo;
import kr.co.netbro.kra.rate.resource.Registries;

public class RaceInfoPart {
	final Logger logger = LoggerFactory.getLogger(getClass());

	private RaceStatusWidget statusWidget;
	private DateTime resultCal;
	private DateTime changeCal;

	@PostConstruct
	public void createControls(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));

		Group g1 = new Group(container, SWT.NONE);
		g1.setText("\uACBD\uB9C8\uC7A5");
		g1.setLayout(new GridLayout(2, false));
		g1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite g1comp1 = new Composite(g1, SWT.NONE);
		g1comp1.setLayout(new GridLayout(3, false));

		GridData gd_g1comp1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_g1comp1.heightHint = 100;
		g1comp1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Point minSize = null;
		final Button g1tb1 = new Button(g1comp1, SWT.TOGGLE);
		g1tb1.setText("\uC11C \uC6B8");
		minSize = g1tb1.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		GridDataFactory.fillDefaults().hint(Math.max(100, minSize.x), SWT.DEFAULT).applyTo(g1tb1);

		
		final Button g1tb2 = new Button(g1comp1, SWT.TOGGLE);
		g1tb2.setText("\uBD80 \uACBD");
		minSize = g1tb2.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		GridDataFactory.fillDefaults().hint(Math.max(100, minSize.x), SWT.DEFAULT).applyTo(g1tb2);
		
		final Button g1tb3 = new Button(g1comp1, SWT.TOGGLE);
		g1tb3.setText("\uC81C \uC8FC");
		minSize = g1tb3.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		GridDataFactory.fillDefaults().hint(Math.max(100, minSize.x), SWT.DEFAULT).applyTo(g1tb3);

		Composite g1comp1_1 = new Composite(g1comp1, SWT.NONE);
		g1comp1_1.setLayout(new GridLayout(4, false));

		GridData gridData = new GridData(GridData.VERTICAL_ALIGN_END);
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		g1comp1_1.setLayoutData(gridData);

		Label label_1 = new Label(g1comp1_1, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("\\맑은 고딕", 9, SWT.NORMAL));
		label_1.setText("\uC131\uC801\uC870\uD68C:"); //성적조회
		label_1.setBounds(10, 25, 100, 15);

		resultCal = new DateTime(g1comp1_1, SWT.DATE | SWT.DROP_DOWN);
		resultCal.setBounds(55, 105, 88, 24);

		Label label_2 = new Label(g1comp1_1, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("\\맑은 고딕", 9, SWT.NORMAL));
		label_2.setText("\uBCC0\uACBD\uC870\uD68C:"); //변경조회
		label_2.setBounds(10, 25, 100, 15);

		changeCal = new DateTime(g1comp1_1, SWT.DATE | SWT.DROP_DOWN);
		changeCal.setBounds(55, 105, 88, 24);

		Composite g1comp2 = new Composite(g1, SWT.NONE);
		g1comp2.setLayout(new GridLayout(1, false));

		GridData gd_g1canvas = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_g1canvas.widthHint = 300;
		gd_g1canvas.heightHint = 80;
		g1comp2.setLayoutData(gd_g1canvas);

		statusWidget = new RaceStatusWidget(g1comp2);

		Group g2 = new Group(container, SWT.NONE);
		g2.setText("\uACBD\uAE30\uC815\uBCF4");
		g2.setLayout(new GridLayout(1, false));
		g2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite g2comp1 = new Composite(g2, SWT.NONE);
		g2comp1.setLayout(new GridLayout(3, false));
		g2comp1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label s_label = new Label(g2comp1, SWT.BORDER);
		s_label.setText("\uC11C\uC6B8 \uC81C1\uACBD\uAE30 \uC885\uB8CC");
		s_label.setBackground(Registries.getInstance().getColor("config1"));
		s_label.setAlignment(SWT.CENTER);
		s_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label b_label = new Label(g2comp1, SWT.BORDER);
		b_label.setText("\uBD80\uACBD \uC81C1\uACBD\uAE30 \uC9C4\uD589");
		b_label.setBackground(Registries.getInstance().getColor("config1"));
		b_label.setAlignment(SWT.CENTER);
		b_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label j_label = new Label(g2comp1, SWT.BORDER);
		j_label.setText("\uC81C\uC8FC \uC81C1\uACBD\uAE30 \uC885\uB8CC");
		j_label.setBackground(Registries.getInstance().getColor("config1"));
		j_label.setAlignment(SWT.CENTER);
		j_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Composite g2comp2 = new Composite(g2comp1, SWT.NONE);
		g2comp2.setLayout(new GridLayout(4, false));
		g2comp2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		Label ip_label = new Label(g2comp2, SWT.NONE);
		ip_label.setText("\uC5F0\uACB0IP :");
		ip_label.setAlignment(SWT.RIGHT);
		ip_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label ip_label_val = new Label(g2comp2, SWT.NONE);
		ip_label_val.setText("192.168.0.1");
		ip_label_val.setAlignment(SWT.LEFT);
		ip_label_val.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label curr_label = new Label(g2comp2, SWT.NONE);
		curr_label.setText("\uCD5C\uADFC \uC5C5\uB370\uC774\uD2B8 :");
		curr_label.setAlignment(SWT.RIGHT);
		curr_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label curr_label_val = new Label(g2comp2, SWT.NONE);
		curr_label_val.setText("2016.09.22 16:51:30");
		curr_label_val.setAlignment(SWT.LEFT);
		curr_label_val.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/STATUS") final RaceInfo raceInfo) {
		if(logger.isDebugEnabled()) {
			logger.debug("Rate Config ->type: "+raceInfo.getGameType());
		}
		if(raceInfo != null) {
			statusWidget.setRaceInfo(raceInfo);
		}
	}

	@Inject @Optional
	public void  getResultsEvent(@UIEventTopic("ODS_DATE/RESULTS") final RaceInfo raceInfo) {
		if(logger.isDebugEnabled()) {
			logger.debug("Rate Config ->type: "+raceInfo.getGameType());
		}

		Calendar cal = Calendar.getInstance();

		resultCal.setYear(cal.get(Calendar.YEAR));
		resultCal.setMonth((cal.get(Calendar.MONTH)));
		resultCal.setDay(cal.get(Calendar.DAY_OF_MONTH));
	}

	@Inject @Optional
	public void  getChangeEvent(@UIEventTopic("ODS_DATE/CHANGE") final RaceInfo raceInfo) {
		if(logger.isDebugEnabled()) {
			logger.debug("Rate Config ->type: "+raceInfo.getGameType());
		}
		Calendar cal = Calendar.getInstance();

		resultCal.setYear(cal.get(Calendar.YEAR));
		resultCal.setMonth((cal.get(Calendar.MONTH)));
		resultCal.setDay(cal.get(Calendar.DAY_OF_MONTH));
	}

}
