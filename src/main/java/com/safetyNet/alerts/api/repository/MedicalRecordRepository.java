package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class MedicalRecordRepository {
    public Optional<MedicalRecord> findById(Long id) {
        return Optional.empty();
    }
    public Iterable<MedicalRecord> findAll() {
        return null;

    }
    public void deleteById(Long id) {

    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        return null;
    }
}
