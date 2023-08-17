package com.safetyNet.alerts.api.controller;


import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.service.MedicalRecordService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordsService;
    /**
     * Read - Get all medicalRecords
     *
     * @return - An Iterable object of Employee full filled
     */
    @GetMapping("/MedicalRecords")
    public Iterable<MedicalRecord> getMedicalRecords() throws ParseException {
        return medicalRecordsService.getMedicalRecords();
    }
    @GetMapping("/MedicalRecord")
    public ResponseEntity<MedicalRecord> getMedicalRecord(@RequestParam Long id) {
        Optional<MedicalRecord> medicalRecordOptional;

        try {
            medicalRecordOptional = medicalRecordsService.getMedicalRecord(id);

            return medicalRecordOptional.map(medicalRecord -> new ResponseEntity<>(medicalRecord, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
