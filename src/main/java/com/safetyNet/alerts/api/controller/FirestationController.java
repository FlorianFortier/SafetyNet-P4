package com.safetyNet.alerts.api.controller;

import com.safetyNet.alerts.api.entity.Firestation;

import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.service.FirestationService;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class FirestationController {
    Optional<Firestation> firestationOptional;
    @Autowired
    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    /**
     * Read - Get all firestations
     *
     * @return - An Iterable object of Employee full filled
     */
    @GetMapping("/Firestations")
    public Iterable<Firestation> getFirestations() {

        return firestationService.getFirestations();
    }

    /**
     *
     * @param stationNumber
     * @return
     */
    @GetMapping("/firestation")
    public JSONArray personByStation(@RequestParam String stationNumber) {

        return firestationService.personByStation(stationNumber);
    }
    @GetMapping("/flood/stations")
    public JSONArray floodByStation(@RequestParam List<String> stationsNumber) {

        return firestationService.floodByStation(stationsNumber);
    }
    /**
     * @param id Array Index
     * @return An Object of a Single Firestation
     */
    @GetMapping("/Firestation")
    public ResponseEntity<Firestation> getFirestation(@RequestParam Long id) {


        firestationOptional = firestationService.getFirestation(id);

        return firestationOptional.map(firestation -> new ResponseEntity<>(firestation, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * @param firestation Body request
     * @return newly created Firestation
     */
    @PostMapping("/Firestation")
    @ResponseBody
    public ResponseEntity<Firestation> postFirestation(@RequestBody Firestation firestation) {


        firestationOptional = firestationService.postFirestation(firestation);

        return firestationOptional.map(firestationToPost
                -> new ResponseEntity<>(firestation, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * @param firestation Body Request
     * @param id          Array index
     * @return Updated Firestation
     * @throws ParseException In case of JSON parsing errors
     */
    @PutMapping("/Firestation/{id}")
    public ResponseEntity<Firestation> putFirestationRecord(@RequestBody Firestation firestation, @PathVariable Long id) throws ParseException {
        Firestation updatedRecord = firestationService.putFirestation(firestation, id);
        if (updatedRecord != null) {
            firestationService.saveFirestation(firestation);
            return ResponseEntity.ok(updatedRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param address address is a filter used as identifier
     * @param station station is a filter used as identifier
     * @return Http Code
     */
    @DeleteMapping("/Firestation")
    public ResponseEntity<Firestation> deleteFirestation(@RequestParam String address, String station) {


        firestationService.deleteFirestation(address, station);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
