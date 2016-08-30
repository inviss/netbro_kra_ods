package kr.co.netbro.kra.database;

import kr.co.netbro.kra.entity.Person;

public interface IPersonDBChangeObserver {
    void changed( String eventID, Person affectedPerson ); 
}