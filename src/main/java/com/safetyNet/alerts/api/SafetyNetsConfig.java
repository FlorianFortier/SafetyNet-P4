package com.safetyNet.alerts.api;

import com.safetyNet.alerts.api.repository.MedicalRecordRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SafetyNetsConfig {
    @Bean
    public MedicalRecordRepository medicalRecordRepository() {
        return new MedicalRecordRepository();
    }

}
