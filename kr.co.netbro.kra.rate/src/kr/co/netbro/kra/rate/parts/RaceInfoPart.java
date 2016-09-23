package kr.co.netbro.kra.rate.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaceInfoPart {
	final Logger logger = LoggerFactory.getLogger(getClass());

	private Canvas g1Canvas;

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
		//g1.setLayoutData(gd_g1comp1);
		g1comp1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Button g1tb1 = new Button(g1comp1, SWT.TOGGLE);
		g1tb1.setText("\uC11C \uC6B8");
		final Button g1tb2 = new Button(g1comp1, SWT.TOGGLE);
		g1tb2.setText("\uBD80 \uACBD");
		final Button g1tb3 = new Button(g1comp1, SWT.TOGGLE);
		g1tb3.setText("\uC81C \uC8FC");

		final Button g1tb4 = new Button(g1comp1, SWT.PUSH);
		g1tb4.setText("\uC131\uC801\uC870\uD68C");
		final Button g1tb5 = new Button(g1comp1, SWT.PUSH);
		g1tb5.setText("\uBCC0\uACBD\uC870\uD68C");
		new Label(g1comp1, SWT.NONE);

		Composite g1comp2 = new Composite(g1, SWT.NONE);

		g1Canvas = new Canvas(g1comp2, SWT.NONE);
		GridData gd_g1canvas = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_g1canvas.widthHint = 300;
		gd_g1canvas.heightHint = 70;
		g1Canvas.setLayoutData(gd_g1canvas);

		Group g2 = new Group(container, SWT.NONE);
		g2.setText("\uACBD\uAE30\uC815\uBCF4");
		g2.setLayout(new GridLayout(1, false));
		g2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Composite g2comp1 = new Composite(g2, SWT.NONE);
		g2comp1.setLayout(new GridLayout(3, false));
		g2comp1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Color color = new Color(getDisplay(), 153, 204, 255);
		
		Label s_label = new Label(g2comp1, SWT.BORDER);
		s_label.setText("\uC11C\uC6B8 \uC81C1\uACBD\uAE30 \uC885\uB8CC");
		s_label.setBackground(color);
		s_label.setAlignment(SWT.CENTER);
		s_label.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if(!color.isDisposed()) color.dispose();
			}
		});
		s_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label b_label = new Label(g2comp1, SWT.BORDER);
		b_label.setText("\uBD80\uACBD \uC81C1\uACBD\uAE30 \uC9C4\uD589");
		b_label.setBackground(color);
		b_label.setAlignment(SWT.CENTER);
		b_label.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if(!color.isDisposed()) color.dispose();
			}
		});
		b_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label j_label = new Label(g2comp1, SWT.BORDER);
		j_label.setText("\uC81C\uC8FC \uC81C1\uACBD\uAE30 \uC885\uB8CC");
		j_label.setBackground(color);
		j_label.setAlignment(SWT.CENTER);
		j_label.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if(!color.isDisposed()) color.dispose();
			}
		});
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
		curr_label_val.setText("20-16.09.22 16:51:30");
		curr_label_val.setAlignment(SWT.LEFT);
		curr_label_val.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	public static Display getDisplay() {
		Display display = Display.getCurrent();
		//may be null if outside the UI thread
		if (display == null)
			display = Display.getDefault();
		return display;		
	}
}
