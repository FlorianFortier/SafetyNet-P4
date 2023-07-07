package com.safetyNet.alerts.api.repository;

import com.safetyNet.alerts.api.entity.Firestation;

import java.util.Optional;

public class FirestationRepository {
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
