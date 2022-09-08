package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{

    private static List<Person> db = new ArrayList<>();

        @Override
        public int insertPerson(UUID id, Person person) {
            db.add(new Person(id,person.getName()));
            return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return db;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return db.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> person = selectPersonById(id);
        if(person.isEmpty()) return 0;
        db.remove(person.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person personNew) {
            return selectPersonById(id)
                    .map(person->{
                        int indexOfPersonToDelete = db.indexOf(person);
                        if(indexOfPersonToDelete>=0){
                            db.set(indexOfPersonToDelete, new Person(id, personNew.getName()));
                            return 1;
                        }
                        return 0;
                    }).orElse(0);

    }



}
