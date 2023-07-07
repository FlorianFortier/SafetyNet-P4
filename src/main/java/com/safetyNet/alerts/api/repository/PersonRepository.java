package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.Person;

import java.util.Optional;

public class PersonRepository {

    public Optional<Person> findById(Long id) {
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
