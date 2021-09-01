package com.spothero.parking.application.rest;

import com.spothero.parking.application.message.RatesMessage;
import com.spothero.parking.application.service.RateService;
import com.spothero.parking.domain.Rate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller that responds to requests for rates resources.
 */
@RestController
@RequestMapping("/rates")
public class RatesController
{
    private final RateService rateService;

    public RatesController(RateService rateService) {
        this.rateService = rateService;
    }

    /**
     * Updates stored rates by replacing them with the given rates. Since the application always begins with an
     * initial set of rates, the successful return value is always {@link HttpStatus#OK}. Rates are accepted
     * as JSON.
     *
     * See src/main/resources/rates.json for an example request body
     *
     * @param ratesMessage a JSON representation of a collection of rates
     */
    @PutMapping (consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateRates(@RequestBody RatesMessage ratesMessage) {
        List<Rate> rates = new ArrayList<>();
        for (RatesMessage.RateMessage rateMessage : ratesMessage.getRates()) {
            rates.add(rateMessage.toRate());
        }
        rateService.updateRates(rates);
    }

    /**
     * @return the current collection of parking rates as JSON.
     *
     * See src/main/resources/rates.json for an example response message
     */
    @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody RatesMessage getRates()
    {
        List<Rate> rates = rateService.getRates();
        List<RatesMessage.RateMessage> rateMessages = new ArrayList<>();
        for (Rate rate: rates) {
            rateMessages.add(RatesMessage.RateMessage.fromRate(rate));
        }
        return new RatesMessage(rateMessages);
    }

    // TODO
    @ExceptionHandler (IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception exception) {
        
    }


}
