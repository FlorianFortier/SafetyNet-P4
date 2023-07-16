package com.safetyNet.alerts.api.controller;

import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {


    @Autowired
    private final PersonService personService;
    /**
     * Read - Get all medicalRecords
     *
     * @return - An Iterable object of Employee full filled
     */

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/Person")
    public Iterable<Person> getPersons() {

        return personService.getPersons();
    }


}
