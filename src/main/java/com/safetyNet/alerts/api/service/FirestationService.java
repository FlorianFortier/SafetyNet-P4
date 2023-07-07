package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.repository.FirestationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class FirestationService {

    @Autowired
    private final FirestationRepository firestationRepository;

    public FirestationService(FirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    public Optional<Firestation> getFirestation(final Long id) {
        return firestationRepository.findById(id);
    }

    public Iterable<Firestation> getFirestations() {
        return firestationRepository.findAll();
    }

    public void deleteFirestation(final Long id) {
        firestationRepository.deleteById(id);
    }

    public Firestation saveFirestation(Firestation firestation) {
        Firestation savedFirestation = firestationRepository.save(firestation);
        return savedFirestation;
    }

}



