package kr.co.netbro.kra.rate.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.model.ScreenType;

public class ChConfigPart {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@PostConstruct
	public void createControls(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(3, false));

		/********** composite 1 **********************/
		FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 1;
		formLayout.marginWidth = 1;
		formLayout.spacing = 1;
		
		Composite comp1 = new Composite(container, SWT.NONE );
		comp1.setLayout( formLayout );
		
		//String[] odsNames = {"삼쌍TOP", "단복승식", "연복연승식", "삼복승식", "삼복TOP", "쌍승식", "경주결과", "변경사항", "테스트"};
		String[] odsNames = {"\uC0BC\uC30DTOP", "\uB2E8\uBCF5\uC2B9\uC2DD", "\uC5F0\uBCF5\uC5F0\uC2B9\uC2DD", "\uC0BC\uBCF5\uC2B9\uC2DD", "\uC0BC\uBCF5TOP", "\uC30D\uC2B9\uC2DD", "\uACBD\uC8FC\uACB0\uACFC", "\uBCC0\uACBD\uC0AC\uD56D", "\uD14C\uC2A4\uD2B8"};
		
		int top = 10;
		int left = 0;
		int right = 10;
		int bottom = 30;
		for(int i=0; i<3; i++) {

			FormData fData0 = new FormData();
			fData0.top = new FormAttachment(top+3);
			fData0.left = new FormAttachment(left);
			fData0.right = new FormAttachment(right+2);
			fData0.bottom = new FormAttachment(bottom);
			//fData0.width = 50;

			Label raceName = new Label(comp1, SWT.NONE);
			raceName.setText(odsNames[i]);
			raceName.setLayoutData(fData0);

			FormData fData1 = new FormData();
			fData1.top = new FormAttachment(top);
			fData1.left = new FormAttachment(raceName);
			fData1.right = new FormAttachment(right+30);
			fData1.bottom = new FormAttachment(bottom);

			Combo c1 = new Combo(comp1, SWT.NONE);
			c1.setLayoutData(fData1);
			c1.setItems(ScreenType.getValues());
			c1.setText("---\uC5C6\uC74C---");
			c1.setData("id", "s1c1");

			FormData fData2 = new FormData();
			fData2.top = new FormAttachment(top);
			fData2.left = new FormAttachment(c1);
			fData2.right = new FormAttachment(right+40);
			fData2.bottom = new FormAttachment(bottom);

			Combo c2 = new Combo(comp1, SWT.NONE);
			c2.setLayoutData(fData2);
			c2.setItems(new String[]{"10", "30", "60"});
			c2.select(1);
			c2.setData("id", "s1c1");

			FormData fData3 = new FormData();
			fData3.top = new FormAttachment(top);
			fData3.left = new FormAttachment(c2);
			fData3.right = new FormAttachment(right+70);
			fData3.bottom = new FormAttachment(bottom);

			Combo c3 = new Combo(comp1, SWT.NONE);
			c3.setLayoutData(fData3);
			c3.setItems(ScreenType.getValues());
			c3.setText("---\uC5C6\uC74C---");
			c3.setData("id", "s1c1");

			FormData fData4 = new FormData();
			fData4.top = new FormAttachment(top);
			fData4.left = new FormAttachment(c3);
			fData4.right = new FormAttachment(right+80);
			fData4.bottom = new FormAttachment(bottom);

			Combo c4 = new Combo(comp1, SWT.NONE);
			c4.setLayoutData(fData4);
			c4.setItems(new String[]{"10", "30", "60"});
			c4.select(1);
			c4.setData("id", "s1c1");
			
			FormData fData5 = new FormData();
			fData5.top = new FormAttachment(top);
			fData5.left = new FormAttachment(c4);
			fData5.right = new FormAttachment(right+90);
			fData5.bottom = new FormAttachment(bottom);
			
			Button fixed = new Button(comp1, SWT.CHECK);
			fixed.setText("고정");
			fixed.setLayoutData(fData5);
			
			top += 30;
			bottom += 30;
		}
		
		/********** composite 2 **********************/
		Composite comp2 = new Composite(container, SWT.NONE );
		comp2.setLayout( formLayout );
		
