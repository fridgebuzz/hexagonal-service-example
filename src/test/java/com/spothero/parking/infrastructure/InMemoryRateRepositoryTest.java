package com.spothero.parking.infrastructure;

import com.spothero.parking.domain.Rate;
import com.spothero.parking.domain.repository.RateRepository;
import com.spothero.parking.infrastructure.repository.InMemoryRateRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryRateRepositoryTest
{
    private static RateRepository rateRepository;

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



    @BeforeAll
    public static void setUp()
    {
        rateRepository = new InMemoryRateRepository();
    }

    @Test
    public void addRates() {
        rateRepository.addRates(Arrays.asList(TEST_RATE_1, TEST_RATE_2));
        rateRepository.addRate(TEST_RATE_3);

        List<Rate> rates = rateRepository.getAllRates();
        assertTrue(rates.containsAll(Arrays.asList(TEST_RATE_1, TEST_RATE_2, TEST_RATE_3)));
    }

    @Test
    public void clear() {
        rateRepository.addRates(Arrays.asList(TEST_RATE_1, TEST_RATE_2));
        rateRepository.clear();

        List<Rate> rates = rateRepository.getAllRates();
        assertTrue(rates.isEmpty());
    }

    @Test
    public void findByDayOfWeek() {
        rateRepository.addRates(Arrays.asList(TEST_RATE_1, TEST_RATE_2, TEST_RATE_3, TEST_RATE_4));

        SortedSet<Rate> rates = rateRepository.findByDayOfWeek(DayOfWeek.SUNDAY);

        assertEquals(2, rates.size());
        // sorted in ascending order of start time
        assertEquals(TEST_RATE_3, rates.first());
        assertEquals(TEST_RATE_4, rates.last());
    }
}
