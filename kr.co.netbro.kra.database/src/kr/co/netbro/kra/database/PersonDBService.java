package kr.co.netbro.kra.database;

import java.util.List;

import kr.co.netbro.kra.entity.Person;

public interface PersonDBService {
    void addPerson( Person person );

    void modifyPerson( Person person );

    void removePersion( Person person );

    List<Person> getPersons();

    void registerPersonObserver( IPersonDBChangeObserver observer );

    void unregisterPersonObserver( IPersonDBChangeObserver observer );
}
