package com.safetyNet.alerts.api.controller;

import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.service.PersonService;
import org.json.simple.JSONArray;
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
     * Retrieve the list of all persons.
     *
     * @return List of all persons
     */
    @GetMapping("/Persons")
    public Iterable<Person> getPersons() {
        return personService.getPersons();
    }

    /**
     * Retrieve a person by their identifier.
     *
     * @param id Person's identifier
     * @return ResponseEntity with the person or a HttpStatus.NOT_FOUND code
     * @throws ParseException in case of parsing error
     */
    @GetMapping("/Person")
    public ResponseEntity<Person> getPerson(@RequestParam Long id) throws ParseException {
        Optional<Person> personOptional = personService.getPerson(id);
        return personOptional.map(person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieve a list of children at a given address.
     *
     * @param address Address for which we want to retrieve the list of children
     * @return JSONArray containing children's information
     * @throws ParseException in case of parsing error
     */
    @GetMapping("/childAlert")
    public JSONArray childByAddress(@RequestParam String address) throws ParseException {
        return personService.childByAddress(address);
    }

    /**
     * Retrieve a list of phone numbers for a given fire station.
     *
     * @param firestation Fire station number for which we want to retrieve phone numbers
     * @return JSONArray containing phone numbers
     */
    @GetMapping("/phoneAlert")
    public JSONArray phoneAlertByStation(@RequestParam String firestation) {
        return personService.phoneAlertByStation(firestation);
    }

    /**
     * Retrieve a person's information by their first name and last name.
     *
     * @param firstName First name of the person
     * @param lastName  Last name of the person
     * @return JSONArray containing the person's information
     */
    @GetMapping("/personInfo")
    public JSONArray personInfo(@RequestParam String firstName, String lastName) {
        return personService.personInfo(firstName, lastName);
    }

    /**
     * Retrieve a list of persons affected by a fire at a given address.
     *
     * @param address Address of the fire
     * @return JSONArray containing information of persons affected by the fire
     */
    @GetMapping("/fire")
    public JSONArray fire(@RequestParam String address) {
        return personService.fire(address);
    }
    /**
     * retrieve a list of unique email addresses for residents of a specific city.
     *
     * @param city The city for which to retrieve the email addresses.
     * @return A JSONArray conta²ining a single JSONObject with a "mails" field, which is a set of unique email addresses.
     */
    @GetMapping("/communityEmail")
    public JSONArray communityEmail(@RequestParam String city) {
        // Delegate the request to the corresponding service method to handle the business logic.
        return personService.communityEmail(city);
    }
    /**
     * Create a new person.
     *
     * @param person Request Body containing the information of the person to create
     * @return ResponseEntity with the new person or a HttpStatus.NOT_FOUND code
     */
    @PostMapping("/Person")
    @ResponseBody
    public ResponseEntity<Person> postPerson(@RequestBody Person person) {
        Optional<Person> personRecordOptional = personService.postPersonRecord(person);
        return personRecordOptional.map(personRecordToPost
                -> new ResponseEntity<>(person, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update the information of an existing person.
     *
     * @param person Request Body containing the new information of the person
     * @param id     Identifier of the person to update
     * @return ResponseEntity with the updated person or a HttpStatus.NOT_FOUND code
     * @throws ParseException in case of parsing error
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
     * Delete a person by their last name and first name.
     *
     * @param lastName  Last name of the person to delete
     * @param firstName First name of the person to delete
     * @return ResponseEntity with a HttpStatus.OK code
     */
    @DeleteMapping("/Person")
    @ResponseBody
    public ResponseEntity<Person> deletePersonRecord(@RequestParam String lastName, String firstName) {
        personService.deletePerson(lastName, firstName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
