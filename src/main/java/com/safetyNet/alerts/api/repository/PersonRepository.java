package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Person;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PersonRepository {


    public Optional<Person> findById(Long id) throws ParseException {
        return Optional.empty();
    }

    public Iterable<Person> findAll() {
        return null;
    }

    public void deleteById(Long id) {

    }

    public Person save(Person person) {
        return null;
    }
}
