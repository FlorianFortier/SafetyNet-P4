package com.safetyNet.alerts.api.controller;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.service.PersonService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PersonController {


    @Autowired
    private PersonService personService;


    /**
     * Read - Get all persons
     * @return
     */
    @GetMapping("/Persons")
    public Iterable<Person> getPersons() {
        return personService.getPersons();
    }
    @GetMapping("/Person")
    public ResponseEntity<Person> getPerson(@RequestParam Long id) throws ParseException {
        Optional<Person> PersonOptional;

        PersonOptional = personService.getPerson(id);

        return PersonOptional.map(person -> new ResponseEntity<>(person, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
