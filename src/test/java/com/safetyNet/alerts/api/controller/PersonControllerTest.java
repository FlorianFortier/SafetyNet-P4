package com.safetyNet.alerts.api.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPerson() throws Exception {
        mockMvc.perform(get("/Persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$[0].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[0].city", is("Culver")))
                .andExpect(jsonPath("$[0].zip", is("97451")))
                .andExpect(jsonPath("$[0].phone", is("841-874-6512")))
                .andExpect(jsonPath("$[0].email", is("jaboyd@email.com")));


    }
    @Test
    public void testGetCommunityEmail() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mails[0]", is("drk@email.com")))
                .andExpect(jsonPath("$[0].mails[1]", is("soph@email.com")))
                .andExpect(jsonPath("$[0].mails[2]", is("reg@email.com")))
                .andExpect(jsonPath("$[0].mails[3]", is("jaboyd@email.com")));

    }
    @Test
    public void testGetPersonInfo() throws Exception {
        mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].zip", is("97451")))
                .andExpect(jsonPath("$[0].allergies[0]", is("nillacilan")))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$[0].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[0].city", is("Culver")))
                .andExpect(jsonPath("$[0].phone", is("841-874-6512")))
                .andExpect(jsonPath("$[0].medications[0]", is("aznol:350mg")))
                .andExpect(jsonPath("$[0].medications[1]", is("hydrapermazol:100mg")))
                .andExpect(jsonPath("$[0].email", is("jaboyd@email.com")))
                .andExpect(jsonPath("$[0].age", is(39)));
    }
    @Test
    public void testGetFireByAddress() throws Exception {
        mockMvc.perform(get("/fire?address=892 Downing Ct"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].allergies[0]", is("peanut")))
                .andExpect(jsonPath("$[0].allergies[1]", is("shellfish")))
                .andExpect(jsonPath("$[0].allergies[2]", is("aznol")))
                .andExpect(jsonPath("$[0].firstName", is("Sophia")))
                .andExpect(jsonPath("$[0].lastName", is("Zemicks")))
                .andExpect(jsonPath("$[0].stationNumber", is("2")))
                .andExpect(jsonPath("$[0].phone", is("841-874-7878")))
                .andExpect(jsonPath("$[0].medications[0]", is("aznol:60mg")))
                .andExpect(jsonPath("$[0].medications[1]", is("hydrapermazol:900mg")))
                .andExpect(jsonPath("$[0].medications[2]", is("pharmacol:5000mg")))
                .andExpect(jsonPath("$[0].medications[3]", is("terazine:500mg")))
                .andExpect(jsonPath("$[0].age", is(35)))

                .andExpect(jsonPath("$[1].allergies", hasSize(0)))
                .andExpect(jsonPath("$[1].firstName", is("Warren")))
                .andExpect(jsonPath("$[1].lastName", is("Zemicks")))
                .andExpect(jsonPath("$[1].stationNumber", is("2")))
                .andExpect(jsonPath("$[1].phone", is("841-874-7512")))
                .andExpect(jsonPath("$[1].medications", is(empty())))
                .andExpect(jsonPath("$[1].age", is(38)))

                .andExpect(jsonPath("$[2].allergies", hasSize(0)))
                .andExpect(jsonPath("$[2].firstName", is("Zach")))
                .andExpect(jsonPath("$[2].lastName", is("Zemicks")))
                .andExpect(jsonPath("$[2].stationNumber", is("2")))
                .andExpect(jsonPath("$[2].phone", is("841-874-7512")))
                .andExpect(jsonPath("$[2].medications", is(empty())))
                .andExpect(jsonPath("$[2].age", is(6)));
    }
    @Test
    public void testGetPhoneAlertByStation() throws Exception {
        mockMvc.perform(get("/phoneAlert?firestation=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", contains(
                        "841-874-6513",
                        "841-874-7878",
                        "841-874-7512",
                        "841-874-7512",
                        "841-874-7458"
                )));
    }
    @Test
    public void testGetChildByAddress() throws Exception {
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Tenley")))
                .andExpect(jsonPath("$[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$[0].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[0].age", is(11)))
                .andExpect(jsonPath("$[1].firstName", is("Roger")))
                .andExpect(jsonPath("$[1].lastName", is("Boyd")))
                .andExpect(jsonPath("$[1].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[1].age", is(6)))
                .andExpect(jsonPath("$[2].relatives[0].firstName", is("Felicia")))
                .andExpect(jsonPath("$[2].relatives[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$[2].relatives[0].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[2].relatives[0].city", is("Culver")))
                .andExpect(jsonPath("$[2].relatives[0].zip", is("97451")))
                .andExpect(jsonPath("$[2].relatives[0].phone", is("841-874-6544")))
                .andExpect(jsonPath("$[2].relatives[0].email", is("jaboyd@email.com")))
                .andExpect(jsonPath("$[2].relatives[0].age", is(37)));


    }
    @Test
    public void testGetASinglePerson() throws Exception {
        mockMvc.perform(get("/Person?id=0"))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is("John")))
                .andExpect(jsonPath("lastName", is("Boyd")))
                .andExpect(jsonPath("address", is("1509 Culver St")))
                .andExpect(jsonPath("city", is("Culver")))
                .andExpect(jsonPath("zip", is("97451")))
                .andExpect(jsonPath("phone", is("841-874-6512")))
                .andExpect(jsonPath("email", is("jaboyd@email.com")));
    }


    @Test
    public void testPostASinglePerson() throws Exception {
        mockMvc.perform(post("/Person")
                        .contentType("application/json")
                        .content("{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\": \"97451\",\"phone\": \"841-874-6512\",\"email\": \"jaboyd@email.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is("John")))
                .andExpect(jsonPath("lastName", is("Boyd")))
                .andExpect(jsonPath("address", is("1509 Culver St")))
                .andExpect(jsonPath("city", is("Culver")))
                .andExpect(jsonPath("zip", is("97451")))
                .andExpect(jsonPath("phone", is("841-874-6512")))
                .andExpect(jsonPath("email", is("jaboyd@email.com")));
    }

    @Test
    public void testPutASinglePerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/Person/0")
                        .contentType("application/json")
                        .content("{\"firstName\":\"John\",\"lastName\":\"Test\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\": \"97451\",\"phone\": \"841-874-6512\",\"email\": \"jaboyd@email.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is("John")))
                .andExpect(jsonPath("lastName", is("Test")))
                .andExpect(jsonPath("address", is("1509 Culver St")))
                .andExpect(jsonPath("city", is("Culver")))
                .andExpect(jsonPath("zip", is("97451")))
                .andExpect(jsonPath("phone", is("841-874-6512")))
                .andExpect(jsonPath("email", is("jaboyd@email.com")));

    }

    @Test
    public void testDeleteASinglePerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/Person?firstName=John&lastName=Boyd"))
                .andExpect(status().isOk());


    }
}

