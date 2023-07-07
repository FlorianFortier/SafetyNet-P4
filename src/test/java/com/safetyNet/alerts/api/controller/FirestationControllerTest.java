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
public class FirestationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetFirestation() throws Exception {
        mockMvc.perform(get("/firestation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[0].station", is("3")));

    }

}

