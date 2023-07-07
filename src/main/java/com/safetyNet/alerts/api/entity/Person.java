package com.safetyNet.alerts.api.entity;

import lombok.Data;

@Data
public class Person {
    Long id;
    String firstName;
    String lastName;
    String address;
    String city;
    String zip;
    String phone;
    String email;

    public Person() {
    }


}
