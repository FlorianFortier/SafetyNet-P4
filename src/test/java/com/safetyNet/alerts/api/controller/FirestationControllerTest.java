package com.safetyNet.alerts.api.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetFirestation() throws Exception {
        mockMvc.perform(get("/Firestations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[0].station", is("3")));

    }

    @Test
    public void testGetASingleFirestation() throws Exception {
        mockMvc.perform(get("/Firestation?id=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("1509 Culver St")))
                .andExpect(jsonPath("station", is("3")));
    }

    @Test
    public void testPostASinglePerson() throws Exception {
        mockMvc.perform(post("/Firestation")
                        .contentType("application/json")
                        .content("{\"address\":\"Test\",\"station\":\"3\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("Test")))
                .andExpect(jsonPath("station", is("3")));
    }

    @Test
    public void testPutASinglePerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/Firestation/0")
                        .contentType("application/json")
                        .content("{\"address\":\"Test\",\"station\":\"3\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("Test")))
                .andExpect(jsonPath("station", is("3")));

    }

    @Test
    public void testDeleteASinglePerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/Firestation?address=1509 Culver St&station=3"))
                .andExpect(status().isOk());


    }
}

