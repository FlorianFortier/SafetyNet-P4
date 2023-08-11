package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.util.ReadDataFromJson;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FirestationRepository extends ReadDataFromJson {
    public Optional<Firestation> findById(Long id) {
        return Optional.empty();
    }

    public Iterable<Firestation> findAll() {
        return null;
    }

    public void deleteById(Long id) {

    }

    public Firestation save(Firestation firestation) {
        return null;
    }
}
