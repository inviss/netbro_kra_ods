package kr.co.netbro.kra.rate.parts;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.map.WritableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.osgi.service.prefs.BackingStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.common.utils.DateUtils;
import kr.co.netbro.common.utils.Utility;
import kr.co.netbro.kra.model.RaceInfo;
import kr.co.netbro.kra.rate.resource.Registries;

@SuppressWarnings("restriction")
public class RaceInfoPart {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private DataBindingContext ctx = new DataBindingContext();
	

	@Inject @Preference(nodePath = "kra.config.socket") IEclipsePreferences pref1;
	@Inject @Preference(nodePath = "kra.config.race") IEclipsePreferences pref3;
	
	@Inject @Preference(nodePath="kra.config.socket", value="zone") Integer zone;
	
	private RaceStatusWidget statusWidget;
	private DateTime resultCal;
	private DateTime changeCal;
	
	private Button g1tb1;
	private Button g1tb2;
	private Button g1tb3;
	
	private IObservableMap attributesMap = new WritableMap();
	private DataBindingContext dbc;

	@PostConstruct
	public void createControls(Composite parent) {
		dbc = new DataBindingContext();
		
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
		g1tb1 = new Button(g1comp1, SWT.TOGGLE);
		g1tb1.setText("\uC11C \uC6B8"); // 서울
		minSize = g1tb1.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		GridDataFactory.fillDefaults().hint(Math.max(100, minSize.x), SWT.DEFAULT).applyTo(g1tb1);
		g1tb1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pref1.remove("zone");
				pref1.putInt("zone", 1);
				if(logger.isDebugEnabled()) {
					logger.debug("selected zone: 1");
				}
				try {
					pref1.flush();
				} catch (BackingStoreException ee) {}
				
				g1tb2.setSelection(false);
				g1tb3.setSelection(false);
			}
		});
		if(zone == 1) {
			g1tb1.setSelection(true);
		}
		
		g1tb2 = new Button(g1comp1, SWT.TOGGLE);
		g1tb2.setText("\uC81C \uC8FC"); // 제주
		minSize = g1tb2.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		GridDataFactory.fillDefaults().hint(Math.max(100, minSize.x), SWT.DEFAULT).applyTo(g1tb2);
		g1tb2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pref1.remove("zone");
				pref1.putInt("zone", 2);
				if(logger.isDebugEnabled()) {
					logger.debug("selected zone: 2");
				}
				try {
					pref1.flush();
				} catch (BackingStoreException ee) {}
				
				g1tb1.setSelection(false);
				g1tb3.setSelection(false);
			}
		});
		if(zone == 2) {
			g1tb2.setSelection(true);
		}
		
		g1tb3 = new Button(g1comp1, SWT.TOGGLE);
		g1tb3.setText("\uBD80 \uACBD"); // 부경
		minSize = g1tb3.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		GridDataFactory.fillDefaults().hint(Math.max(100, minSize.x), SWT.DEFAULT).applyTo(g1tb3);
		g1tb3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pref1.remove("zone");
				pref1.putInt("zone", 3);
				if(logger.isDebugEnabled()) {
					logger.debug("selected zone: 3");
				}
				try {
					pref1.flush();
				} catch (BackingStoreException ee) {}
				
				g1tb1.setSelection(false);
				g1tb2.setSelection(false);
			}
		});
		if(zone == 3) {
			g1tb3.setSelection(true);
		}
		
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

		Calendar cal = Calendar.getInstance();
		switch(zone) {
		case 1 : // 서울
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			break;
		case 2 : // 제주
			cal.add(Calendar.DATE, -7);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			break;
		case 3 : // 부경
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			break;
		}
		
		resultCal = new DateTime(g1comp1_1, SWT.DATE | SWT.DROP_DOWN);
		resultCal.setBounds(55, 105, 88, 24);
		resultCal.addSelectionListener (new SelectionAdapter () {
	        public void widgetSelected (SelectionEvent e) {
	        	pref3.remove("grade");
				pref3.put("grade", resultCal.getYear()+(Utility.padLeft(String.valueOf(resultCal.getMonth()+1), "0", 2))+resultCal.getDay());
				if(logger.isDebugEnabled()) {
					logger.debug("성적조회 일자변경: "+resultCal.getYear()+"."+(resultCal.getMonth()+1)+"."+resultCal.getDay());
				}
				try {
					pref3.flush();
				} catch (BackingStoreException ee) {}
	        }
	    });
		String gradeDate = DateUtils.getFmtDateString(cal.getTime(), "yyyyMMdd");
		resultCal.setDate(
				Integer.parseInt(gradeDate.substring(0, 4)), 
				Integer.parseInt(gradeDate.substring(4, 6)) -1, 
				Integer.parseInt(gradeDate.substring(6))
		);

		Label label_2 = new Label(g1comp1_1, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("\\맑은 고딕", 9, SWT.NORMAL));
		label_2.setText("\uBCC0\uACBD\uC870\uD68C:"); //변경조회
		label_2.setBounds(10, 25, 100, 15);

		changeCal = new DateTime(g1comp1_1, SWT.DATE | SWT.DROP_DOWN);
		changeCal.setBounds(55, 105, 88, 24);
		changeCal.setBounds(55, 105, 88, 24);
		changeCal.addSelectionListener (new SelectionAdapter () {
	        public void widgetSelected (SelectionEvent e) {
	        	pref3.remove("change");
				pref3.put("change", changeCal.getYear()+(Utility.padLeft(String.valueOf(changeCal.getMonth()+1), "0", 2))+changeCal.getDay());
				if(logger.isDebugEnabled()) {
					logger.debug("변경조회 일자변경: "+changeCal.getYear()+"."+(changeCal.getMonth()+1)+"."+changeCal.getDay());
				}
				try {
					pref3.flush();
				} catch (BackingStoreException ee) {}
	        }
	    });

		Composite g1comp2 = new Composite(g1, SWT.NONE);
		g1comp2.setLayout(new GridLayout(1, false));

		GridData gd_g1canvas = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_g1canvas.widthHint = 300;
		gd_g1canvas.heightHint = 80;
		g1comp2.setLayoutData(gd_g1canvas);

		statusWidget = new RaceStatusWidget(g1comp2);

		Group g2 = new Group(container, SWT.NONE);
		g2.setText("\uACBD\uAE30\uC815\uBCF4");   // 경기정보
		g2.setLayout(new GridLayout(1, false));
		
		GridData g2_grid = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		g2_grid.widthHint = 500;
		g2_grid.heightHint = 90;
		g2.setLayoutData(g2_grid);

		Composite g2comp1 = new Composite(g2, SWT.NONE);
		g2comp1.setLayout(new GridLayout(3, false));
		g2comp1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label s_label = new Label(g2comp1, SWT.BORDER);
		s_label.setText("\uC11C\uC6B8 \uC81C1\uACBD\uAE30 \uC885\uB8CC");
		s_label.setBackground(Registries.getInstance().getColor("config1"));
		s_label.setAlignment(SWT.CENTER);
		s_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ISWTObservableValue  oSValue = WidgetProperties.text().observe(s_label);
		IObservableValue nSValue = Observables.observeMapEntry(attributesMap, "1_status");
		dbc.bindValue(oSValue, nSValue);

		Label b_label = new Label(g2comp1, SWT.BORDER);
		b_label.setText("\uBD80\uACBD \uC81C1\uACBD\uAE30 \uC9C4\uD589");
		b_label.setBackground(Registries.getInstance().getColor("config1"));
		b_label.setAlignment(SWT.CENTER);
		b_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ISWTObservableValue  oBValue = WidgetProperties.text().observe(b_label);
		IObservableValue nBValue = Observables.observeMapEntry(attributesMap, "3_status");
		dbc.bindValue(oBValue, nBValue);

		Label j_label = new Label(g2comp1, SWT.BORDER);
		j_label.setText("\uC81C\uC8FC \uC81C1\uACBD\uAE30 \uC885\uB8CC");
		j_label.setBackground(Registries.getInstance().getColor("config1"));
		j_label.setAlignment(SWT.CENTER);
		j_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ISWTObservableValue  oJLabel = WidgetProperties.text().observe(j_label);
		IObservableValue nJValue = Observables.observeMapEntry(attributesMap, "2_status");
		dbc.bindValue(oJLabel, nJValue);

		Composite g2comp2 = new Composite(g2comp1, SWT.NONE);
		g2comp2.setLayout(new GridLayout(2, false));
		g2comp2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		Label ip_label_val = new Label(g2comp2, SWT.NONE);
		ip_label_val.setText("192.168.0.1");
		ip_label_val.setAlignment(SWT.LEFT);
		ip_label_val.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ISWTObservableValue  oIPLabel = WidgetProperties.text().observe(ip_label_val);
		IObservableValue nIPValue = Observables.observeMapEntry(attributesMap, "ip_status");
		dbc.bindValue(oIPLabel, nIPValue);

		Label curr_label_val = new Label(g2comp2, SWT.NONE);
		curr_label_val.setText("2016.09.22 16:51:30");
		curr_label_val.setAlignment(SWT.LEFT);
		curr_label_val.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ISWTObservableValue  oTimeLabel = WidgetProperties.text().observe(curr_label_val);
		IObservableValue nTimeValue = Observables.observeMapEntry(attributesMap, "latest_status");
		dbc.bindValue(oTimeLabel, nTimeValue);
	}

	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/STATUS") final RaceInfo raceInfo) {
		if(logger.isDebugEnabled()) {
			logger.debug("Rate Config ->type: "+raceInfo.getGameType());
		}
		if(raceInfo != null) {
			statusWidget.setRaceInfo(raceInfo);
			
			String timeStr = "";
			if(raceInfo.getTime().equals("xx")) {
				timeStr = "\uC885\uB8CC";
			} else if(raceInfo.getTime().equals("yy")) {
				timeStr = "\uB300\uAE30\uC911";
			} else {
				timeStr = "\uACBD\uAE30\uC911";
			}
			/*
			if(raceInfo.getTime().equals("xx")) {
				timeStr = "마감";
			} else {
				if(raceInfo.getTime().equals("yy")) {
					timeStr = "분전";
				} else {
					timeStr = "마감 "+raceInfo.getTime()+"분전";
				}
			}
			*/
			String rInfo = raceInfo.getZoneName()+" \uC81C"+raceInfo.getRaceNum()+"\uACBD\uAE30 "+timeStr;
			attributesMap.put(raceInfo.getZone()+"_status", rInfo);
			
			if(StringUtils.isNotBlank(raceInfo.getClientIP())) {
				attributesMap.put("ip_status", "\uC5F0\uACB0IP: "+raceInfo.getClientIP());
				attributesMap.put("latest_status", "\uCD5C\uADFC \uC5C5\uB370\uC774\uD2B8: "+raceInfo.getUpdateTime());
			}
		}
	}

	@Inject @Optional
	public void  getResultsEvent(@UIEventTopic("ODS_DATE/GRADE") final String gradeDate) {
		if(logger.isDebugEnabled()) {
			logger.debug("gradeDate: "+gradeDate);
		}

		Calendar cal = Calendar.getInstance();
		
		resultCal.setYear(cal.get(Calendar.YEAR));
		resultCal.setMonth((cal.get(Calendar.MONTH)));
		resultCal.setDay(cal.get(Calendar.DAY_OF_MONTH));
	}

}
