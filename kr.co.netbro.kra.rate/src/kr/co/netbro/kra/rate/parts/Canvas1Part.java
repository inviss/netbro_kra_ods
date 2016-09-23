package kr.co.netbro.kra.rate.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import kr.co.netbro.kra.model.IRaceInfoService;
import kr.co.netbro.kra.model.RaceInfo;
import kr.co.netbro.kra.model.RaceType;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Canvas1Part extends RateViewer {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	@Optional
	private IEventBroker eventBroker;

	@Optional
	@Inject
	private IRaceInfoService raceInfoService;

	//@Inject
	//private EPartService partService;
	
	@Inject @Preference(nodePath="kra.config.socket", value="zone") Integer zone;

	CustomWidget widget1;
	CustomWidget widget2;
	CustomWidget widget3;
    
	@PostConstruct
	public void createPartControl(final Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		
		FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = 5;
		fillLayout.marginWidth = 5;
		composite.setLayout(fillLayout);
		
		Composite outer = new Composite(composite, SWT.NONE );
		outer.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 5;
		formLayout.marginWidth = 5;
		formLayout.spacing = 5;
		outer.setLayout( formLayout );

		Composite innerLeft1 = new Composite(outer, SWT.NONE );
		innerLeft1.setLayout(new FillLayout());

		FormData fData = new FormData();
		fData.top = new FormAttachment(0);
		fData.left = new FormAttachment(0);
		fData.right = new FormAttachment(20); // Locks on 20% of the view
		fData.bottom = new FormAttachment(100);
		innerLeft1.setLayoutData(fData);

		Composite innerLeft2 = new Composite(outer, SWT.NONE );
		innerLeft2.setLayout(new FillLayout());

		fData = new FormData();
		fData.top = new FormAttachment(0);
		fData.left = new FormAttachment(innerLeft1);
		fData.right = new FormAttachment(40); // Locks on 40% of the view
		fData.bottom = new FormAttachment(100);
		innerLeft2.setLayoutData(fData);

		Composite innerRight = new Composite(outer, SWT.NONE );
		innerRight.setLayout(new FillLayout());

		fData = new FormData();
		fData.top = new FormAttachment(0);
		fData.left = new FormAttachment(innerLeft2);
		fData.right = new FormAttachment(100);
		fData.bottom = new FormAttachment(100);
		innerRight.setLayoutData(fData);

		if(widget1 == null)
			widget1 = new CustomWidget(innerLeft1);
		if(widget2 == null)
			widget2 = new CustomWidget(innerLeft2);
		if(widget3 == null)
			widget3 = new CustomWidget(innerRight);
		
		RaceInfo raceInfo = raceInfoService.getRaceInfo(RaceType.DAN.getType());
		if(raceInfo != null) {
			eventBroker.post("ODS_RACE/1252", raceInfo);
		}
		raceInfo = raceInfoService.getRaceInfo(RaceType.YON.getType());
		if(raceInfo != null) {
			eventBroker.post("ODS_RACE/1252", raceInfo);
		}
		raceInfo = raceInfoService.getRaceInfo(RaceType.SAMBOK_TOP60.getType());
		if(raceInfo != null) {
			eventBroker.post("ODS_RACE/1252", raceInfo);
		}
		
		//CustomWidgetObservableValue customWidgetObservableValue = new CustomWidgetObservableValue(widget);
/*
		DataBindingContext dbc = new DataBindingContext();
		CustomWidgetProperty customWidgetProperty = new CustomWidgetProperty();
		ISWTObservableValue customWidgetObservableValue = customWidgetProperty.observe(widget);

		RaceInfo raceInfo = null;
		IObservableValue todoSummaryObservable = PojoProperties.value("summary").observe(raceInfo);
		dbc.bindValue(customWidgetObservableValue, todoSummaryObservable);
*/
		Object[] oo = parent.getDisplay().getDeviceData().objects;
		int c = 0;
		int f = 0;
		for(Object o : oo) {
			if(o instanceof Color) {
				c++;
			}
			if(o instanceof Font) {
				f++;
			}
		}
		
		logger.debug("color: "+c+", font: "+f);
	}

	@Focus
	public void setFocus() {
	}

	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/1252") final RaceInfo raceInfo) {
		int type = raceInfo.getGameType();
		if(logger.isDebugEnabled()) {
			logger.debug("1part->type: "+type);
			logger.debug("config zone: "+zone);
		}
		if(RaceType.DAN.getType() == raceInfo.getGameType()) {
			widget1.setData(raceInfo);
		} else if(RaceType.YON.getType() == raceInfo.getGameType()) {
			widget2.setData(raceInfo);
		} else if(RaceType.SAMBOK_TOP60.getType() == raceInfo.getGameType()) {
			widget3.setData(raceInfo);
		}
		
	}
}
