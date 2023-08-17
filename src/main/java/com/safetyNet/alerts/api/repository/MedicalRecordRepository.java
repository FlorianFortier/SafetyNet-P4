package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.util.ReadDataFromJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Repository
public class MedicalRecordRepository extends ReadDataFromJson {

    private final JSONObject medicalRecordJSON = readJsonFile("D:\\Dev\\SafetyNet-P4\\src\\main\\resources\\dataSafetyNet.json");

    /**
     * Constructor
     */
    public MedicalRecordRepository() throws ParseException {
    }


    /**
     * Read - Get one medicalRecord
     * @param id - The id of the medicalRecord to retrieve
     * @return - An Optional medicalRecord if found, otherwise empty
     */
    public Optional<MedicalRecord> findById(Long id) throws ParseException {
        JSONArray medicalRecorsArray = (JSONArray) medicalRecordJSON.get(id);

        return Optional.empty();
    }

    /**
     * Read - Get all medicalRecords
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
        }

        return medicalRecordsList;
    }
    public void deleteById(Long id) {

    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        return null;
    }
}
