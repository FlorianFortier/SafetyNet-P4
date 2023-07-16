package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.repository.PersonRepository;
import com.safetyNet.alerts.api.util.ReadDataFromJson;
import lombok.Data;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Data
@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> getPerson(final Long id) throws ParseException {
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



