package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.repository.FirestationRepository;
import com.safetyNet.alerts.api.repository.MedicalRecordRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class MedicalRecordService {


    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Optional<MedicalRecord> getMedicalRecord(final Long id) {

        return medicalRecordRepository.findById(id);
    }

    public Iterable<MedicalRecord> getMedicalRecords() {

        return medicalRecordRepository.findAll();
    }

    public void deleteMedicalRecord(final Long id) {

        medicalRecordRepository.deleteById(id);
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);
        return savedMedicalRecord;
    }
}
