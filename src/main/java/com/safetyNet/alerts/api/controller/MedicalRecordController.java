package com.safetyNet.alerts.api.controller;


import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.service.MedicalRecordService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordsService;
    /**
     * Read - Get all medicalRecords
     *
     * @return - An Iterable object of Employee full filled
     */
    @GetMapping("/MedicalRecord")
    public Iterable<MedicalRecord> getMedicalRecords() throws ParseException {
        return medicalRecordsService.getMedicalRecords();
    }
}
