package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordsServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Bean
    public MedicalRecordService medicalRecordService() {
        return new MedicalRecordService(medicalRecordRepository);
    }


    @Autowired
    private MedicalRecordService medicalRecordService;
    @MockBean
    private MedicalRecordRepository medicalRecordRepository;

    public void shouldReturnTheRightMedicalRecord() {

//        MedicalRecord found = medicalRecordService.getMedicalRecord()
    }
}
