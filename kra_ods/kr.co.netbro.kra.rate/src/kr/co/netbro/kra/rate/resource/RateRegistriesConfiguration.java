package kr.co.netbro.kra.rate.resource;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;

public class RateRegistriesConfiguration implements IRegistriesConfiguration {

	@Override
	public void configure(Registries r) {
		// Colors add
		r.putColor("bg_1", 12, 38, 89);
		r.putColor("bg_2", 17, 3, 36);
		r.putColor("bg_3", 21, 41, 22);
		
		r.putColor("white", 235, 235, 235);
		r.putColor("yellow", 255, 240, 24);
		r.putColor("green", 173, 255, 107);
		r.putColor("blue", 120, 255, 255);
		r.putColor("orange", 255, 186, 2);
		r.putColor("pink", 255, 165, 255);
		r.putColor("green2", 27, 229, 0);
		
		r.putColor("mj_color_1", 5, 5, 50);
		r.putColor("mj_color_2", 100, 100, 200);
		r.putColor("mj_color_4", 0, 0, 45);
		r.putColor("mj_color_5", 255, 60, 0);
		r.putColor("mj_color_6", 27, 229, 0);
		
		r.putColor("mh_color", 219, 157, 28);
		r.putColor("mh_text", 0, 25, 100);
		r.putColor("mb_colir", 10, 35, 80);
		
		r.putColor("color0", 0, 0, 0);
		r.putColor("color1", 110, 9, 54);
		r.putColor("color2", 10, 80, 0);
		r.putColor("color3", 0, 1, 110);
		r.putColor("color4", 255, 100, 0);
		r.putColor("color5", 204, 204, 215);
		r.putColor("color5_2", 255, 255, 255);
		r.putColor("color6", 255, 240, 0);
		r.putColor("color7", 80, 255, 255);
		r.putColor("color8", 255, 140, 0);
		r.putColor("color9", 255, 255, 60);
		r.putColor("color10", 255, 0, 0);
		r.putColor("color11", 120, 255, 150);
		r.putColor("color12", 80, 255, 200);
		r.putColor("color13", 2, 56, 164);
		r.putColor("color14", 0, 128, 0);
		r.putColor("color15", 175, 14, 86);
		r.putColor("color16", 255, 216, 0);
		r.putColor("color17", 190, 190, 190);
		r.putColor("color18", 55, 55, 55);
		r.putColor("color19", 16, 29, 78);
		r.putColor("color20", 7, 15, 55);
		r.putColor("color21", 5, 8, 23);
		r.putColor("color_gray", 128, 128, 128);
		
		// Images add
		r.putImage("final_1", "images/final_1.jpg");
		r.putImage("final_2", "images/final_2.jpg");
		r.putImage("final_3", "images/final_3.jpg");
		r.putImage("over_1", "images/over_1.png");
		r.putImage("over_2", "images/over_2.png");
		r.putImage("over_3", "images/over_3.png");
		
		// Fonts add
		r.putFont("tv7", new FontData("tvtest", 7, SWT.NONE));
		r.putFont("tv12", new FontData("tvtest", 12, SWT.NONE));
		r.putFont("tv15", new FontData("tvtest", 15, SWT.NONE));
		r.putFont("tv17", new FontData("tvtest", 17, SWT.NONE));
		r.putFont("tv18", new FontData("tvtest", 18, SWT.NONE));
		r.putFont("tv19", new FontData("tvtest", 19, SWT.NONE));
		r.putFont("tv20", new FontData("tvtest", 20, SWT.NONE));
		r.putFont("tv21", new FontData("tvtest", 21, SWT.NONE));
		r.putFont("tv22", new FontData("tvtest", 22, SWT.NONE));
		r.putFont("tv23", new FontData("tvtest", 23, SWT.NONE));
		r.putFont("tv24", new FontData("tvtest", 24, SWT.NONE));
		r.putFont("tv25", new FontData("tvtest", 25, SWT.NONE));
		r.putFont("tv26", new FontData("tvtest", 26, SWT.NONE));
		r.putFont("tv27", new FontData("tvtest", 27, SWT.NONE));
		r.putFont("tv28", new FontData("tvtest", 28, SWT.NONE));
		r.putFont("tv29", new FontData("tvtest", 29, SWT.NONE));
		r.putFont("tv30", new FontData("tvtest", 30, SWT.NONE));
		r.putFont("tv32", new FontData("tvtest", 32, SWT.NONE));
		r.putFont("tv33", new FontData("tvtest", 33, SWT.NONE));
		r.putFont("tv34", new FontData("tvtest", 34, SWT.NONE));
		r.putFont("tv36", new FontData("tvtest", 36, SWT.NONE));
		r.putFont("tv38", new FontData("tvtest", 38, SWT.NONE));
		r.putFont("tv40", new FontData("tvtest", 40, SWT.NONE));
		r.putFont("tv42", new FontData("tvtest", 42, SWT.NONE));
		r.putFont("tv44", new FontData("tvtest", 44, SWT.NONE));
		r.putFont("tv46", new FontData("tvtest", 46, SWT.NONE));
		r.putFont("tv48", new FontData("tvtest", 48, SWT.NONE));
		r.putFont("tv50", new FontData("tvtest", 50, SWT.NONE));
		r.putFont("tv52", new FontData("tvtest", 52, SWT.NONE));
		r.putFont("ts16", new FontData("tsTv95", 16, SWT.NONE));
		r.putFont("ts18", new FontData("tsTv95", 18, SWT.NONE));
		r.putFont("ts20", new FontData("tsTv95", 20, SWT.NONE));
		r.putFont("ts22", new FontData("tsTv95", 22, SWT.NONE));
		r.putFont("ts26", new FontData("tsTv95", 26, SWT.NONE));
	}

}
