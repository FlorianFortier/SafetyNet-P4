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
import java.util.Set;
import java.util.HashSet;

import static com.safetyNet.alerts.api.repository.FirestationRepository.calculateAge;

/**
 * Repository class for managing Person data.
 */
@Repository
public class PersonRepository extends ReadDataFromJson {

    private final JSONObject personJSON = readJsonFile("D:\\Dev\\SafetyNet-P4\\src\\main\\resources\\dataSafetyNet.json");
    Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    JSONArray personArray = (JSONArray) personJSON.get("persons");

    /**
     * Constructor for PersonRepository.
     *
     * @throws ParseException - If the JSON file is not found
     */
    public PersonRepository() throws ParseException {
    }

    /**
     * Find a Person by their ID.
     *
     * @param id The index of the Person in the JSON array.
     * @return An Optional object containing the found Person, or empty if not found.
     */
    public Optional<Person> findById(Long id) {
        JSONObject recordObj = (JSONObject) personArray.get(Math.toIntExact(id));
        Person person = new Person(
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
     * Find children by address and include their relatives.
     *
     * @param address The address to search for.
     * @return A JSONArray containing children and their relatives.
     */
    public JSONArray childByAddress(String address) {
        JSONArray persons = (JSONArray) personJSON.get("persons");
        JSONArray medicalRecords = (JSONArray) personJSON.get("medicalrecords");
        JSONArray childByAddressWithRelatives = new JSONArray();
        JSONArray relatives = new JSONArray();
        Set<Person> relativesSet = new HashSet<>(); // Using set to get rid of duplications

        for (Object personObj : persons) {
            JSONObject personJson = (JSONObject) personObj;
            String personAddress = (String) personJson.get("address");

            if (personAddress.equals(address)) {
                String personFirstName = (String) personJson.get("firstName");
                String personLastName = (String) personJson.get("lastName");
                JSONObject matchingMedicalRecord = FirestationRepository.findMatchingMedicalRecord(personFirstName, personLastName, medicalRecords);

                if (matchingMedicalRecord != null) {
                    String birthdate = (String) matchingMedicalRecord.get("birthdate");
                    int age = calculateAge(birthdate);
                    if (age <= 18) {
                        Person person = new Person(
                                personFirstName,
                                personLastName,
                                personAddress,
                                (String) personJson.get("city"),
                                (String) personJson.get("zip"),
                                (String) personJson.get("phone"),
                                (String) personJson.get("email"),
                                age
                        );
                        childByAddressWithRelatives.add(person); // Add person to Set to eliminate duplication
                    } else if (!childByAddressWithRelatives.isEmpty()) {
                        Person person = new Person(
                                personFirstName,
                                personLastName,
                                personAddress,
                                (String) personJson.get("city"),
                                (String) personJson.get("zip"),
                                (String) personJson.get("phone"),
                                (String) personJson.get("email"),
                                age
                        );
                        relativesSet.add(person);
                    }
                }
            }
        }
        if (!relativesSet.isEmpty()) {
            // Creates an object to hold "Set" of relatives
            JSONObject relativesObject = new JSONObject();
            relativesObject.put("relatives", relativesSet);

            // Adds object "relativesObject" to "childByAddressWithRelatives"
            childByAddressWithRelatives.add(relativesObject);
        }
        logger.info("Childs with relatives retrieved successfully");
        return childByAddressWithRelatives;
    }

    /**
     * Retrieve all Persons from the JSON file.
     *
     * @return An Iterable object of Persons.
     */
    public Iterable<Person> findAll() {
        List<Person> PersonList = new ArrayList<>();
        for (Object o : personArray) {
            JSONObject recordObj = (JSONObject) o;
            Person person = new Person(
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

    public JSONArray phoneAlertByStation(String firestationNumber) {
        JSONArray persons = (JSONArray) personJSON.get("persons");
        JSONArray firestations = (JSONArray) personJSON.get("firestations");
        JSONArray phonesNumbersList = new JSONArray();
        for (Object firestationObj : firestations) {
            JSONObject firestation = (JSONObject) firestationObj;
            if (firestation.get("station").equals(firestationNumber)) {
                String firestationAddress = (String) firestation.get("address");
                for (Object personObj : persons) {
                    JSONObject personJson = (JSONObject) personObj;
                    String personAddress = (String) personJson.get("address");
                    if (personAddress.equals(firestationAddress)) {
                        String personPhone = (String) personJson.get("phone");
                        phonesNumbersList.add(personPhone);
                    }
                }
            }
        }
        return phonesNumbersList;
    }


    /**
     * Delete a Person by their last name and first name.
     *
     * @param lastName  The last name of the Person.
     * @param firstName The first name of the Person.
     */
    public void deleteById(String lastName, String firstName) {
        JSONObject recordObj = (JSONObject) personArray.stream()
                .filter(person -> ((JSONObject) person).get("lastName").equals(lastName) &&
                        ((JSONObject) person).get("firstName").equals(firstName)).findFirst().get();
        personArray.remove(recordObj);
        logger.info("Person deleted successfully");
    }

    /**
     * Save a new Person.
     *
     * @param person The Person to be saved.
     * @return The saved Person.
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
     * Update an existing Person.
     *
     * @param id     The index of the Person in the JSON array.
     * @param person The updated Person data.
     * @return The updated Person object.
     */
    public Person update(Long id, Person person) {
        JSONObject recordObj = (JSONObject) personArray.get(Math.toIntExact(id));
        Person newPerson = new Person(
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
