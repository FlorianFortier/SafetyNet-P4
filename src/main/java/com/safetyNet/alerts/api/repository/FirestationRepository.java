package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Firestation;
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
public class FirestationRepository extends ReadDataFromJson {
    private final JSONObject firestationRecordJSON = readJsonFile("D:\\Dev\\SafetyNet-P4\\src\\main\\resources\\dataSafetyNet.json");
    Logger logger = LoggerFactory.getLogger(FirestationRepository.class);


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

    /**
     *
     * @param address address is a filter used as identifier
     * @param station station is a filter used as identifier
     */
    public void deleteById(String address, String station) {
        JSONArray firestationRecordArray = (JSONArray) firestationRecordJSON.get("firestations");
        JSONObject recordObj = (JSONObject) firestationRecordArray.stream()
                .filter(medicalRecord -> ((JSONObject) medicalRecord).get("address").equals(address) &&
                        ((JSONObject) medicalRecord).get("station").equals(station)).findFirst().get();
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
