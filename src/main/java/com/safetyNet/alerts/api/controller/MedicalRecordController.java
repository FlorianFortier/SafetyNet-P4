package com.safetyNet.alerts.api.controller;


import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.service.MedicalRecordService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
public class MedicalRecordController {


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

    /**
     * @param id Get a single Medical Record
     * @return Response Entity of Medical Record
     */
    @GetMapping("/MedicalRecord")
    public ResponseEntity<MedicalRecord> getMedicalRecord(@RequestParam Long id) {

        try {
            medicalRecordOptional = medicalRecordsService.getMedicalRecord(id);

            return medicalRecordOptional.map(medicalRecord -> new ResponseEntity<>(medicalRecord, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param medicalRecord Body Request
     * @return Response Entity of newly created Medical Record
     */
    @PostMapping("/MedicalRecord")
    @ResponseBody
    public ResponseEntity<MedicalRecord> postMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        Optional<MedicalRecord> medicalRecordOptional;


        medicalRecordOptional = medicalRecordsService.postMedicalRecord(medicalRecord);

        return medicalRecordOptional.map(medicalRecordToPost
                -> new ResponseEntity<>(medicalRecord, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * @param medicalRecord Body Request
     * @param id            index of Array
     * @return Response Entity of updated Medical Record
     * @throws ParseException in case of JSON parsing Errors
     */
    @PutMapping("/MedicalRecord/{id}")
    public ResponseEntity<MedicalRecord> putMedicalRecord(@RequestBody MedicalRecord medicalRecord, @PathVariable Long id) throws ParseException {
        MedicalRecord updatedRecord = medicalRecordsService.putMedicalRecord(id, medicalRecord);
        if (updatedRecord != null) {
            medicalRecordsService.saveMedicalRecord(medicalRecord);
            return ResponseEntity.ok(updatedRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param lastName  lastName is a filter used as identifier
     * @param firstName firtName is a filter used as identifier
     * @return Http Code
     */
    @DeleteMapping("/MedicalRecord")
    @ResponseBody
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestParam String lastName, String firstName) {


        medicalRecordsService.deleteMedicalRecord(lastName, firstName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
