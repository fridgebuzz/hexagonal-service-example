package com.fridgebuzz.parking.domain.service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class PriceNotAvailableException extends Exception
{
    private static final long serialVersionUID = 904080600290036027L;

    public PriceNotAvailableException(String message)
    {
        super(message);
    }

    public PriceNotAvailableException(OffsetDateTime start, OffsetDateTime end) {
        this("No price available for time range: " + start.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) + " - " + end.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }
}
