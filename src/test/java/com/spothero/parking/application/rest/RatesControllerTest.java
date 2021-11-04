package com.fridgebuzz.parking.application.rest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RatesControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    private static String updatedRates;
    private static String initialRates;

    @BeforeAll
    public static void readInitialAndUpdatedRates() throws IOException
    {
        Path fileName = Path.of("src/test/resources/updatedRates.json");
        updatedRates = Files.readString(fileName);
        fileName = Path.of("src/main/resources/rates.json");
        initialRates = Files.readString(fileName);
    }

    @Test
    public void returnsInitialRates() throws Exception
    {
        // this was difficult to match with jsonPath b/c they don't come back in a given order.
        this.mockMvc.perform(get("/rates")).andDo(print()).andExpect(status().isOk())
        				.andExpect(content().
                                string(containsString("{\"days\":\"mon,wed,sat\",\"times\":\"0100-0500\"," +
                                        "\"tz\":\"America/Chicago\",\"price\":1000}")))
                        .andExpect(content().
                                string(containsString("{\"days\":\"wed\",\"times\":\"0600-1800\"," +
                                "\"tz\":\"America/Chicago\",\"price\":1750}")))
                        .andExpect(content().
                                string(containsString("{\"days\":\"sun,tues\",\"times\":\"0100-0700\"," +
                                "\"tz\":\"America/Chicago\",\"price\":925}")))
                        .andExpect(content().
                                string(containsString("{\"days\":\"mon,tues,thurs\"," +
                                "\"times\":\"0900-2100\",\"tz\":\"America/Chicago\",\"price\":1500}")))
                        .andExpect(content().
                                string(containsString("{\"days\":\"fri,sat,sun\"," +
                                "\"times\":\"0900-2100\",\"tz\":\"America/Chicago\",\"price\":2000}")));
    }

    @Test
    public void updateRates() throws Exception{
        this.mockMvc.perform(put("/rates").content(updatedRates).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/rates")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"days\":\"mon,tues,thurs\",\"times\":\"0900-2200\"," +
                        "\"tz\":\"America/New_York\",\"price\":1500}")));

        // restore initial rates because tests are not always run in order
        this.mockMvc.perform(put("/rates").content(initialRates).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
