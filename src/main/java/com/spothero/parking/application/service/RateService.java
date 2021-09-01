package com.spothero.parking.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spothero.parking.application.message.RatesMessage;
import com.spothero.parking.domain.Rate;
import com.spothero.parking.domain.repository.RateRepository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Application service for handling rate queries and manipulation.
 */
public class RateService
{
    public static final String PARKING_RATE_LOCATION = "parking.rate.location";

    private final RateRepository rateRepository;

    private final String ratesPathName = System.getProperty(PARKING_RATE_LOCATION, "src/main/resources/rates.json");

    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @PostConstruct
    public void loadInitialRates() throws IOException
    {
        // load initial rates
        ObjectMapper objectMapper = new ObjectMapper();
        RatesMessage ratesMessage = objectMapper.readValue(new File(ratesPathName), RatesMessage.class);
        List<Rate> rates = new ArrayList<>();
        for (RatesMessage.RateMessage rateMessage : ratesMessage.getRates()) {
            rates.add(rateMessage.toRate());
        }
        updateRates(rates);

    }
    /**
     * Replaces existing rates with the given ones.
     *
     * @param rates new rates
     */
    public void updateRates(List<Rate> rates) {
        rateRepository.clear();
        rateRepository.addRates(rates);

    }

    /**
     * @return all existing rates
     */
    public List<Rate> getRates() {
        return rateRepository.getAllRates();
    }
}
