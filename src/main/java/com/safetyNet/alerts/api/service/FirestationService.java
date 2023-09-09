package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.repository.FirestationRepository;
import lombok.Data;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Data
@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;

    /**
     * Constructor
     *
     * @param firestationRepository repository of Firestation
     */
    public FirestationService(FirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    /**
     * @param id array of index
     * @return An object of a single firestation
     */
    public Optional<Firestation> getFirestation(final Long id) {

        return firestationRepository.findById(id);
    }

    /**
     * @return All Firestations
     */
    public Iterable<Firestation> getFirestations() {

        return firestationRepository.findAll();
    }

    public JSONArray personByStation(String stationNumber) {

        return firestationRepository.personByStation(stationNumber);
    }

    /**
     * @param address Adress is a filter used as identifier
     * @param station station is a filter used as identifier
     */
    public void deleteFirestation(final String address, String station) {

        firestationRepository.deleteById(address, station);
    }

    /**
     * @param firestation Body Request
     * @return Saved firestation
     */
    public Firestation saveFirestation(Firestation firestation) {
        return firestationRepository.save(firestation);
    }

    /**
     * @param firestation Body Request
     * @return Optional Object of newly created Firestation
     */
    public Optional<Firestation> postFirestation(Firestation firestation) {
        return Optional.ofNullable(firestationRepository.save(firestation));
    }

    /**
     * @param firestation Body request
     * @param id          Array index
     * @return updated Firestation Object
     */
    public Firestation putFirestation(Firestation firestation, @PathVariable long id) {
        Optional<Firestation> getterResponse = firestationRepository.findById(id);
        Firestation recordObj = getterResponse.get();
        recordObj.setAddress(firestation.getAddress());
        recordObj.setStation(firestation.getStation());

        firestationRepository.save(recordObj);

        return recordObj;
    }
}



