package kr.co.netbro.kra.rate.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

public class TestPart {

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
		
		PaintListenerCanvas canvas = new PaintListenerCanvas(innerRight, SWT.NULL);
		canvas.setMessage("Hello");
	}
}
