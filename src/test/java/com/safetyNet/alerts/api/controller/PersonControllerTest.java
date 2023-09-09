package com.safetyNet.alerts.api.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public  void testGetASinglePerson() throws Exception {
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

