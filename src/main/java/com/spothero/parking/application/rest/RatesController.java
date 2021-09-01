package com.spothero.parking.application.rest;

import com.spothero.parking.application.message.RatesMessage;
import com.spothero.parking.application.service.RateService;
import com.spothero.parking.domain.Rate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that responds to requests for rates resources.
 */
@RestController
@RequestMapping("/rates")
@Tag(name = "Rates API")
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
    @Operation(summary = "Replace all current parking rates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rates updated successfully")
    })
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
    @Operation(summary = "Get all current parking rates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All rates returned",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = RatesMessage.class))
        })
    })
    @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RatesMessage getRates()
    {
        List<Rate> rates = rateService.getRates();
        List<RatesMessage.RateMessage> rateMessages = new ArrayList<>();
        for (Rate rate: rates) {
            rateMessages.add(RatesMessage.RateMessage.fromRate(rate));
        }
        return new RatesMessage(rateMessages);
    }

}
