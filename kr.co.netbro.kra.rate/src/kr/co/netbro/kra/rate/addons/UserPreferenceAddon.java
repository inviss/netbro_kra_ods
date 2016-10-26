package kr.co.netbro.kra.rate.addons;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.extensions.Preference;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.prefs.BackingStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.database.DatabaseDataChecker;
import kr.co.netbro.kra.model.IRaceInfoService;
import kr.co.netbro.kra.rate.resource.RateRegistriesConfiguration;
import kr.co.netbro.kra.rate.resource.Registries;
import kr.co.netbro.kra.socket.SocketDataReceiver;

@SuppressWarnings({"restriction", "unused"})
public class UserPreferenceAddon {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private IRaceInfoService raceInfoService;
	@Inject
	private SocketDataReceiver socketReceiver;
	@Inject
	private DatabaseDataChecker databaseChecker;
	
	@Inject @Preference(nodePath="kra.config.socket", value="port") Integer port;
	@Inject @Preference(nodePath="kra.config.socket", value="timeout") Integer timeout;
	@Inject @Preference(nodePath="kra.config.socket", value="zone") Integer zone;
	@Inject @Preference(nodePath="kra.config.socket", value="capture") String capture;
	
	@PostConstruct
	public void setPreference(
			@Preference(nodePath = "kra.config.socket") IEclipsePreferences pref1, 
			@Preference(nodePath = "kra.config.screen") IEclipsePreferences pref2) {
		pref1.putInt("port", (port == null || port == 0) ? 8000 : port);
		pref1.putInt("timeout", (timeout == null || timeout == 0) ? 25000 : timeout);
		pref1.putInt("zone", (zone == null || zone == 0) ? 1 : zone);
		pref1.putBoolean("capture", false);
		
		flushNode(pref1);
		
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		Registries.getInstance(bundle).init(new RateRegistriesConfiguration());
	}
	
	private void flushNode(final IEclipsePreferences pref) {
		try {
			pref.flush();
		} catch (BackingStoreException e) {
			throw new RuntimeException("Error during flushing preference '" + pref.name() + "'.", e);
		}
	}
}
