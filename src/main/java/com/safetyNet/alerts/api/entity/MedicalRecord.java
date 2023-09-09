package com.safetyNet.alerts.api.entity;

import lombok.Data;

@Data
public class MedicalRecord {

        String firstName;
        String lastName;
        String birthdate;
        String[] medications;
        String[] allergies;

    /**
     *
     * @param firstName first name of the person
     * @param lastName last name of the person
     * @param birthdate birthdate of the person
     * @param medications Medications of the person
     * @param allergies Allergies of the person
     */
        public MedicalRecord(String firstName, String lastName, String birthdate, String[] medications, String[] allergies) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthdate = birthdate;
            this.medications = medications;
            this.allergies = allergies;
        }
}
