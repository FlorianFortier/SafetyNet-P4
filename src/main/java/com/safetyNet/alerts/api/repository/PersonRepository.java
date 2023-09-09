package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.util.ReadDataFromJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository extends ReadDataFromJson {

    private final JSONObject personJSON = readJsonFile("D:\\Dev\\SafetyNet-P4\\src\\main\\resources\\dataSafetyNet.json");
    Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    JSONArray personArray = (JSONArray) personJSON.get("persons");

    /**
     * Constructor
     *
     * @throws ParseException - If the JSON file is not found
     */
    public PersonRepository() throws ParseException {
    }

    /**
     * @param id index of array
     * @return An Optional object of a single Person
     */
    public Optional<Person> findById(Long id) {
        JSONObject recordObj = (JSONObject) personArray.get(Math.toIntExact(id));
        Person person = new Person(
                // Extract and convert properties from recordObj to corresponding MedicalRecord fields.
                (String) recordObj.get("firstName"),
                (String) recordObj.get("lastName"),
                (String) recordObj.get("address"),
                (String) recordObj.get("city"),
                (String) recordObj.get("zip"),
                (String) recordObj.get("phone"),
                (String) recordObj.get("email")
        );
        logger.info("Person retrieved successfully");
        return Optional.of(person);
    }

    /**
     * Retrieve all Persons from the JSON file.
     *
     * @return - An Iterable object of Persons
     */
    public Iterable<Person> findAll() {

        List<Person> PersonList = new ArrayList<>();

        //  Assuming Person class has a constructor that takes relevant properties as arguments.
        for (Object o : personArray) {
            JSONObject recordObj = (JSONObject) o;
            Person person = new Person(
                    // Extract and convert properties from recordObj to corresponding Person fields.
                    (String) recordObj.get("firstName"),
                    (String) recordObj.get("lastName"),
                    (String) recordObj.get("address"),
                    (String) recordObj.get("city"),
                    (String) recordObj.get("zip"),
                    (String) recordObj.get("phone"),
                    (String) recordObj.get("email")
            );
            PersonList.add(person);
        }
        logger.info("List of Persons retrieved successfully");
        return PersonList;
    }

    /**
     * @param lastName  lastName is a filter used as identifier
     * @param firstName firstName is a filter used as identifier
     */
    public void deleteById(String lastName, String firstName) {
        JSONObject recordObj = (JSONObject) personArray.stream()
                .filter(person -> ((JSONObject) person).get("lastName").equals(lastName) &&
                        ((JSONObject) person).get("firstName").equals(firstName)).findFirst().get();
        personArray.remove(recordObj);
        logger.info("Person deleted successfully");
    }

    /**
     * @param person Body Request
     * @return newly Saved Person
     */
    public Person save(Person person) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", person.getFirstName());
        jsonObject.put("lastName", person.getLastName());
        jsonObject.put("address", person.getAddress());
        jsonObject.put("city", person.getCity());
        jsonObject.put("zip", person.getZip());
        jsonObject.put("phone", person.getPhone());
        jsonObject.put("email", person.getEmail());

        logger.info("Person saved successfully");
        return person;
    }

    /**
     * @param id     Array Index
     * @param person Body request
     * @return updated object of Person
     */
    public Person update(Long id, Person person) {
        JSONObject recordObj = (JSONObject) personArray.get(Math.toIntExact(id));
        Person newPerson = new Person(
                // Extract and convert properties from recordObj to corresponding MedicalRecord fields.
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getCity(),
                person.getZip(),
                person.getPhone(),
                person.getEmail()

        );
        logger.info("Person updated successfully");
        save(person);
        return newPerson;
    }
}
