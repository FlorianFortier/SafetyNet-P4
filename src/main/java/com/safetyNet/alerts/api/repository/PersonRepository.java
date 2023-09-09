package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.util.ReadDataFromJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.safetyNet.alerts.api.repository.FirestationRepository.calculateAge;
import static com.safetyNet.alerts.api.repository.FirestationRepository.findMatchingMedicalRecord;

/**
 * Repository class for managing Person data.
 */
@Repository
public class PersonRepository extends ReadDataFromJson {

    private final JSONObject personJSON = readJsonFile("D:\\Dev\\SafetyNet-P4\\src\\main\\resources\\dataSafetyNet.json");
    Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    JSONArray personArray = (JSONArray) personJSON.get("persons");
    JSONArray medicalRecords = (JSONArray) personJSON.get("medicalrecords");

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
        JSONArray childByAddressWithRelatives = new JSONArray();
        JSONArray relatives = new JSONArray();
        Set<LinkedHashMap<String, Object>> relativesSet = new HashSet<>(); // Using set to get rid of duplications

        for (Object personObj : personArray) {
            JSONObject personJson = (JSONObject) personObj;
            String personAddress = (String) personJson.get("address");

            if (personAddress.equals(address)) {
                String personFirstName = (String) personJson.get("firstName");
                String personLastName = (String) personJson.get("lastName");
                JSONObject matchingMedicalRecord = findMatchingMedicalRecord(personFirstName, personLastName, medicalRecords);

                if (matchingMedicalRecord != null) {
                    String birthdate = (String) matchingMedicalRecord.get("birthdate");
                    int age = calculateAge(birthdate);
                    if (age <= 18) {
                        LinkedHashMap<String, Object> personData = new LinkedHashMap<>();
                        personData.put("firstName", personFirstName);
                        personData.put("lastName", personLastName);
                        personData.put("address", personAddress);
                        personData.put("age", age);

                        childByAddressWithRelatives.add(personData); // Add person to Set to eliminate duplication
                    } else if (!childByAddressWithRelatives.isEmpty()) {
                        LinkedHashMap<String, Object> personData = new LinkedHashMap<>();
                        personData.put("firstName", personFirstName);
                        personData.put("lastName", personLastName);
                        personData.put("address", personAddress);
                        personData.put("city", personJson.get("city"));
                        personData.put("zip", personJson.get("zip"));
                        personData.put("phone", personJson.get("phone"));
                        personData.put("email", personJson.get("email"));
                        personData.put("age", age);
                        relativesSet.add(personData);
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
        logger.info("Children with relatives retrieved successfully");
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

    /**
     * Retrieve detailed information about a person by their first name and last name.
     *
     * @param firstName The first name of the person.
     * @param lastName  The last name of the person.
     * @return A JSONObject containing detailed information about the person.
     */
    public JSONArray personInfo(String firstName, String lastName) {
        JSONArray returnPersonArray = new JSONArray();

        Optional<JSONObject> personOptional = personArray.stream()
                .filter(person -> ((JSONObject) person).get("firstName").equals(firstName) &&
                        ((JSONObject) person).get("lastName").equals(lastName))
                .findFirst();

        if (personOptional.isPresent()) {
            JSONObject personJson = personOptional.get();

            Optional<JSONObject> medicalRecordOptional = medicalRecords.stream()
                    .filter(medicalRecord -> ((JSONObject) medicalRecord).get("firstName").equals(firstName) &&
                            ((JSONObject) medicalRecord).get("lastName").equals(lastName))
                    .findFirst();

            if (medicalRecordOptional.isPresent()) {
                JSONObject medicalRecordJson = medicalRecordOptional.get();
                JSONArray allergies = (JSONArray) medicalRecordJson.get("allergies");
                JSONArray medications = (JSONArray) medicalRecordJson.get("medications");

                int age = calculateAge((String) medicalRecordJson.get("birthdate"));

                personJson.put("age", age);
                personJson.put("allergies", allergies);
                personJson.put("medications", medications);

                returnPersonArray.add(personJson);
                logger.info("Person retrieved successfully");
            }
        }

        return returnPersonArray;
    }


    /**
     * Retrieve phone numbers of individuals served by a fire station.
     *
     * @param firestationNumber The number of the fire station.
     * @return A JSONArray containing phone numbers of individuals served by the fire station.
     */
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
        logger.info("List of phone numbers retrieved successfully");
        return phonesNumbersList;
    }

    /**
     * Retrieve a list of residents at a given address who may require assistance in case of fire.
     *
     * @param address The address to search for.
     * @return A JSONArray containing information about residents at the address.
     */
    public JSONArray fire(String address) {
        JSONArray persons = (JSONArray) personJSON.get("persons");
        JSONArray firestations = (JSONArray) personJSON.get("firestations");
        JSONArray medicalRecords = (JSONArray) personJSON.get("medicalrecords");
        JSONArray residentList = new JSONArray();

        for (Object firestationObj : firestations) {
            JSONObject firestation = (JSONObject) firestationObj;
            if (firestation.get("address").equals(address)) {
                for (Object personJson : persons) {
                    JSONObject personObj = (JSONObject) personJson;
                    String personAddress = (String) personObj.get("address");

                    if (personAddress.equals(address)) {
                        String personLastName = (String) personObj.get("lastName");
                        String personFirstName = (String) personObj.get("firstName");
                        String personPhone = (String) personObj.get("phone");
                        JSONObject matchingMedicalRecord = findMatchingMedicalRecord(personFirstName, personLastName, medicalRecords);

                        if (matchingMedicalRecord != null) {
                            String birthdate = (String) matchingMedicalRecord.get("birthdate");
                            int age = calculateAge(birthdate);

                            JSONObject resident = new JSONObject();
                            resident.put("firstName", personFirstName);
                            resident.put("lastName", personLastName);
                            resident.put("phone", personPhone);
                            resident.put("age", age);
                            resident.put("allergies", matchingMedicalRecord.get("allergies"));
                            resident.put("medications", matchingMedicalRecord.get("medications"));
                            resident.put("stationNumber", firestation.get("station"));

                            residentList.add(resident);
                        }
                    }
                }
            }
        }
        logger.info("List of residents retrieved successfully");
        return residentList;
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
