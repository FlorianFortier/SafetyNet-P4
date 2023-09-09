package com.safetyNet.alerts.api.entity;

import lombok.Data;

@Data
public class Person {

    String firstName;
    String lastName;
    String address;
    String city;
    String zip;
    String phone;
    String email;
    Integer age;
    /**
     *  Standard Contrustor
     * @param firstName First name of the person
     * @param lastName Last name of the person
     * @param address Address of the person
     * @param city City of the person
     * @param zip Zip code of the person
     * @param phone Phone number of the person
     * @param email Email of the person
     */
    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city=city;
        this.zip=zip;
        this.phone=phone;
        this.email=email;
    }

    /**
     * Contructor for Age of person result of LEFT JOINT with medical Record
     * @param firstName
     * @param lastName
     * @param address
     * @param city
     * @param zip
     * @param phone
     * @param email
     * @param age
     */
    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city=city;
        this.zip=zip;
        this.phone=phone;
        this.email=email;
        this.age=age;
    }

}
