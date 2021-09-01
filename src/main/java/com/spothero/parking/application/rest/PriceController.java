package com.spothero.parking.application.rest;

import com.spothero.parking.application.message.PriceMessage;
import com.spothero.parking.domain.service.PriceNotAvailableException;
import com.spothero.parking.domain.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/price")
@Tag(name = "Price API")
public class PriceController
{
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    /**
     * Get a parking price given a start and end offset date time in ISO-8601 format
     *
     * @param start start time with offset
     * @param end end time with offset
     * @return a JSON representation of a price if found
     * @throws PriceNotAvailableException if no valid price is found
     */
    @Operation(summary = "Get a parking price for a date/time range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found a valid price",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PriceMessage.class))
        }) ,
        @ApiResponse(responseCode = "404", description = "unavailable",
                    content = @Content)
    })
    @GetMapping  (produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody PriceMessage getPrice(@Parameter(description = "start date time to search for", required = true)
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
                                               @Parameter(description = "end date time to search for", required = true)
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end)
            throws PriceNotAvailableException
    {
        final int price = priceService.getPrice(start, end);
        return new PriceMessage(price);
    }

    /**
     * Handle an unavailable price
     * @return the string message "unavailable"
     */
    @ExceptionHandler (PriceNotAvailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleException() {
        return "unavailable";
    }

}
