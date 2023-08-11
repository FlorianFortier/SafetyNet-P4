package com.safetyNet.alerts.api.controller;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/firestation")
    public Iterable<Firestation> getFirestations() {

        return firestationService.getFirestations();
    }

    @PostMapping("/firestation")
        public Iterable<Firestation> postFirestations() {



        return firestationService.getFirestations();
    }



}
