package kr.co.netbro.kra.rate.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import kr.co.netbro.kra.model.RaceInfo;
import kr.co.netbro.kra.model.RaceType;
import kr.co.netbro.kra.model.RaceZone;
import kr.co.netbro.kra.model.ScreenType;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.osgi.service.prefs.BackingStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
public class KraRateConfig {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject @Preference(nodePath="kra.config.socket", value="zone") Integer zone;
	
	@Inject @Preference(nodePath="kra.config.screen", value="sceen1") boolean sceen1;
	@Inject @Preference(nodePath="kra.config.screen", value="s1c1") Integer s1c1;
	@Inject @Preference(nodePath="kra.config.screen", value="s1c2") Integer s1c2;
	@Inject @Preference(nodePath="kra.config.screen", value="s1c3") Integer s1c3;
	@Inject @Preference(nodePath="kra.config.screen", value="s1c4") Integer s1c4;
	
	@Inject @Preference(nodePath="kra.config.screen", value="sceen2") boolean sceen2;
	@Inject @Preference(nodePath="kra.config.screen", value="s2c1") Integer s2c1;
	@Inject @Preference(nodePath="kra.config.screen", value="s2c2") Integer s2c2;
	@Inject @Preference(nodePath="kra.config.screen", value="s2c3") Integer s2c3;
	@Inject @Preference(nodePath="kra.config.screen", value="s2c4") Integer s2c4;
	
	@Inject @Preference(nodePath="kra.config.screen", value="sceen3") boolean sceen3;
	@Inject @Preference(nodePath="kra.config.screen", value="s3c1") Integer s3c1;
	@Inject @Preference(nodePath="kra.config.screen", value="s3c2") Integer s3c2;
	@Inject @Preference(nodePath="kra.config.screen", value="s3c3") Integer s3c3;
	@Inject @Preference(nodePath="kra.config.screen", value="s3c4") Integer s3c4;
	
	@Inject @Preference(nodePath="kra.config.screen", value="sceen4") boolean sceen4;
	@Inject @Preference(nodePath="kra.config.screen", value="s4c1") Integer s4c1;
	@Inject @Preference(nodePath="kra.config.screen", value="s4c2") Integer s4c2;
	@Inject @Preference(nodePath="kra.config.screen", value="s4c3") Integer s4c3;
	@Inject @Preference(nodePath="kra.config.screen", value="s4c4") Integer s4c4;
	
	@Inject @Preference(nodePath = "kra.config.socket") IEclipsePreferences pref1;
	@Inject @Preference(nodePath = "kra.config.screen") IEclipsePreferences pref2;
	
	private final boolean[][] games = new boolean[RaceZone.values().length][RaceType.values().length];

	private Canvas g1canvas;
	
	@Inject
	private IEventBroker eventBroker;

