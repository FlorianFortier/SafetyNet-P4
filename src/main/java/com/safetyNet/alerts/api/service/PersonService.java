package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.repository.PersonRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Data
@Service
public class PersonService {

    private PersonRepository personRepository;

    public Optional<Person> getPerson(final Long id) {
        return personRepository.findById(id);
    }

    public Iterable<Person> getPersons() {
        return personRepository.findAll();
    }

    public void deletePerson(final Long id) {
        personRepository.deleteById(id);
    }

    public Person savePerson(Person person) {
        Person savedPerson = personRepository.save(person);
        return savedPerson;
    }

}



