package com.spothero.parking.domain.service;

import com.spothero.parking.domain.Rate;
import com.spothero.parking.domain.repository.RateRepository;
import com.spothero.parking.infrastructure.repository.InMemoryRateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceServiceTest
{
    private static RateRepository rateRepository;

    private static PriceService priceService;

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

    @BeforeAll
    public static void setUp() {
        rateRepository = new InMemoryRateRepository();
        rateRepository.addRates(Arrays.asList(TEST_RATE_1, TEST_RATE_2, TEST_RATE_3, TEST_RATE_4, TEST_RATE_5));
        priceService = new PriceService(rateRepository);
    }

    @Test
    public void findMatchingPrice() throws PriceNotAvailableException
    {
        int price = priceService.getPrice(OffsetDateTime.parse("2021-08-29T08:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                OffsetDateTime.parse("2021-08-29T09:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        assertEquals(1500, price);
    }

    @Test
    public void requestSpanningDaysThrows()
    {
        Assertions.assertThrows(PriceNotAvailableException.class, () -> priceService.getPrice(
                OffsetDateTime.parse("2021-08-29T08:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                OffsetDateTime.parse("2021-08-30T09:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME)));
    }

    @Test
    public void requestWithMoreThanOneMatchThrows() {
        Assertions.assertThrows(PriceNotAvailableException.class, () -> priceService.getPrice(
                OffsetDateTime.parse("2021-08-29T03:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                OffsetDateTime.parse("2021-08-30T04:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME)));

    }
}
