package com.safetyNet.alerts.api.controller;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.service.FirestationService;
import com.safetyNet.alerts.api.service.MedicalRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordController {
    private MedicalRecordService medicalRecordsService;
    /**
     * Read - Get all medicalRecords
     *
     * @return - An Iterable object of Employee full filled
     */
    @GetMapping("/MedicalRecord")
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordsService.getMedicalRecords();
    }
}
