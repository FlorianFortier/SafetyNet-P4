package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.util.ReadDataFromJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Firestation data.
 */
@Repository
public class FirestationRepository extends ReadDataFromJson {
    private final JSONObject firestationRecordJSON = readJsonFile("D:\\Dev\\SafetyNet-P4\\src\\main\\resources\\dataSafetyNet.json");
    static Logger logger = LoggerFactory.getLogger(FirestationRepository.class);
    JSONArray firestationRecordArray = (JSONArray) firestationRecordJSON.get("firestations");
    JSONArray persons = (JSONArray) firestationRecordJSON.get("persons");
    JSONArray medicalRecords = (JSONArray) firestationRecordJSON.get("medicalrecords");

    /**
     * Constructor.
     *
     * @throws ParseException If there is an error parsing JSON data.
     */
    public FirestationRepository() throws ParseException {
    }

    /**
     * Find a Firestation by its ID (array index).
     *
     * @param id The array index.
     * @return An optional containing the Firestation if found.
     */
    public Optional<Firestation> findById(Long id) {
        JSONObject recordObj = (JSONObject) firestationRecordArray.get(Math.toIntExact(id));
        Firestation firestation = new Firestation(
                (String) recordObj.get("address"),
                (String) recordObj.get("station")
        );
        logger.info("Firestation retrieved successfully");
        return Optional.of(firestation);
    }

    /**
     * Retrieve all Firestations from the JSON file.
     *
     * @return A list of Firestations.
     */
    public Iterable<Firestation> findAll() {
        List<Firestation> firestationRecordList = new ArrayList<>();

        for (Object o : firestationRecordArray) {
            JSONObject recordObj = (JSONObject) o;
            Firestation firestation = new Firestation(
                    (String) recordObj.get("address"),
                    (String) recordObj.get("station")
            );
            firestationRecordList.add(firestation);
            logger.info("Firestation retrieved successfully");
        }

        return firestationRecordList;
    }

    /**
     * Retrieve persons related to a fire station and count adults and children.
     *
     * @param stationNumber The station number.
     * @return A JSON array containing persons and count information.
     */
    public JSONArray personByStation(String stationNumber) {

        JSONArray personRelatedToStationRecordList = new JSONArray();
        int nbChild = 0;
        int nbAdult = 0;

        for (Object firestationObj : firestationRecordArray) {
            JSONObject firestation = (JSONObject) firestationObj;
            if (firestation.get("station").equals(stationNumber)) {
                String firestationAddress = (String) firestation.get("address");

                for (Object personObj : persons) {
                    JSONObject personJson = (JSONObject) personObj;
                    String personAddress = (String) personJson.get("address");

                    if (personAddress.equals(firestationAddress)) {
                        String personFirstName = (String) personJson.get("firstName");
                        String personLastName = (String) personJson.get("lastName");

                        JSONObject matchingMedicalRecord = findMatchingMedicalRecord(personFirstName, personLastName, medicalRecords);
                        if (matchingMedicalRecord != null) {
                            String birthdate = (String) matchingMedicalRecord.get("birthdate");
                            int age = calculateAge(birthdate);

                            if (age < 18) {
                                nbChild++;
                            } else {
                                nbAdult++;
                            }
                            LinkedHashMap<String, Object> personData = new LinkedHashMap<>();
                            personData.put("firstName", personFirstName);
                            personData.put("lastName", personLastName);
                            personData.put("address", personAddress);
                            personData.put("phone", personJson.get("phone"));
                            personData.put("age", age);

                            personRelatedToStationRecordList.add(personData);
                        }
                    }
                }
            }
        }
        // Add count information to the JSON array
        personRelatedToStationRecordList.add("Number of childs = " + nbChild);
        personRelatedToStationRecordList.add("Number of adults = " + nbAdult);
        logger.info("PersonByStation records retrieved successfully");
        logger.info("Number of Adults: " + nbAdult);
        logger.info("Number of Children: " + nbChild);
        return (JSONArray) personRelatedToStationRecordList;
    }

