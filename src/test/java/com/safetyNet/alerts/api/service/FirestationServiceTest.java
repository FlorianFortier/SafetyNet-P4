package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.repository.FirestationRepository;
import com.safetyNet.alerts.api.service.FirestationService;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FirestationServiceTest {

    @Mock
    private FirestationRepository firestationRepository;

    @InjectMocks
    private FirestationService firestationService;
    final String address = "test1";
    final String station = "3";
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetFirestation() {
        Firestation mockFirestation = new Firestation(address, station);

        when(firestationRepository.findById(1L)).thenReturn(Optional.of(mockFirestation));

        Optional<Firestation> result = firestationService.getFirestation(1L);

        assertEquals(mockFirestation, result.orElse(null));
    }

    @Test
    public void testGetFirestations() {
        List<Firestation> mockFirestations = new ArrayList<>();
        mockFirestations.add(new Firestation(address, station));
        mockFirestations.add(new Firestation(address, station));

        when(firestationRepository.findAll()).thenReturn(mockFirestations);

        Iterable<Firestation> result = firestationService.getFirestations();

        assertEquals(mockFirestations, (List<Firestation>) result);
    }

    @Test
    public void testPersonByStation() {
        JSONArray mockJSONArray = new JSONArray();

        when(firestationRepository.personByStation("stationNumber")).thenReturn(mockJSONArray);

        JSONArray result = firestationService.personByStation("stationNumber");

        assertEquals(mockJSONArray, result);
    }

    @Test
    public void testFloodByStation() {
        List<String> stationNumbers = new ArrayList<>();
        stationNumbers.add("station1");
        stationNumbers.add("station2");

        JSONArray mockJSONArray = new JSONArray();

        when(firestationRepository.floodByStation(stationNumbers)).thenReturn(mockJSONArray);

        JSONArray result = firestationService.floodByStation(stationNumbers);

        assertEquals(mockJSONArray, result);
    }

    @Test
    public void testDeleteFirestation() {
        doNothing().when(firestationRepository).deleteById("testAddress", "testStation");

        firestationService.deleteFirestation("testAddress", "testStation");

        verify(firestationRepository, times(1)).deleteById("testAddress", "testStation");
    }

    @Test
    public void testSaveFirestation() {
        Firestation mockFirestation = new Firestation(address, station);

        when(firestationRepository.save(mockFirestation)).thenReturn(mockFirestation);

        Firestation result = firestationService.saveFirestation(mockFirestation);

        verify(firestationRepository, times(1)).save(mockFirestation);

        assertEquals(mockFirestation, result);
    }

    @Test
    public void testPostFirestation() {
        Firestation mockFirestation = new Firestation(address, station);

        when(firestationRepository.save(mockFirestation)).thenReturn(mockFirestation);

        Optional<Firestation> result = firestationService.postFirestation(mockFirestation);

        verify(firestationRepository, times(1)).save(mockFirestation);

        assertEquals(mockFirestation, result.orElse(null));
    }

    @Test
    public void testPutFirestation() {
        Firestation mockFirestation = new Firestation(address, station);
        mockFirestation.setAddress("testAddress");
        mockFirestation.setStation("testStation");

        when(firestationRepository.findById(1L)).thenReturn(Optional.of(mockFirestation));
        when(firestationRepository.update(1L, mockFirestation)).thenReturn(mockFirestation);

        Firestation result = firestationService.putFirestation(mockFirestation, 1L);

        verify(firestationRepository, times(1)).update(1L, mockFirestation);

        assertEquals(mockFirestation, result);
    }
}
