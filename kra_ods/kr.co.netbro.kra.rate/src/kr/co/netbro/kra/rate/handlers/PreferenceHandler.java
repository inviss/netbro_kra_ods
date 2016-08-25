package kr.co.netbro.kra.rate.handlers;

import kr.co.netbro.kra.rate.dialogs.PreferenceDialog;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;

public class PreferenceHandler {

	@Execute
	public void execute(IEclipseContext context) {
		PreferenceDialog dialog = ContextInjectionFactory.make(PreferenceDialog.class, context);
		dialog.open();
	}
	
}
