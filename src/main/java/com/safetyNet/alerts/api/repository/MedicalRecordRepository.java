package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.MedicalRecord;
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
public class MedicalRecordRepository extends ReadDataFromJson {

    Logger logger = LoggerFactory.getLogger(MedicalRecordRepository.class);

    private final JSONObject medicalRecordJSON = readJsonFile("D:\\Dev\\SafetyNet-P4\\src\\main\\resources\\dataSafetyNet.json");

    /**
     * Constructor
     */
    public MedicalRecordRepository() throws ParseException {
    }


    /**
     * Read - Get one medicalRecord
     *
     * @param id - The id of the medicalRecord to retrieve
     * @return - An Optional medicalRecord if found, otherwise empty
     */
    public Optional<MedicalRecord> findById(Long id) throws ParseException {
        JSONArray medicalRecordArray = (JSONArray) medicalRecordJSON.get("medicalrecords");
        JSONObject recordObj = (JSONObject) medicalRecordArray.get(Math.toIntExact(id));
        MedicalRecord medicalRecord = new MedicalRecord(
                // Extract and convert properties from recordObj to corresponding MedicalRecord fields.
                (String) recordObj.get("firstName"),
                (String) recordObj.get("lastName"),
                (String) recordObj.get("birthdate"),
                (String[]) ((JSONArray) recordObj.get("medications")).toArray(new String[0]),
                (String[]) ((JSONArray) recordObj.get("allergies")).toArray(new String[0])
        );
        logger.info("MedicalRecord retrieved successfully");
        return Optional.of(medicalRecord);
    }

    /**
     * Read - Get all medicalRecords
     *
     * @return - An Iterable object of medicalRecords
     */
    public List<MedicalRecord> findAll() throws ParseException {
        JSONArray medicalRecorsArray = (JSONArray) medicalRecordJSON.get("medicalrecords");
        List<MedicalRecord> medicalRecordsList = new ArrayList<>();

//         Assuming MedicalRecord class has a constructor that takes relevant properties as arguments.
        for (Object o : medicalRecorsArray) {
            JSONObject recordObj = (JSONObject) o;
            MedicalRecord medicalRecord = new MedicalRecord(
                    // Extract and convert properties from recordObj to corresponding MedicalRecord fields.
                    (String) recordObj.get("firstName"),
                    (String) recordObj.get("lastName"),
                    (String) recordObj.get("birthdate"),
                    (String[]) ((JSONArray) recordObj.get("medications")).toArray(new String[0]),
                    (String[]) ((JSONArray) recordObj.get("allergies")).toArray(new String[0])
            );
            medicalRecordsList.add(medicalRecord);
            logger.info("MedicalRecord added successfully");
        }

        return medicalRecordsList;
    }

    public void deleteById(String lastName, String firstName) {
        JSONArray medicalRecordArray = (JSONArray) medicalRecordJSON.get("medicalrecords");
        JSONObject recordObj = (JSONObject) medicalRecordArray.stream()
                .filter(medicalRecord -> ((JSONObject) medicalRecord).get("lastName").equals(lastName) &&
                        ((JSONObject) medicalRecord).get("firstName").equals(firstName)).findFirst().get();
        medicalRecordArray.remove(recordObj);
        logger.info("MedicalRecord deleted successfully");

    }


    public MedicalRecord save(MedicalRecord medicalRecord) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", medicalRecord.getFirstName());
        jsonObject.put("lastName", medicalRecord.getLastName());
        jsonObject.put("birthdate", medicalRecord.getBirthdate());
        jsonObject.put("medications", medicalRecord.getMedications());
        jsonObject.put("allergies", medicalRecord.getAllergies());
        logger.info("MedicalRecord saved successfully");
        return medicalRecord;
    }

    public MedicalRecord update(Long id, MedicalRecord medicalRecord) throws ParseException {
        JSONArray medicalRecordArray = (JSONArray) medicalRecordJSON.get("medicalrecords");
        JSONObject recordObj = (JSONObject) medicalRecordArray.get(Math.toIntExact(id));
        recordObj.put("firstName", medicalRecord.getFirstName());
        recordObj.put("lastName", medicalRecord.getLastName());
        recordObj.put("birthdate", medicalRecord.getBirthdate());
        recordObj.put("medications", medicalRecord.getMedications());
        recordObj.put("allergies", medicalRecord.getAllergies());
        logger.info("MedicalRecord updated successfully");

        return medicalRecord;
    }
}
