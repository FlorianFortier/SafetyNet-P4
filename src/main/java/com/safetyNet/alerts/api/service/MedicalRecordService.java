package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.repository.MedicalRecordRepository;
import lombok.Data;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Data
@Service
public class MedicalRecordService {


    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Optional<MedicalRecord> getMedicalRecord(final Long id) throws ParseException {

        return medicalRecordRepository.findById(id);
    }

    public Iterable<MedicalRecord> getMedicalRecords() throws ParseException {

        return medicalRecordRepository.findAll();
    }

    public void deleteMedicalRecord(final String lastName, String firstName) throws ParseException {

        medicalRecordRepository.deleteById(lastName, firstName);
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public Optional<MedicalRecord> postMedicalRecord(MedicalRecord medicalRecord) {
        return Optional.ofNullable(medicalRecordRepository.save(medicalRecord));
    }

    public MedicalRecord putMedicalRecord(MedicalRecord medicalRecord, @PathVariable long id) throws ParseException {
         Optional<MedicalRecord> getterResponse = medicalRecordRepository.findById(id);
            MedicalRecord recordObj = getterResponse.get();
            recordObj.setFirstName(medicalRecord.getFirstName());
            recordObj.setLastName(medicalRecord.getLastName());
            recordObj.setBirthdate(medicalRecord.getBirthdate());
            recordObj.setMedications(medicalRecord.getMedications());
            recordObj.setAllergies(medicalRecord.getAllergies());
            medicalRecordRepository.save(recordObj);

            return recordObj;
    }
}
