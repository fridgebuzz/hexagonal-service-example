package com.spothero.parking.application.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest
{
    // TODO these tests should really use the jsonPath matcher
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void validPrice() throws Exception
    {
        this.mockMvc.perform(get("/price")
                        .param("start", "2015-07-01T07:00:00-05:00")
                        .param("end", "2015-07-01T12:00:00-05:00"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.price").value("1750")) ;

    }

    @Test
    public void unavailablePrice() throws Exception{
        this.mockMvc.perform(get("/price")
                        .param("start", "2015-07-04T07:00:00+05:00")
                        .param("end", "2015-07-04T20:00:00+05:00"))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().
                        string(containsString("unavailable"))) ;
    }
}
