package com.safetyNet.alerts.api.controller;

import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private PersonService personService;
    /**
     * Read - Get all medicalRecords
     *
     * @return - An Iterable object of Employee full filled
     */
    @GetMapping("/Person")
    public Iterable<Person> getPersons() {
        return personService.getPersons();
    }

}
