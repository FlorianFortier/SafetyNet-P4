package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.repository.MedicalRecordRepository;
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

public class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordService medicalRecordService;
    final String firstName = "test";
    final String lastName = "test";
    final String birthdate = "12/13/2200";
    final String[] medications = new String[]{"test", "test"};
    final String[] allergies = new String[]{"test", "test"};
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testGetMedicalRecord() throws org.json.simple.parser.ParseException {

        MedicalRecord mockMedicalRecord = new MedicalRecord(firstName, lastName, birthdate, medications, allergies);

        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(mockMedicalRecord));

        Optional<MedicalRecord> result = medicalRecordService.getMedicalRecord(1L);

        assertEquals(mockMedicalRecord, result.orElse(null));
    }

    @Test
    public void testGetMedicalRecords() throws org.json.simple.parser.ParseException {
        List<MedicalRecord> mockMedicalRecords = new ArrayList<>();
        mockMedicalRecords.add(new MedicalRecord(firstName, lastName, birthdate, medications, allergies));
        mockMedicalRecords.add(new MedicalRecord(firstName, lastName, birthdate, medications, allergies));

        when(medicalRecordRepository.findAll()).thenReturn(mockMedicalRecords);

        Iterable<MedicalRecord> result = medicalRecordService.getMedicalRecords();

        assertEquals(mockMedicalRecords, (List<MedicalRecord>) result);
    }

    @Test
    public void testDeleteMedicalRecord() {
        doNothing().when(medicalRecordRepository).deleteById("testLastName", "testFirstName");

        medicalRecordService.deleteMedicalRecord("testLastName", "testFirstName");

        verify(medicalRecordRepository, times(1)).deleteById("testLastName", "testFirstName");
    }

    @Test
    public void testSaveMedicalRecord() {
        MedicalRecord mockMedicalRecord = new MedicalRecord(firstName, lastName, birthdate, medications, allergies);

        when(medicalRecordRepository.save(mockMedicalRecord)).thenReturn(mockMedicalRecord);

        medicalRecordService.saveMedicalRecord(mockMedicalRecord);

        verify(medicalRecordRepository, times(1)).save(mockMedicalRecord);
    }
}
