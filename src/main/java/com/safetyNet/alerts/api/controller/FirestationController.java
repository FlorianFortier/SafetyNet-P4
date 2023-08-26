package com.safetyNet.alerts.api.controller;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.service.FirestationService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FirestationController {

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
    public Iterable<Firestation> getFirestations() throws ParseException {

        return firestationService.getFirestations();
    }
    @GetMapping("/Firestation")
    public ResponseEntity<Firestation> getFirestation(@RequestParam Long id) {
        Optional<Firestation> FirestationOptional;

        FirestationOptional = firestationService.getFirestation(id);

        return FirestationOptional.map(firestation -> new ResponseEntity<>(firestation, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/Firestation")
        public Iterable<Firestation> postFirestations() {



        return firestationService.getFirestations();
    }

    @DeleteMapping("/Firestation")
        public Iterable<Firestation> deleteFirestations(@RequestParam Long id ) {


        return null;
    }
}
