package com.safetyNet.alerts.api.controller;

import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {


    @Autowired
    private PersonService personService;


    /**
     * Read - Get all persons
     * @return
     */
    @GetMapping("/Person")
    public Iterable<Person> getPersons() {
        return personService.getPersons();
    }
}
