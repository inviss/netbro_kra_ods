package kr.co.netbro.kra.rate.handlers;

import kr.co.netbro.kra.rate.dialogs.PreferenceDialog;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.swt.SWT;

@SuppressWarnings("restriction")
public class PreferenceHandler {

	@Inject @Preference(nodePath = "kra.config.socket")
	private IEclipsePreferences prefs;
	
	@Execute
	public void execute(IEclipseContext context) {
		PreferenceDialog dialog = ContextInjectionFactory.make(PreferenceDialog.class, context);
		if(SWT.OK == dialog.open()) {
			prefs.putInt("port", dialog.getPort());
			prefs.putInt("timeout", dialog.getTimeout());
			if(StringUtils.isNotBlank(dialog.getWatcherDir())) {
				prefs.put("watcherDir", dialog.getWatcherDir());
			}
			
			try {
				prefs.flush();
			} catch (Exception e) {}
		}
	}
	
}
