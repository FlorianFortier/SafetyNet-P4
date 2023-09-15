package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.repository.MedicalRecordRepository;
import lombok.Data;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Data
@Service
public class MedicalRecordService {


    private final MedicalRecordRepository medicalRecordRepository;

    /**
     * @param medicalRecordRepository Constructor
     */
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    /**
     * @param id index of array
     * @return A single medical record
     * @throws ParseException
     */
    public Optional<MedicalRecord> getMedicalRecord(final Long id) throws ParseException {

        return medicalRecordRepository.findById(id);
    }

    /**
     * @return All Medical Records
     * @throws ParseException in case of JSON parsing exeption
     */
    public Iterable<MedicalRecord> getMedicalRecords() throws ParseException {

        return medicalRecordRepository.findAll();
    }

    /**
     * @param lastName  lastName is a filter used as identifier
     * @param firstName firtName is a filter used as identifier
     */
    public void deleteMedicalRecord(final String lastName, String firstName) {

        medicalRecordRepository.deleteById(lastName, firstName);
    }

    /**
     * @param medicalRecord Request Body
     */
    public void saveMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.save(medicalRecord);
    }

    /**
     * @param medicalRecord Request Body
     * @return newly created Medical record
     */
    public Optional<MedicalRecord> postMedicalRecord(MedicalRecord medicalRecord) {
        return Optional.ofNullable(medicalRecordRepository.save(medicalRecord));
    }

    /**
     * @param id            index of array
     * @param medicalRecord Request Body
     * @return updated Medical Record
     * @throws ParseException In case of errors with parsing JSON
     */
    public MedicalRecord putMedicalRecord(@PathVariable long id, MedicalRecord medicalRecord) throws ParseException {
        Optional<MedicalRecord> getterResponse = medicalRecordRepository.findById(id);
        MedicalRecord updatedRecord = medicalRecordRepository.update(id, medicalRecord);
        MedicalRecord recordObj = getterResponse.get();

        recordObj.setFirstName(updatedRecord.getFirstName());
        recordObj.setLastName(updatedRecord.getLastName());
        recordObj.setBirthdate(updatedRecord.getBirthdate());
        recordObj.setMedications(updatedRecord.getMedications());
        recordObj.setAllergies(updatedRecord.getAllergies());


        return recordObj;
    }
}
