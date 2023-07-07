package com.safetyNet.alerts.api.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetMedicalRecord() throws Exception {
        mockMvc.perform(get("/MedicalRecord"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$[0].birthdate", is("03/06/1984")))
                .andExpect(jsonPath("$[0].medications[0]", is("aznol:350mg")))
                .andExpect(jsonPath("$[0].medications[1]", is("hydrapermazol:100mg")))
                .andExpect(jsonPath("$[0].allergies[0]", is("nillacilan")));
    }

}

