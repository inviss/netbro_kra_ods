package kr.co.netbro.race.database.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.netbro.kra.entity.Cancel;
import kr.co.netbro.kra.entity.Change;
import kr.co.netbro.kra.entity.Final;
import kr.co.netbro.kra.entity.Result;
import kr.co.netbro.race.database.RateODSService;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RateODSServiceImpl implements RateODSService {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private EntityManagerFactory emf;
    private EntityManager em;

	protected void activate() {
		Map map = new HashMap();
        map.put( PersistenceUnitProperties.CLASSLOADER, getClass().getClassLoader() );

        org.eclipse.persistence.jpa.PersistenceProvider persistenceProvider = new org.eclipse.persistence.jpa.PersistenceProvider();
        emf = persistenceProvider.createEntityManagerFactory( "kra.oracle.jpa", map );
        em = emf.createEntityManager();
	}
	
	protected void deactivate() {
		em.close();
        emf.close();
        em = null;
        emf = null;
	}

	@Override
	public List<Cancel> findCancels() {
		Query q = em.createNamedQuery("Rate.Cancel").setParameter(1, "20160906").setParameter(2, "1");
        return q.getResultList(); 
	}

	@Override
	public List<Change> findChanges() {
		Query q = em.createNamedQuery("Rate.Change").setParameter(2, "20160906").setParameter(1, "1");
        return q.getResultList(); 
	}

	@Override
	public List<Final> findFinals() {
		Query q = em.createNamedQuery("Rate.Final").setParameter(1, "20160906").setParameter(2, "1");
        return q.getResultList(); 
	}

	@Override
	public List<Result> findResults() {
		Query q = em.createNamedQuery("Rate.Result").setParameter(1, "20160906").setParameter(2, "1");
        return q.getResultList(); 
	}
}
