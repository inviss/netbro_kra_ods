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
	
	@PostConstruct
	public void setPreference(
			@Preference(nodePath = "kra.config.socket") IEclipsePreferences pref1, 
			@Preference(nodePath = "kra.config.screen") IEclipsePreferences pref2) {
		pref1.putInt("port", (port == null || port == 0) ? 8000 : port);
		pref1.putInt("timeout", (timeout == null || timeout == 0) ? 25000 : timeout);
		pref1.putInt("zone", (zone == null || zone == 0) ? 1 : zone);
		pref1.putBoolean("capture", false);
		
		flushNode(pref1);
		
		pref2.putInt("s1c1", (s1c1 == null || s1c1 == 0) ? 0 : s1c1);
		pref2.putInt("s1c2", (s1c2 == null || s1c2 == 0) ? 0 : s1c2);
		pref2.putInt("s1c3", (s1c3 == null || s1c3 == 0) ? 0 : s1c3);
		pref2.putInt("s1c4", (s1c4 == null || s1c4 == 0) ? 0 : s1c4);
		
		pref2.putInt("s2c1", (s2c1 == null || s2c1 == 0) ? 0 : s2c1);
		pref2.putInt("s2c2", (s2c2 == null || s2c2 == 0) ? 0 : s2c2);
		pref2.putInt("s2c3", (s2c3 == null || s2c3 == 0) ? 0 : s2c3);
		pref2.putInt("s2c4", (s2c4 == null || s2c4 == 0) ? 0 : s2c4);
		
		pref2.putInt("s3c1", (s3c1 == null || s3c1 == 0) ? 0 : s3c1);
		pref2.putInt("s3c2", (s3c2 == null || s3c2 == 0) ? 0 : s3c2);
		pref2.putInt("s3c3", (s3c3 == null || s3c3 == 0) ? 0 : s3c3);
		pref2.putInt("s3c4", (s3c4 == null || s3c4 == 0) ? 0 : s3c4);
		
		pref2.putInt("s4c1", (s4c1 == null || s4c1 == 0) ? 0 : s4c1);
		pref2.putInt("s4c2", (s4c2 == null || s4c2 == 0) ? 0 : s4c2);
		pref2.putInt("s4c3", (s4c3 == null || s4c3 == 0) ? 0 : s4c3);
		pref2.putInt("s4c4", (s4c4 == null || s4c4 == 0) ? 0 : s4c4);
		
		flushNode(pref2);
		logger.debug("context addon");
		
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
