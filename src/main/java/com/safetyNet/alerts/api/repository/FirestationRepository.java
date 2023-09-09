package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.service.FirestationService;
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
import java.util.List;
import java.util.Optional;

@Repository
public class FirestationRepository extends ReadDataFromJson {
    private final JSONObject firestationRecordJSON = readJsonFile("D:\\Dev\\SafetyNet-P4\\src\\main\\resources\\dataSafetyNet.json");
    Logger logger = LoggerFactory.getLogger(FirestationRepository.class);

    FirestationService firestationService;

    /**
     * Constructor
     */
    public FirestationRepository() throws ParseException {
    }

    /**
     *
     * @param id Array index
     * @return a Single Firestation
     */
    public Optional<Firestation> findById(Long id) {

        JSONArray firestationArray = (JSONArray) firestationRecordJSON.get("firestations");
        JSONObject recordObj = (JSONObject) firestationArray.get(Math.toIntExact(id));
        Firestation firestation = new Firestation(
                // Extract and convert properties from recordObj to corresponding MedicalRecord fields.
                (String) recordObj.get("address"),
                (String) recordObj.get("station")
        );
        logger.info("station retrieved successfully");
        return Optional.of(firestation);
    }

    /**
     * Retrieve all Firestation from the JSON file.
     */
    public Iterable<Firestation> findAll() {

        JSONArray firestationRecordArray = (JSONArray) firestationRecordJSON.get("firestations");
        List<Firestation> firestationRecordList = new ArrayList<>();

        // Assuming Firestation class has a constructor that takes relevant properties as arguments.
        for (Object o : firestationRecordArray) {
            JSONObject recordObj = (JSONObject) o;
            Firestation firestation = new Firestation(
                // Extract and convert properties from recordObj to corresponding Firestation fields.
                (String) recordObj.get("address"),
                (String) recordObj.get("station")
            );
            firestationRecordList.add(firestation);
            logger.info("Firestation retrieved successfully");
        }

        return firestationRecordList;
    }
    public JSONArray personByStation(String stationNumber) {
        JSONArray firestations = (JSONArray) firestationRecordJSON.get("firestations");
        JSONArray personRelatedToStationRecordList = new JSONArray();
        int nbChild = 0;
        int nbAdult = 0;

        for (Object firestationObj : firestations) {
            JSONObject firestation = (JSONObject) firestationObj;
            if (firestation.get("station").equals(stationNumber)) {
                String firestationAddress = (String) firestation.get("address");

                JSONArray persons = (JSONArray) firestationRecordJSON.get("persons");
                JSONArray medicalRecords = (JSONArray) firestationRecordJSON.get("medicalrecords");

                for (Object personObj : persons) {
                    JSONObject personJson = (JSONObject) personObj;
                    String personAddress = (String) personJson.get("address");

                    if (personAddress.equals(firestationAddress)) {
                        String personFirstName = (String) personJson.get("firstName");
                        String personLastName = (String) personJson.get("lastName");

                        // Search for the matching medical record
                        JSONObject matchingMedicalRecord = findMatchingMedicalRecord(personFirstName, personLastName, medicalRecords);
                        if (matchingMedicalRecord != null) {
                            String birthdate = (String) matchingMedicalRecord.get("birthdate");
                            int age = calculateAge(birthdate); // Calculate age from birthdate

                            if (age < 18) {
                                nbChild++;
                            } else {
                                nbAdult++;
                            }

                            // Assuming there's a Person constructor that takes relevant properties as arguments
                            Person person = new Person(
                                    personFirstName,
                                    personLastName,
                                    personAddress,
                                    (String) personJson.get("city"),
                                    (String) personJson.get("zip"),
                                    (String) personJson.get("phone"),
                                    (String) personJson.get("email"),
                                    age // Add age to Person constructor
                                    // Add other relevant properties here
                            );
                            personRelatedToStationRecordList.add(person);
                        }
                    }
                }
            }
        }
        personRelatedToStationRecordList.add("Number of childs = " + nbChild);
        personRelatedToStationRecordList.add("Number of adults = " + nbAdult);
        logger.info("PersonByStation records retrieved successfully");
        logger.info("Number of Adults: " + nbAdult);
        logger.info("Number of Children: " + nbChild);
        return (JSONArray) personRelatedToStationRecordList;
    }

    /**
     *
     * @param firstName
     * @param lastName
     * @param medicalRecords
     * @return
     */
    private JSONObject findMatchingMedicalRecord(String firstName, String lastName, JSONArray medicalRecords) {
        for (Object medicalRecordObj : medicalRecords) {
            JSONObject medicalRecord = (JSONObject) medicalRecordObj;
            if (medicalRecord.get("firstName").equals(firstName) && medicalRecord.get("lastName").equals(lastName)) {
                return medicalRecord;
            }
        }
        return null;
    }

    /**
     *
     * @param birthdate
     * @return
     */
    private int calculateAge(String birthdate) {
        try {
            // Parse the birthdate string into a LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // Replace with your date format
            LocalDate birthDate = LocalDate.parse(birthdate, formatter);

            // Calculate the age based on the current date
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(birthDate, currentDate);

            // Return the age
            return period.getYears();
        } catch (Exception e) {
            // Handle parsing errors or other exceptions as needed
            e.printStackTrace();
            return 0; // Default age in case of an error
        }
    }
    /**
     *
     * @param address address is a filter used as identifier
     * @param station station is a filter used as identifier
     */
    public void deleteById(String address, String station) {
        JSONArray firestationRecordArray = (JSONArray) firestationRecordJSON.get("firestations");
        JSONObject recordObj = (JSONObject) firestationRecordArray.stream()
                .filter(firestation -> ((JSONObject) firestation).get("address").equals(address) &&
                        ((JSONObject) firestation).get("station").equals(station)).findFirst().get();
        firestationRecordArray.remove(recordObj);
        logger.info("Firestation deleted successfully");
    }

    /**
     *
     * @param firestation Body Request
     * @return saved Firestation
     */
    public Firestation save(Firestation firestation) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", firestation.getAddress());
        jsonObject.put("station", firestation.getStation());
        logger.info("Firestation saved successfully");
        return firestation;
    }

    /**
     *
     * @param id Array Index
     * @param firestation Body Request
     * @return updated Firestation
     */
    public Firestation update(Long id, Firestation firestation)  {
        JSONArray firestationArray = (JSONArray) firestationRecordJSON.get("firestations");
        JSONObject recordObj = (JSONObject) firestationArray.get(Math.toIntExact(id));
        Firestation newFirestation = new Firestation(
                // Extract and convert properties from recordObj to corresponding MedicalRecord fields.
                firestation.getAddress(),
                firestation.getStation()
        );
        logger.info("Firestation updated successfully");
        save(firestation);

        return newFirestation;
    }
}
