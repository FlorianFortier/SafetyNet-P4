package com.safetyNet.alerts.api.controller;

import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.service.PersonService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PersonController {


    @Autowired
    private PersonService personService;


    /**
     * Read - Get all persons
     * @return List of all persons
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

    /**
     *
     * @param person Body request
     * @return The newly created Person
     */
    @PostMapping("/Person")
    @ResponseBody
    public ResponseEntity<Person> postMedicalRecord(@RequestBody Person person) {
        Optional<Person> personRecordOptional;


        personRecordOptional = personService.postPersonRecord(person);

        return personRecordOptional.map(personRecordToPost
                -> new ResponseEntity<>(person, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     *
     * @param person Request Body
     * @param id index of array
     * @return Updated Person
     * @throws ParseException throws exeption in case of parsing error with JSON
     */
    @PutMapping("/Person/{id}")
    public ResponseEntity<Person> putPersonRecord(@RequestBody Person person, @PathVariable Long id) throws ParseException {
        Person updatedRecord = personService.putPersonRecord(id, person);
        if (updatedRecord != null) {
            personService.savePerson(person);
            return ResponseEntity.ok(updatedRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     *
     * @param lastName lastName is a filter used as identifier
     * @param firstName firtName is a filter used as identifier
     * @return Http code
     */
    @DeleteMapping("/Person")
    @ResponseBody
    public ResponseEntity<Person> deletePersonRecord(@RequestParam String lastName, String firstName) {



        personService.deletePerson(lastName, firstName);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
