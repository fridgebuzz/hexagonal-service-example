package com.spothero.parking.application.rest;

import com.spothero.parking.application.message.PriceMessage;
import com.spothero.parking.domain.service.PriceNotAvailableException;
import com.spothero.parking.domain.service.PriceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/price")
public class PriceController
{
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping  (produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody PriceMessage getPrice(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end) throws PriceNotAvailableException
    {
        final int price = priceService.getPrice(start, end);
        return new PriceMessage(price);
    }

    @ExceptionHandler (PriceNotAvailableException.class)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String handleException(Exception exception) {
        return "unavailable";
    }

}
