package com.safetyNet.alerts.api.controller;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.service.FirestationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirestationController {

    private FirestationService firestationService;
    /**
     * Read - Get all firestations
     *
     * @return - An Iterable object of Employee full filled
     */
    @GetMapping("/firestation")
    public Iterable<Firestation> getFirestations() {

        return firestationService.getFirestations();
    }



}
