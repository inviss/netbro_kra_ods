package kr.co.netbro.kra.database.internal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import kr.co.netbro.kra.database.IPersonDBChangeObserver;
import kr.co.netbro.kra.database.PersonDBService;
import kr.co.netbro.kra.database.PersonEvent;
import kr.co.netbro.kra.entity.Person;

public class PersonDBServiceImpl implements PersonDBService
{
    private EntityManagerFactory h2emf;
    private EntityManager h2em;
    private EntityManagerFactory oracleEmf;
    private EntityManager oracleEm;
    private List<IPersonDBChangeObserver> observers;

    @SuppressWarnings("unchecked")
    protected void activate() {
        @SuppressWarnings("rawtypes")
        Map map = new HashMap();
        map.put( PersistenceUnitProperties.CLASSLOADER, getClass().getClassLoader() );
       
        org.eclipse.persistence.jpa.PersistenceProvider persistenceProvider1 = new org.eclipse.persistence.jpa.PersistenceProvider();
        h2emf = persistenceProvider1.createEntityManagerFactory( "kra.h2.jpa", map );
        h2em = h2emf.createEntityManager();
        
        org.eclipse.persistence.jpa.PersistenceProvider persistenceProvider2 = new org.eclipse.persistence.jpa.PersistenceProvider();
        oracleEmf = persistenceProvider2.createEntityManagerFactory( "kra.h2.jpa", map );
        oracleEm = h2emf.createEntityManager();

        observers = new LinkedList<>();
    }

    protected void deactivate()
    {
        observers = null;
        h2em.close();
        h2emf.close();
        h2em = null;
        h2emf = null;
        
        oracleEm.close();
        oracleEmf.close();
        oracleEm = null;
        oracleEmf = null;
    }

    @Override
    public void addPerson( Person person )
    {
//        em.getTransaction().begin();
//        em.persist( person );
//        em.getTransaction().commit();
//        sendEvent( PersonEvent.ADDED, person );
    }

    @Override
    public void modifyPerson( Person person )
    {
//        em.getTransaction().begin();
//        em.merge( person );
//        em.getTransaction().commit();
//        sendEvent( PersonEvent.CHANGED, person );
    }

    @Override
    public void removePersion( Person person )
    {
//        em.getTransaction().begin();
//        Person find = em.find( Person.class, person.getId() );
//        em.remove( find );
//        em.getTransaction().commit();
//        sendEvent( PersonEvent.REMOVED, person );
    }

    @Override
    public List<Person> getPersons()
    {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Person> cq = cb.createQuery( Person.class );
//        Root<Person> rootEntry = cq.from( Person.class );
//        CriteriaQuery<Person> all = cq.select( rootEntry );
//        TypedQuery<Person> allQuery = em.createQuery( all );
//
//        List<Person> resultList = allQuery.getResultList();
//        return resultList;
    	return null;
    }

    private void sendEvent( String eventID, Person affectedPerson )
    {
        for( IPersonDBChangeObserver observer : observers )
            observer.changed( eventID, affectedPerson );
    }

    @Override
    public void registerPersonObserver( IPersonDBChangeObserver observer )
    {
        if( !observers.contains( observer ) )
            observers.add( observer );
    }

    @Override
    public void unregisterPersonObserver( IPersonDBChangeObserver observer )
    {
        if( observers.contains( observer ) )
            observers.remove( observer );
    }
}