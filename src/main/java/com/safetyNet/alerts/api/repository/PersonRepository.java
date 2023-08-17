package com.safetyNet.alerts.api.repository;


import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.util.ReadDataFromJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository extends ReadDataFromJson {

    private final JSONObject personJSON = readJsonFile("D:\\Dev\\SafetyNet-P4\\src\\main\\resources\\dataSafetyNet.json");

    /**
     * Constructor
     *
     * @throws ParseException - If the JSON file is not found
     */
    public PersonRepository() throws ParseException {
    }

    public Optional<Person> findById(Long id) throws ParseException {
        return Optional.empty();
    }

    /**
     * Retrieve all Persons from the JSON file.
     * @return - An Iterable object of Persons
     */
    public Iterable<Person> findAll() {
        JSONArray PersonArray = (JSONArray) personJSON.get("persons");
        List<Person> PersonList = new ArrayList<>();

        //   Assuming Person class has a constructor that takes relevant properties as arguments.
        for (Object o : PersonArray) {
            JSONObject recordObj = (JSONObject) o;
            Person person = new Person(
                    // Extract and convert properties from recordObj to corresponding Person fields.
                    (String) recordObj.get("firstName"),
                    (String) recordObj.get("lastName"),
                    (String) recordObj.get("address"),
                    (String) recordObj.get("city"),
                    (String) recordObj.get("zip"),
                    (String) recordObj.get("phone"),
                    (String) recordObj.get("email")
            );
            PersonList.add(person);
        }
        return PersonList;
    }

    public void deleteById(Long id) {

    }

    public Person save(Person person) {
        return null;
    }
}
