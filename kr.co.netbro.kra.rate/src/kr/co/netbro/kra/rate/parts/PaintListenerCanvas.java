package kr.co.netbro.kra.rate.parts;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class PaintListenerCanvas extends Canvas {

	private final int BORDER_MARGIN = 4;
	private ResourceManager resourceManager;
	private String message;
	
	
	public PaintListenerCanvas(Composite parent, int style) {
		super(parent, style);
		resourceManager = new LocalResourceManager(JFaceResources.getResources(), this);
		
		addPaintListener(new PaintListener() {
			@Override
            public void paintControl(PaintEvent e) {
				paintBorderAndText(e.gc);
			}
		});
	}
	
	
	
	public String getMessage() {
		checkWidget();
		return message;
	}

	public void setMessage(String message) {
		checkWidget();
		this.message = message;
		redraw();
	}



	protected void paintBorderAndText(GC gc) {
		Font boldArialFont = resourceManager.createFont(FontDescriptor.createFrom("Arial", 14, SWT.BOLD));
		
		gc.setFont(boldArialFont);
        // setFont before using textExtent, so that the size of the text
        // can be calculated correctly
        Point textExtent = gc.textExtent(getMessage());

        // system colors may be used without the resourceManager, because those
        // system colors are maintained by the OS itself
        //gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));

        // Always begin with the background so that everything is drawn upon it
        //gc.fillRoundRectangle(3, 3, textExtent.x + BORDER_MARGIN, textExtent.y + BORDER_MARGIN, 8, 8);

        // draw the orange border
        Color orange = resourceManager.createColor(new RGB(255, 191, 0));
        //gc.setBackground(orange);
        gc.setForeground(orange);

        gc.drawRoundRectangle(3, 3, textExtent.x + BORDER_MARGIN, textExtent.y + BORDER_MARGIN, 8, 8);

        // And finally draw the given text
        gc.drawString(getMessage(), 5, 4, true);
	}

}
