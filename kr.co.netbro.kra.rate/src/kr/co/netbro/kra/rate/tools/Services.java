package kr.co.netbro.kra.rate.tools;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import kr.co.netbro.kra.database.PersonDBService;

public class Services {
	public static PersonDBService getPersonService()
    {
        BundleContext bundleContext = FrameworkUtil.getBundle( Services.class ).getBundleContext();

        ServiceReference<PersonDBService> reference = bundleContext.getServiceReference( PersonDBService.class );
        PersonDBService service = bundleContext.getService( reference );
        return service;
    }
}
