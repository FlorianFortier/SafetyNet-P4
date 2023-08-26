package com.safetyNet.alerts.api.controller;


import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.service.MedicalRecordService;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController

public class MedicalRecordController {
    Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    Optional<MedicalRecord> medicalRecordOptional;

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

        try {
            medicalRecordOptional = medicalRecordsService.getMedicalRecord(id);

            return medicalRecordOptional.map(medicalRecord -> new ResponseEntity<>(medicalRecord, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/MedicalRecord")
    @ResponseBody
    public ResponseEntity<MedicalRecord> postMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        Optional<MedicalRecord> medicalRecordOptional;


        medicalRecordOptional = medicalRecordsService.postMedicalRecord(medicalRecord);

        return medicalRecordOptional.map(medicalRecordToPost
                -> new ResponseEntity<>(medicalRecord, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @PutMapping("/MedicalRecord/{id}")
    public ResponseEntity<MedicalRecord> putMedicalRecord(@RequestBody MedicalRecord medicalRecord, @PathVariable Long id) throws IOException, ParseException {
        MedicalRecord updatedRecord = medicalRecordsService.putMedicalRecord(medicalRecord, id);
        if (updatedRecord != null) {
            return ResponseEntity.ok(updatedRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/MedicalRecord")
    @ResponseBody
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestParam String lastName, String firstName) throws ParseException {



        medicalRecordsService.deleteMedicalRecord(lastName, firstName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
