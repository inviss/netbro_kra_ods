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
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Canvas6Part extends RateViewer{

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	@Optional
	private IEventBroker eventBroker;

	@Optional
	@Inject
	private IRaceInfoService raceInfoService;

	private KraRateWidget widget3;
	private ScrolledComposite scrolled;
	private Composite composite;

	@PostConstruct
	public void createPartControl(final Composite parent) {

		scrolled = new ScrolledComposite(parent, SWT.H_SCROLL);
		scrolled.setLayout(new FillLayout());
		scrolled.setExpandHorizontal(true);
		scrolled.setExpandVertical(true);
		
		composite = new Composite(scrolled, SWT.NONE);
		composite.setLayout(new FillLayout());
		
		if(widget3 == null)
			widget3 = new KraRateWidget(composite);

		RaceInfo raceInfo = raceInfoService.getRaceInfo(RaceType.SAMSSANG.getType());
		if(raceInfo != null) {
			eventBroker.post("ODS_RACE/10", raceInfo);
		}
		
		scrolled.setContent(composite);
	}

	@Focus
	public void setFocus() {
	}

	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/10") final RaceInfo raceInfo) {
		int type = raceInfo.getGameType();
		if(logger.isDebugEnabled()) {
			logger.debug("6part->type: "+type);
		}
		widget3.setRaceInfo(raceInfo);
		
		if(widget3.getPoint() != null)
			scrolled.setMinSize(composite.computeSize(widget3.getPoint().x, widget3.getPoint().y));
	}
}
