package com.spothero.parking.application.service;

import com.spothero.parking.domain.Rate;
import com.spothero.parking.infrastructure.repository.InMemoryRateRepository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RateServiceTest
{
    private final static RateService rateService = new RateService(new InMemoryRateRepository());

    private static final Rate TEST_RATE_1 =  new Rate(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                    OffsetTime.parse("2021-08-31T09:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    OffsetTime.parse("2021-08-31T18:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    ZoneId.of("America/Montreal"), 2000);

    private static final Rate TEST_RATE_2 =  new Rate(List.of(DayOfWeek.FRIDAY),
                    OffsetTime.parse("2021-08-31T09:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    OffsetTime.parse("2021-08-31T18:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    ZoneId.of("America/Montreal"), 2500);

    private static final Rate TEST_RATE_3 =  new Rate(Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                    OffsetTime.parse("2021-08-31T01:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    OffsetTime.parse("2021-08-31T06:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    ZoneId.of("America/Montreal"), 1000);

    private static final Rate TEST_RATE_4 =  new Rate(Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                    OffsetTime.parse("2021-08-31T07:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    OffsetTime.parse("2021-08-31T11:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    ZoneId.of("America/Montreal"), 1500);

    private static final Rate TEST_RATE_5 =  new Rate(Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                    OffsetTime.parse("2021-08-31T02:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    OffsetTime.parse("2021-08-31T08:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    ZoneId.of("America/Chicago"), 1500);

    @Test
    public void loadInitialRatesFromResources() throws IOException
    {
        rateService.loadInitialRates();
        assertFalse(rateService.getRates().isEmpty());
    }

    @Test
    public void loadInitialRatesFromSetProperty() throws IOException {
        System.setProperty(RateService.PARKING_RATE_LOCATION, "src/test/resources/updateRates.json");
        rateService.loadInitialRates();
        assertFalse(rateService.getRates().isEmpty());
    }

    @Test
    public void updateRates() throws IOException {
        System.clearProperty(RateService.PARKING_RATE_LOCATION);
        rateService.loadInitialRates(); // will load from default location src/main/resources/rates.json
        rateService.updateRates(Arrays.asList(TEST_RATE_1, TEST_RATE_2, TEST_RATE_3, TEST_RATE_4, TEST_RATE_5));
        assertEquals(5, rateService.getRates().size());
    }
}