	@PostConstruct
	public void createControls(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));

		Group group1 = new Group(container, SWT.NONE);
		group1.setText("\uACBD\uAE30\uC815\uBCF4 \uC124\uC815");
		//group1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		GridLayout g1Layout = new GridLayout(4, false);
		g1Layout.marginRight = 5;
		g1Layout.marginLeft = 5;
		g1Layout.horizontalSpacing = 10;
		g1Layout.marginWidth = 5;
		group1.setLayout(g1Layout);

		Label g1Label1 = new Label(group1, SWT.NONE);
		g1Label1.setText("\uACBD\uB9C8\uC7A5 \uC120\uD0DD : "); // 경마장

		GridData gd1Data = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd1Data.widthHint = 80;
		g1Label1.setLayoutData(gd1Data);

		final Combo g1combo1 = new Combo(group1, SWT.NONE);
		g1combo1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		g1combo1.setItems(RaceZone.getValues());
		g1combo1.setText("---\uC5C6\uC74C---");
		g1combo1.select(zone-1);
		g1combo1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int newZone = g1combo1.getSelectionIndex() +1;
				pref1.remove("zone");
				pref1.putInt("zone", newZone);
				if(logger.isDebugEnabled()) {
					logger.debug("selected zone: "+newZone);
				}
				try {
					pref1.flush();
				} catch (BackingStoreException ee) {
					logger.error("zone configuration error", ee);
				}
			}
		});

		Label g1Label2 = new Label(group1, SWT.NONE);
		GridData gd_g1Label2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_g1Label2.widthHint = 80;
		g1Label2.setLayoutData(gd_g1Label2);
		g1Label2.setText("\uC218\uC2E0\uC0C1\uD669:");

		g1canvas = new Canvas(group1, SWT.NONE);
		GridData gd_g1canvas = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_g1canvas.widthHint = 300;
		gd_g1canvas.heightHint = 80;
		g1canvas.setLayoutData(gd_g1canvas);
		g1canvas.addPaintListener(eventListener(null));

		Composite composite2 = new Composite(group1, SWT.NONE);
		GridData g1CompositeGrid = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		g1CompositeGrid.horizontalSpan = 4;
		composite2.setLayoutData(g1CompositeGrid);

		GridLayout g1CompositeLayout = new GridLayout(1, false);
		composite2.setLayout(g1CompositeLayout);
