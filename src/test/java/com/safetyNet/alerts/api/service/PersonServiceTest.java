package com.safetyNet.alerts.api.service;

import com.safetyNet.alerts.api.entity.Person;
import com.safetyNet.alerts.api.repository.PersonRepository;
import com.safetyNet.alerts.api.service.PersonService;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    final String firstName = "test";
    final String lastName = "test";
    final String address = "test";
    final String city = "test";
    final String zip = "test";
    final String phone = "test";
    final String email = "test";
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPerson() {
        Person mockPerson = new Person(firstName,lastName,address,city,zip,phone,email);

        when(personRepository.findById(1L)).thenReturn(Optional.of(mockPerson));

        Optional<Person> result = personService.getPerson(1L);

        assertEquals(mockPerson, result.orElse(null));
    }

    @Test
    public void testGetPersons() {
        List<Person> mockPersons = new ArrayList<>();
        mockPersons.add(new Person(firstName,lastName,address,city,zip,phone,email));
        mockPersons.add(new Person(firstName,lastName,address,city,zip,phone,email));

        when(personRepository.findAll()).thenReturn(mockPersons);

        Iterable<Person> result = personService.getPersons();

        assertEquals(mockPersons, (List<Person>) result);
    }

    @Test
    public void testChildByAddress() {
        JSONArray mockJSONArray = new JSONArray();

        when(personRepository.childByAddress("testAddress")).thenReturn(mockJSONArray);

        JSONArray result = personService.childByAddress("testAddress");

        assertEquals(mockJSONArray, result);
    }

    @Test
    public void testPhoneAlertByStation() {
        JSONArray mockJSONArray = new JSONArray();

        when(personRepository.phoneAlertByStation("testStation")).thenReturn(mockJSONArray);

        JSONArray result = personService.phoneAlertByStation("testStation");

        assertEquals(mockJSONArray, result);
    }

    @Test
    public void testPersonInfo() {
        JSONArray mockJSONArray = new JSONArray();

        when(personRepository.personInfo("testFirstName", "testLastName")).thenReturn(mockJSONArray);

        JSONArray result = personService.personInfo("testFirstName", "testLastName");

        assertEquals(mockJSONArray, result);
    }

    @Test
    public void testFire() {
        JSONArray mockJSONArray = new JSONArray();

        when(personRepository.fire("testAddress")).thenReturn(mockJSONArray);

        JSONArray result = personService.fire("testAddress");

        assertEquals(mockJSONArray, result);
    }

    @Test
    public void testCommunityEmail() {
        JSONArray mockJSONArray = new JSONArray();

        when(personRepository.communityEmail("testCity")).thenReturn(mockJSONArray);

        JSONArray result = personService.communityEmail("testCity");

        assertEquals(mockJSONArray, result);
    }

    @Test
    public void testPostPersonRecord() {
        Person mockPerson = new Person(firstName,lastName,address,city,zip,phone,email);

        when(personRepository.save(mockPerson)).thenReturn(mockPerson);

        Optional<Person> result = personService.postPersonRecord(mockPerson);

        verify(personRepository, times(1)).save(mockPerson);

        assertEquals(mockPerson, result.orElse(null));
    }

    @Test
    public void testPutPersonRecord() {
        Person mockPerson = new Person(firstName,lastName,address,city,zip,phone,email);
        mockPerson.setFirstName("testFirstName");
        mockPerson.setLastName("testLastName");

        when(personRepository.findById(1L)).thenReturn(Optional.of(mockPerson));
        when(personRepository.update(1L, mockPerson)).thenReturn(mockPerson);

        Person result = personService.putPersonRecord(1L, mockPerson);

        verify(personRepository, times(1)).update(1L, mockPerson);

        assertEquals(mockPerson, result);
    }

    @Test
    public void testDeletePerson() {
        doNothing().when(personRepository).deleteById("testLastName", "testFirstName");

        personService.deletePerson("testLastName", "testFirstName");

        verify(personRepository, times(1)).deleteById("testLastName", "testFirstName");
    }

    @Test
    public void testSavePerson() {
        Person mockPerson = new Person(firstName,lastName,address,city,zip,phone,email);

        personService.savePerson(mockPerson);

        verify(personRepository, times(1)).save(mockPerson);
    }
}