		top = 10;
		left = 0;
		right = 10;
		bottom = 30;
		for(int i=0; i<3; i++) {
			FormData fData0 = new FormData();
			fData0.top = new FormAttachment(top+3);
			fData0.left = new FormAttachment(left);
			fData0.right = new FormAttachment(right+2);
			fData0.bottom = new FormAttachment(bottom);
			//fData0.width = 50;

			Label raceName = new Label(comp2, SWT.NONE);
			raceName.setText(odsNames[3+i]);
			raceName.setLayoutData(fData0);

			FormData fData1 = new FormData();
			fData1.top = new FormAttachment(top);
			fData1.left = new FormAttachment(raceName);
			fData1.right = new FormAttachment(right+30);
			fData1.bottom = new FormAttachment(bottom);

			Combo c1 = new Combo(comp2, SWT.NONE);
			//c1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			c1.setLayoutData(fData1);
			c1.setItems(ScreenType.getValues());
			c1.setText("---\uC5C6\uC74C---");
			c1.setData("id", "s1c1");

			FormData fData2 = new FormData();
			fData2.top = new FormAttachment(top);
			fData2.left = new FormAttachment(c1);
			fData2.right = new FormAttachment(right+40);
			fData2.bottom = new FormAttachment(bottom);

			Combo c2 = new Combo(comp2, SWT.NONE);
			//c2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			c2.setLayoutData(fData2);
			c2.setItems(new String[]{"10", "30", "60"});
			c2.select(1);
			c2.setData("id", "s1c1");

			FormData fData3 = new FormData();
			fData3.top = new FormAttachment(top);
			fData3.left = new FormAttachment(c2);
			fData3.right = new FormAttachment(right+70);
			fData3.bottom = new FormAttachment(bottom);

			Combo c3 = new Combo(comp2, SWT.NONE);
			//c3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			c3.setLayoutData(fData3);
			c3.setItems(ScreenType.getValues());
			c3.setText("---\uC5C6\uC74C---");
			c3.setData("id", "s1c1");

			FormData fData4 = new FormData();
			fData4.top = new FormAttachment(top);
			fData4.left = new FormAttachment(c3);
			fData4.right = new FormAttachment(right+80);
			fData4.bottom = new FormAttachment(bottom);

			Combo c4 = new Combo(comp2, SWT.NONE);
			//c4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			c4.setLayoutData(fData4);
			c4.setItems(new String[]{"10", "30", "60"});
			c4.select(1);
			c4.setData("id", "s1c1");
			
			FormData fData5 = new FormData();
			fData5.top = new FormAttachment(top);
			fData5.left = new FormAttachment(c4);
			fData5.right = new FormAttachment(right+90);
			fData5.bottom = new FormAttachment(bottom);
			
			Button fixed = new Button(comp2, SWT.CHECK);
			fixed.setText("고정");
			fixed.setLayoutData(fData5);
			
			top += 30;
			bottom += 30;
		}
		
		Composite comp3 = new Composite(container, SWT.NONE );
		comp3.setLayout( formLayout );
		
		top = 10;
		left = 0;
		right = 10;
		bottom = 30;
		for(int i=0; i<3; i++) {
			FormData fData0 = new FormData();
			fData0.top = new FormAttachment(top+3);
			fData0.left = new FormAttachment(left);
			fData0.right = new FormAttachment(right+2);
			fData0.bottom = new FormAttachment(bottom);
			//fData0.width = 50;

			Label raceName = new Label(comp3, SWT.NONE);
			raceName.setText(odsNames[6+i]);
			raceName.setLayoutData(fData0);

			FormData fData1 = new FormData();
			fData1.top = new FormAttachment(top);
			fData1.left = new FormAttachment(raceName);
			fData1.right = new FormAttachment(right+30);
			fData1.bottom = new FormAttachment(bottom);

			Combo c1 = new Combo(comp3, SWT.NONE);
			//c1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			c1.setLayoutData(fData1);
			c1.setItems(ScreenType.getValues());
			c1.setText("---\uC5C6\uC74C---");
			c1.setData("id", "s1c1");

			FormData fData2 = new FormData();
			fData2.top = new FormAttachment(top);
			fData2.left = new FormAttachment(c1);
			fData2.right = new FormAttachment(right+40);
			fData2.bottom = new FormAttachment(bottom);

			Combo c2 = new Combo(comp3, SWT.NONE);
			//c2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			c2.setLayoutData(fData2);
			c2.setItems(new String[]{"10", "30", "60"});
			c2.select(1);
			c2.setData("id", "s1c1");

			FormData fData3 = new FormData();
			fData3.top = new FormAttachment(top);
			fData3.left = new FormAttachment(c2);
			fData3.right = new FormAttachment(right+70);
			fData3.bottom = new FormAttachment(bottom);

			Combo c3 = new Combo(comp3, SWT.NONE);
			//c3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			c3.setLayoutData(fData3);
			c3.setItems(ScreenType.getValues());
			c3.setText("---\uC5C6\uC74C---");
			c3.setData("id", "s1c1");

			FormData fData4 = new FormData();
			fData4.top = new FormAttachment(top);
			fData4.left = new FormAttachment(c3);
			fData4.right = new FormAttachment(right+80);
			fData4.bottom = new FormAttachment(bottom);

			Combo c4 = new Combo(comp3, SWT.NONE);
			//c4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			c4.setLayoutData(fData4);
			c4.setItems(new String[]{"10", "30", "60"});
			c4.select(1);
			c4.setData("id", "s1c1");
			
			FormData fData5 = new FormData();
			fData5.top = new FormAttachment(top);
			fData5.left = new FormAttachment(c4);
			fData5.right = new FormAttachment(right+90);
			fData5.bottom = new FormAttachment(bottom);
			
			Button fixed = new Button(comp3, SWT.CHECK);
			fixed.setText("고정");
			fixed.setLayoutData(fData5);
			
			top += 30;
			bottom += 30;
		}
	}
}