/*
		final Button myButton = new Button(composite2, SWT.TOGGLE);
		myButton.setText("\uCEA1\uCC98\uC2DC\uC791");
		myButton.setSelection(false);
		myButton.addSelectionListener(new SelectionAdapter () {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(myButton.getSelection()) { // 캡춰전 -> 캡춰시작(\uCEA1\uCC98\uC2DC\uC791)
					myButton.setSelection(true);
					// Recording event start
					pref1.remove("capture");
					pref1.putBoolean("capture", true);
					eventBroker.post("ODS_RACE/CAPTURE", "Y");
					
					myButton.setText("\uCEA1\uCC98\uC911\uC9C0");
				} else { // 캡춰중 -> 캡춰중지 (\uCEA1\uCC98\uC911\uC9C0)
					myButton.setSelection(false);
					// Recording event stop
					pref1.remove("capture");
					pref1.putBoolean("capture", false);
					eventBroker.post("ODS_RACE/CAPTURE", "N");
					
					myButton.setText("\uCEA1\uCC98\uC2DC\uC791");
				}
				
				try {
					pref1.flush();
					if(logger.isInfoEnabled()) {
						logger.info("capture configured!");
					}
				} catch (BackingStoreException ee) {
					logger.error("zone configuration error", ee);
				}
			}
		});
*/
		SelectionListener sceenListener = new SelectionAdapter () {
			public void widgetSelected(SelectionEvent event) {
				Button button = ((Button) event.widget);
				Group group = (Group)button.getParent();
				Control[] controls = group.getChildren();
				String id = (String)event.widget.getData("id");
				if(button.getSelection()) {
					for(Control control : controls) {
						if(control instanceof Button) {
							button.setSelection(true);
							button.setText("\uC0AC\uC6A9\uC548\uD568"); //사용안함
							button.pack();
						} else {
							control.setEnabled(true);
							
							pref2.remove(id);
							pref2.putBoolean(id, true);
						}
					}
				} else {
					for(Control control : controls) {
						if(control instanceof Button) {
							button.setSelection(false);
							button.setText("\uC0AC\uC6A9\uD568"); //사용함
							button.pack();
						} else {
							control.setEnabled(false);
							
							pref2.remove(id);
							pref2.putBoolean(id, false);
						}
					}
				}
				
				try {
					pref2.flush();
				} catch (BackingStoreException ee) {
					logger.error("pref2 configuration error", ee);
				}
			};
		};
		
		SelectionListener typeListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int newType = ((Combo)e.widget).getSelectionIndex();
				String id = (String)e.widget.getData("id");
				if(logger.isDebugEnabled()) {
					logger.debug("rate type changed id: "+id+", value: "+newType);
				}
				pref2.remove(id);
				pref2.putInt(id, newType);

				try {
					pref2.flush();
				} catch (BackingStoreException ee) {
					logger.error("proference configuration error", ee);
				}
			}
			
		};

		Group group2 = new Group(container, SWT.NONE);
		group2.setText("\uC2B9\uC2DD \uD45C\uCD9C \uC124\uC815");
		group2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout g2Layout = new GridLayout(4, true);
		g2Layout.marginRight = 5;
		g2Layout.marginLeft = 5;
		g2Layout.horizontalSpacing = 10;
		g2Layout.marginWidth = 5;
		group2.setLayout(g2Layout);

		Group g2Sub1 = new Group(group2, SWT.NONE);
		g2Sub1.setText("\uBAA8\uB2C8\uD1301");
		g2Sub1.setToolTipText("\uBAA8\uB2C8\uD1301");
		g2Sub1.setLayout(new GridLayout(1, false));
		g2Sub1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Combo s1combo1 = new Combo(g2Sub1, SWT.NONE);
		s1combo1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s1combo1.setItems(ScreenType.getValues());
		s1combo1.setText("---\uC5C6\uC74C---");
		s1combo1.setData("id", "s1c1");
		s1combo1.select(s1c1);
		s1combo1.addSelectionListener(typeListener);
		

		Combo s1combo2 = new Combo(g2Sub1, SWT.NONE);
		s1combo2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s1combo2.setItems(ScreenType.getValues());
		s1combo2.setText("---\uC5C6\uC74C---");
		s1combo2.setData("id", "s1c2");
		s1combo2.select(s1c2);
		s1combo2.addSelectionListener(typeListener);

		Combo s1combo3 = new Combo(g2Sub1, SWT.NONE);
		s1combo3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s1combo3.setItems(ScreenType.getValues());
		s1combo3.setText("---\uC5C6\uC74C---");
		s1combo3.setData("id", "s1c3");
		s1combo3.select(s1c3);
		s1combo3.addSelectionListener(typeListener);

		Combo s1combo4 = new Combo(g2Sub1, SWT.NONE);
		s1combo4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s1combo4.setItems(ScreenType.getValues());
		s1combo4.setText("---\uC5C6\uC74C---");
		s1combo4.setData("id", "s1c4");
		s1combo4.select(s1c4);
		s1combo4.addSelectionListener(typeListener);
		
		if(!sceen1) {
			for(Control control : g2Sub1.getChildren()) {
				if(control instanceof Combo) {
					control.setEnabled(false);
				}
			}
		}
		
		Button g2Sub1Button1 = new Button(g2Sub1, SWT.TOGGLE);
		g2Sub1Button1.setText(sceen1 ? "\uC0AC\uC6A9\uC548\uD568" : "\uC0AC\uC6A9\uD568");
		g2Sub1Button1.setSelection(sceen1);
		g2Sub1Button1.setData("id", "sceen1");
		g2Sub1Button1.addSelectionListener(sceenListener);

		Group g2Sub2 = new Group(group2, SWT.NONE);
		g2Sub2.setText("\uBAA8\uB2C8\uD1302");
		g2Sub2.setToolTipText("\uBAA8\uB2C8\uD1302");
		g2Sub2.setLayout(new GridLayout(1, false));
		g2Sub2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Combo s2combo1 = new Combo(g2Sub2, SWT.NONE);
		s2combo1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s2combo1.setItems(ScreenType.getValues());
		s2combo1.setText("---\uC5C6\uC74C---");
		s2combo1.setData("id", "s2c1");
		s2combo1.select(s2c1);
		s2combo1.addSelectionListener(typeListener);

		Combo s2combo2 = new Combo(g2Sub2, SWT.NONE);
		s2combo2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s2combo2.setItems(ScreenType.getValues());
		s2combo2.setText("---\uC5C6\uC74C---");
		s2combo2.setData("id", "s2c2");
		s2combo2.select(s2c2);
		s2combo2.addSelectionListener(typeListener);

		Combo s2combo3 = new Combo(g2Sub2, SWT.NONE);
		s2combo3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s2combo3.setItems(ScreenType.getValues());
		s2combo3.setText("---\uC5C6\uC74C---");
		s2combo3.setData("id", "s2c3");
		s2combo3.select(s2c3);
		s2combo3.addSelectionListener(typeListener);

		Combo s2combo4 = new Combo(g2Sub2, SWT.NONE);
		s2combo4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s2combo4.setItems(ScreenType.getValues());
		s2combo4.setText("---\uC5C6\uC74C---");
		s2combo4.setData("id", "s2c4");
		s2combo4.select(s2c4);
		s2combo4.addSelectionListener(typeListener);

		if(!sceen2) {
			for(Control control : g2Sub2.getChildren()) {
				if(control instanceof Combo) {
					control.setEnabled(false);
				}
			}
		}
		
		Button g2Sub2Button1 = new Button(g2Sub2, SWT.TOGGLE);
		g2Sub2Button1.setText(sceen2 ? "\uC0AC\uC6A9\uC548\uD568" : "\uC0AC\uC6A9\uD568");
		g2Sub2Button1.setSelection(sceen2);
		g2Sub2Button1.setData("id", "sceen2");
		g2Sub2Button1.addSelectionListener(sceenListener);
		
		Group g2Sub3 = new Group(group2, SWT.NONE);
		g2Sub3.setText("\uBAA8\uB2C8\uD1303");
		g2Sub3.setToolTipText("\uBAA8\uB2C8\uD1303");
		g2Sub3.setLayout(new GridLayout(1, false));
		g2Sub3.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Combo s3combo1 = new Combo(g2Sub3, SWT.NONE);
		s3combo1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s3combo1.setItems(ScreenType.getValues());
		s3combo1.setText("---\uC5C6\uC74C---");
		s3combo1.setData("id", "s3c1");
		s3combo1.select(s3c1);
		s3combo1.addSelectionListener(typeListener);

		Combo s3combo2 = new Combo(g2Sub3, SWT.NONE);
		s3combo2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s3combo2.setItems(ScreenType.getValues());
		s3combo2.setText("---\uC5C6\uC74C---");
		s3combo2.setData("id", "s3c2");
		s3combo2.select(s3c2);
		s3combo2.addSelectionListener(typeListener);

		Combo s3combo3 = new Combo(g2Sub3, SWT.NONE);
		s3combo3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s3combo3.setItems(ScreenType.getValues());
		s3combo3.setText("---\uC5C6\uC74C---");
		s3combo3.setData("id", "s3c3");
		s3combo3.select(s3c3);
		s3combo3.addSelectionListener(typeListener);

		Combo s3combo4 = new Combo(g2Sub3, SWT.NONE);
		s3combo4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s3combo4.setItems(ScreenType.getValues());
		s3combo4.setText("---\uC5C6\uC74C---");
		s3combo4.setData("id", "s3c4");
		s3combo4.select(s3c4);
		s3combo4.addSelectionListener(typeListener);

		if(!sceen3) {
			for(Control control : g2Sub3.getChildren()) {
				if(control instanceof Combo) {
					control.setEnabled(false);
				}
			}
		}
		
		Button g2Sub3Button1 = new Button(g2Sub3, SWT.TOGGLE);
		g2Sub3Button1.setText(sceen3 ? "\uC0AC\uC6A9\uC548\uD568" : "\uC0AC\uC6A9\uD568");
		g2Sub3Button1.setSelection(sceen3);
		g2Sub3Button1.setData("id", "sceen3");
		g2Sub3Button1.addSelectionListener(sceenListener);

		Group g2Sub4 = new Group(group2, SWT.NONE);
		g2Sub4.setText("\uBAA8\uB2C8\uD1304");
		g2Sub4.setToolTipText("\uBAA8\uB2C8\uD1304");
		g2Sub4.setLayout(new GridLayout(1, false));
		g2Sub4.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Combo s4combo1 = new Combo(g2Sub4, SWT.NONE);
		s4combo1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s4combo1.setItems(ScreenType.getValues());
		s4combo1.setText("---\uC5C6\uC74C---");
		s4combo1.setData("id", "s4c1");
		s4combo1.select(s4c1);
		s4combo1.addSelectionListener(typeListener);

		Combo s4combo2 = new Combo(g2Sub4, SWT.NONE);
		s4combo2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s4combo2.setItems(ScreenType.getValues());
		s4combo2.setText("---\uC5C6\uC74C---");
		s4combo2.setData("id", "s4c2");
		s4combo2.select(s4c2);
		s4combo2.addSelectionListener(typeListener);

		Combo s4combo3 = new Combo(g2Sub4, SWT.NONE);
		s4combo3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s4combo3.setItems(ScreenType.getValues());
		s4combo3.setText("---\uC5C6\uC74C---");
		s4combo3.setData("id", "s4c3");
		s4combo3.select(s4c3);
		s4combo3.addSelectionListener(typeListener);

		Combo s4combo4 = new Combo(g2Sub4, SWT.NONE);
		s4combo4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		s4combo4.setItems(ScreenType.getValues());
		s4combo4.setText("---\uC5C6\uC74C---");
		s4combo4.setData("id", "s4c4");
		s4combo4.select(s4c4);
		s4combo4.addSelectionListener(typeListener);

		if(!sceen4) {
			for(Control control : g2Sub4.getChildren()) {
				if(control instanceof Combo) {
					control.setEnabled(false);
				}
			}
		}
		
		Button g2Sub4Button1 = new Button(g2Sub4, SWT.TOGGLE);
		g2Sub4Button1.setText(sceen4 ? "\uC0AC\uC6A9\uC548\uD568" : "\uC0AC\uC6A9\uD568");
		g2Sub4Button1.setSelection(sceen4);
		g2Sub4Button1.setData("id", "sceen4");
		g2Sub4Button1.addSelectionListener(sceenListener);
	}

	@Inject @Optional
	public void  getEvent(@UIEventTopic("ODS_RACE/STATUS") final RaceInfo raceInfo) {
		if(logger.isDebugEnabled()) {
			logger.debug("Rate Config ->type: "+raceInfo.getGameType());
		}
		gameReceiveStatus(raceInfo);
		g1canvas.removePaintListener(eventListener(null));
		g1canvas.addPaintListener(eventListener(raceInfo));
		g1canvas.redraw();
	}
	
	private PaintListener eventListener(final RaceInfo raceInfo) {
		PaintListener paintListener = new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				
				int x = 0;
				int y = 10;
				for(String tName : RaceType.SHORT_TYPE_NAME) {
					if(tName.length() == 1)
						x += 0;
					else if(tName.length() == 2)
						x += -4;
					else
						x += -6;
					
					gc.drawString(tName, x, y);
					
					if(tName.length() == 1)
						x += 30;
					else if(tName.length() == 2)
						x += 34;
					else
						x += 36;
				}
				y += 15;
				for(int i=0; i < games.length; i++) {
					x = 0;
					for(int j=0; j < games[i].length; j++) {
						if(games[i][j]) {
							gc.drawString("★", x, y);
						} else {
							gc.drawString("☆", x, y);
						}
						x += 30;
					}
					y += 15;
				}
			}

		};
		return paintListener;
	}
	
	private void gameReceiveStatus(RaceInfo raceInfo) {
		if(raceInfo.getGameType() == 0) {
			for(int i=0; i < games.length; i++) {
				for(int j=0; j < games[i].length; j++) {
					games[i][j] = false;
				}
			}
		} else {
			int ordinal = RaceType.getTypeOrdinal(raceInfo.getGameType());
			int zone = raceInfo.getZone() -1;
			logger.debug("zone : "+zone+", ordinal: "+ordinal);
			games[zone][ordinal] = true;
		}
		
	}
}
