package kr.co.netbro.kra.rate.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import kr.co.netbro.kra.model.IRaceInfoService;
import kr.co.netbro.kra.model.RaceInfo;
import kr.co.netbro.kra.model.RaceType;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Canvas3Part extends RateViewer{
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	@Optional
	private IEventBroker eventBroker;

	@Optional
	@Inject
	private IRaceInfoService raceInfoService;

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

		Composite innerRight = new Composite(outer, SWT.NONE );
		innerRight.setLayout(new FillLayout());

		FormData fData = new FormData();
		fData.top = new FormAttachment(0);
		fData.left = new FormAttachment(0);
		fData.right = new FormAttachment(100);
		fData.bottom = new FormAttachment(100);
		innerRight.setLayoutData(fData);

		if(widget3 == null)
			widget3 = new CustomWidget(innerRight);
		
		RaceInfo raceInfo = raceInfoService.getRaceInfo(RaceType.SSANG.getType());
		if(raceInfo != null) {
			eventBroker.post("ODS_RACE/4", raceInfo);
		}
/*
		//CustomWidgetObservableValue customWidgetObservableValue = new CustomWidgetObservableValue(widget);

		DataBindingContext dbc = new DataBindingContext();
		CustomWidgetProperty customWidgetProperty = new CustomWidgetProperty();
		ISWTObservableValue customWidgetObservableValue = customWidgetProperty.observe(widget);

		RaceInfo raceInfo = null;
		IObservableValue todoSummaryObservable = PojoProperties.value("summary").observe(raceInfo);
		dbc.bindValue(customWidgetObservableValue, todoSummaryObservable);
*/
	}

	@Focus
	public void setFocus() {
	}

	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/4") final RaceInfo raceInfo) {
		int type = raceInfo.getGameType();
		if(logger.isDebugEnabled()) {
			logger.debug("3part->type: "+type);
		}
		widget3.setData(raceInfo);
	}
}
