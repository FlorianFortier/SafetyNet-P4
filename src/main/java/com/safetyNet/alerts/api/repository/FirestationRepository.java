package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.util.ReadDataFromJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FirestationRepository extends ReadDataFromJson {
    private final JSONObject FirestationRecordJSON = readJsonFile("D:\\Dev\\SafetyNet-P4\\src\\main\\resources\\dataSafetyNet.json");


    /**
     * Constructor
     */
    public FirestationRepository() throws ParseException {
    }

    /**
     *
     */
    public Optional<Firestation> findById(Long id) {
        return Optional.empty();
    }

    /**
     * Retrieve all Firestation from the JSON file.
     */
    public Iterable<Firestation> findAll() {

        JSONArray FirestationRecordArray = (JSONArray) FirestationRecordJSON.get("firestations");

        List<Firestation> FirestationRecordList = new ArrayList<>();

        // Assuming Firestation class has a constructor that takes relevant properties as arguments.
        for (Object o : FirestationRecordArray) {
            JSONObject recordObj = (JSONObject) o;
            Firestation firestation = new Firestation(
                // Extract and convert properties from recordObj to corresponding Firestation fields.
                (String) recordObj.get("address"),
                (String) recordObj.get("station")
            );
            FirestationRecordList.add(firestation);
        }

        return FirestationRecordList;
    }

    /**
     *
     */
    public void deleteById(Long id) {

    }
    /**
     *
     */
    public Firestation save(Firestation firestation) {
        return null;
    }
}
