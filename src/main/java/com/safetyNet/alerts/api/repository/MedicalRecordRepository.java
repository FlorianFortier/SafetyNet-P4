package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.util.ReadDataFromJson;
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
    /**
     * Read - Get one medicalRecord
     * @param id - The id of the medicalRecord to retrieve
     * @return - An Optional medicalRecord if found, otherwise empty
     * @throws ParseException
     */
    public Optional<MedicalRecord> findById(Long id) throws ParseException {
        JSONObject medicalRecord = readJsonFile("src/main/resources/dataSafetyNet.json");
        medicalRecord.get("medicalrecords");
        return Optional.empty();
    }

    /**
     * Read - Get all medicalRecords
     * @return - An Iterable object of Employee full filled
     * @throws ParseException
     */
    public List<MedicalRecord> findAll() throws ParseException {
        JSONObject medicalRecordJSON = readJsonFile("dataSafetyNet.json");
        JSONObject jsonObject = (JSONObject) medicalRecordJSON.get("medicalrecords");
        Iterator<JSONObject> iterator = jsonObject.values().iterator();
        List<MedicalRecord> medicalRecordsList = new ArrayList<>();

        // Assuming MedicalRecord class has a constructor that takes relevant properties as arguments.
        while (iterator.hasNext()) {
            JSONObject recordObj = iterator.next();
            MedicalRecord medicalRecord = new MedicalRecord(
                    // Extract and convert properties from recordObj to corresponding MedicalRecord fields.
                    (String) recordObj.get("firstName"),
                    (String) recordObj.get("lastname"),
                    (String) recordObj.get("birthdate"),
                    (String[]) recordObj.get("medications"),
                    (String[]) recordObj.get("allergies")
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