    /**
     * Retrieve persons related to multiple fire stations.
     *
     * @param stationsNumber The list of station numbers.
     * @return A JSON array containing information about persons related to each station.
     */
    public JSONArray floodByStation(List<String> stationsNumber) {

        JSONArray result = new JSONArray();

        for (String stationNumber : stationsNumber) {
            JSONObject stationData = new JSONObject();
            stationData.put("stationNumber", stationNumber);

            JSONArray personList = new JSONArray();

            for (Object firestationObj : firestationRecordArray) {
                JSONObject firestation = (JSONObject) firestationObj;
                if (firestation.get("station").equals(stationNumber)) {
                    String firestationAddress = (String) firestation.get("address");

                    for (Object personObj : persons) {
                        JSONObject personJson = (JSONObject) personObj;
                        String personAddress = (String) personJson.get("address");

                        if (personAddress.equals(firestationAddress)) {
                            String personLastName = (String) personJson.get("lastName");
                            String personFirstName = (String) personJson.get("firstName");
                            String personPhone = (String) personJson.get("phone");

                            JSONObject matchingMedicalRecord = findMatchingMedicalRecord(personFirstName, personLastName, medicalRecords);

                            if (matchingMedicalRecord != null) {
                                String birthdate = (String) matchingMedicalRecord.get("birthdate");
                                int age = calculateAge(birthdate);

                                // Création d'un LinkedHashMap pour stocker les informations dans l'ordre souhaité
                                LinkedHashMap<String, Object> personData = new LinkedHashMap<>();
                                personData.put("allergies", new ArrayList<>());
                                personData.put("firstName", personFirstName);
                                personData.put("lastName", personLastName);
                                personData.put("address", personJson.get("address"));
                                personData.put("phone", personPhone);
                                personData.put("medications", new ArrayList<>());
                                personData.put("age", age);

                                personList.add(personData);
                            }
                        }
                    }
                }
            }
            result.add(stationData);
            stationData.put("personList", personList);
            logger.info(" List of person by station retrieve successfully");
        }

        return result;
    }

    /**
     * Find a matching medical record for a person.
     *
     * @param firstName      The person's first name.
     * @param lastName       The person's last name.
     * @param medicalRecords The list of medical records.
     * @return The matching medical record if found, or null if not found.
     */
    public static JSONObject findMatchingMedicalRecord(String firstName, String lastName, JSONArray medicalRecords) {
        for (Object medicalRecordObj : medicalRecords) {
            JSONObject medicalRecord = (JSONObject) medicalRecordObj;
            if (medicalRecord.get("firstName").equals(firstName) && medicalRecord.get("lastName").equals(lastName)) {
                return medicalRecord;
            }
        }
        return null;
    }

    /**
     * Calculate age based on birthdate.
     *
     * @param birthdate The birthdate string.
     * @return The calculated age.
     */
    public static int calculateAge(String birthdate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthDate = LocalDate.parse(birthdate, formatter);
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(birthDate, currentDate);
            return period.getYears();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur de parse sur la date");
            return 0;
        }
    }

    /**
     * Delete a Firestation by address and station number.
     *
     * @param address The address to delete.
     * @param station The station number to delete.
     */
    public void deleteById(String address, String station) {
        JSONObject recordObj = (JSONObject) firestationRecordArray.stream()
                .filter(firestation -> ((JSONObject) firestation).get("address").equals(address) &&
                        ((JSONObject) firestation).get("station").equals(station)).findFirst().get();
        firestationRecordArray.remove(recordObj);
        logger.info("Firestation deleted successfully");
    }

    /**
     * Save a Firestation.
     *
     * @param firestation The Firestation to save.
     * @return The saved Firestation.
     */
    public Firestation save(Firestation firestation) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", firestation.getAddress());
        jsonObject.put("station", firestation.getStation());
        logger.info("Firestation saved successfully");
        return firestation;
    }

    /**
     * Update a Firestation by ID (array index).
     *
     * @param id          The ID (array index) of the Firestation to update.
     * @param firestation The updated Firestation data.
     * @return The updated Firestation.
     */
    public Firestation update(Long id, Firestation firestation) {
        JSONArray firestationArray = (JSONArray) firestationRecordJSON.get("firestations");
        JSONObject recordObj = (JSONObject) firestationArray.get(Math.toIntExact(id));
        Firestation newFirestation = new Firestation(
                firestation.getAddress(),
                firestation.getStation()
        );
        logger.info("Firestation updated successfully");
        save(firestation);
        return newFirestation;
    }
}