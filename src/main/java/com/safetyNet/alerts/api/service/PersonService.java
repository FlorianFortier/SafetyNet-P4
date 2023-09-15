package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.repository.PersonRepository;
import lombok.Data;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class PersonService {

    private final PersonRepository personRepository;

    /**
     * Constructor
     *
     * @param personRepository The repository for Person entities
     */
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Read - Retrieve a single Person by their identifier.
     *
     * @param id The index of the person to retrieve
     * @return An Optional containing the retrieved Person or empty if not found
     */
    public Optional<Person> getPerson(final Long id) {
        return personRepository.findById(id);
    }

    /**
     * Read - Retrieve all Persons.
     *
     * @return Iterable of all Person entities
     */
    public Iterable<Person> getPersons() {
        return personRepository.findAll();
    }

    /**
     * Retrieve a list of children at a given address.
     *
     * @param address Address for which to retrieve the list of children
     * @return JSONArray containing children's information
     */
    public JSONArray childByAddress(final String address) {
        return personRepository.childByAddress(address);
    }

    /**
     * Retrieve a list of phone numbers for a given fire station.
     *
     * @param firestation Fire station number for which to retrieve phone numbers
     * @return JSONArray containing phone numbers
     */
    public JSONArray phoneAlertByStation(String firestation) {
        return personRepository.phoneAlertByStation(firestation);
    }

    /**
     * Retrieve a person's information by their first name and last name.
     *
     * @param firstName First name of the person
     * @param lastName  Last name of the person
     * @return JSONArray containing the person's information
     */
    public JSONArray personInfo(String firstName, String lastName) {
        return personRepository.personInfo(firstName, lastName);
    }

    /**
     * Retrieve a list of persons affected by a fire at a given address.
     *
     * @param address Address of the fire
     * @return JSONArray containing information of persons affected by the fire
     */
    public JSONArray fire(String address) {
        return personRepository.fire(address);
    }

    /**
     * Retrieve a list of unique email addresses for residents of a specific city.
     *
     * @param city The city for which to retrieve the email addresses.
     * @return A JSONArray containing a single JSONObject with a "mails" field, which is a set of unique email addresses.
     */
    public JSONArray communityEmail(String city) {
        // Delegate the request to the corresponding repository method to handle the data retrieval.
        return personRepository.communityEmail(city);
    }

    /**
     * Create a new person.
     *
     * @param person Request Body containing the information of the person to create
     * @return Optional containing the newly created Person or empty if creation fails
     */
    public Optional<Person> postPersonRecord(Person person) {
        return Optional.ofNullable(personRepository.save(person));
    }

    /**
     * Update the information of an existing person.
     *
     * @param id     Index of the person to update
     * @param person Request Body containing the new information of the person
     * @return The updated Person entity
     */
    public Person putPersonRecord(long id, Person person) {
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
     * Delete a person by their last name and first name.
     *
     * @param lastName  Last name of the person to delete
     * @param firstName First name of the person to delete
     */
    public void deletePerson(final String lastName, String firstName) {
        personRepository.deleteById(lastName, firstName);
    }

    /**
     * Save a Person entity.
     *
     * @param person The Person entity to save
     */
    public void savePerson(Person person) {
        personRepository.save(person);
    }
}
