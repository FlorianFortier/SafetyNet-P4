package com.safetyNet.alerts.api.entity;

import lombok.Data;

@Data
public class MedicalRecord {
        Long id;
        String firstName;
        String lastName;
        String birthdate;
        String[] medications;
        String[] allergies;

        public MedicalRecord(String firstName, String lastName, String birthdate, String[] medications, String[] allergies) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthdate = birthdate;
            this.medications = medications;
            this.allergies = allergies;
        }
}
