package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.repository.PersonRepository;
import lombok.Data;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Data
@Service
public class PersonService {
    private final PersonRepository personRepository;

    /**
     * Constructor
     *
     * @param personRepository repository of Person
     */
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Read
     *
     * @param id index of array
     * @return A single Person
     */
    public Optional<Person> getPerson(final Long id) {
        return personRepository.findById(id);
    }
    public JSONArray childByAddress(final String address) {
        return personRepository.childByAddress(address);
    }
    /**
     * Read
     *
     * @return All Person
     */
    public Iterable<Person> getPersons() {
        return personRepository.findAll();
    }

    /**
     *
      * @param firestation
     * @return
     */
    public JSONArray phoneAlertByStation(String firestation) {

        return personRepository.phoneAlertByStation(firestation);
    }

    /**
     *
     * @param address
     * @return
     */
    public JSONArray fire(String address) {

        return personRepository.fire(address);
    }
    /**
     * @param person Request Body
     * @return newly created Person
     */
    public Optional<Person> postPersonRecord(Person person) {
        return Optional.ofNullable(personRepository.save(person));
    }

    /**
     * @param id     index of array
     * @param person Body Request
     * @return updated Person
     */
    public Person putPersonRecord(@PathVariable long id, Person person) {
        Optional<Person> getterResponse = personRepository.findById(id);
        Person updatedRecord = personRepository.update(id, person);
        Person recordObj = getterResponse.get();

        recordObj.setFirstName(updatedRecord.getFirstName());
        recordObj.setLastName(updatedRecord.getLastName());
        recordObj.setAddress(updatedRecord.getAddress());
        recordObj.setCity(updatedRecord.getCity());
        recordObj.setZip(updatedRecord.getZip());
        recordObj.setPhone(updatedRecord.getPhone());
        recordObj.setEmail(updatedRecord.getEmail());

        return recordObj;
    }

    /**
     * @param lastName  lastName is a filter used as identifier
     * @param firstName firstName is a filter used as identifier
     */
    public void deletePerson(final String lastName, String firstName) {
        personRepository.deleteById(lastName, firstName);
    }

    /**
     * @param person body request
     */
    public void savePerson(Person person) {
        personRepository.save(person);
    }
}



