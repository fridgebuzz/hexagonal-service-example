package com.fridgebuzz.parking.domain;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RateTest
{
    private static final Rate TEST_RATE_1 =  new Rate(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                    OffsetTime.parse("2021-08-31T09:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    OffsetTime.parse("2021-08-31T18:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    ZoneId.of("America/Montreal"), 2000);

    private static final Rate TEST_RATE_2 =  new Rate(List.of(DayOfWeek.FRIDAY),
                    OffsetTime.parse("2021-08-31T09:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    OffsetTime.parse("2021-08-31T18:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    ZoneId.of("America/Montreal"), 2000);

    private static final Rate TEST_RATE_3 =  new Rate(Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                    OffsetTime.parse("2021-08-31T01:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    OffsetTime.parse("2021-08-31T06:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    ZoneId.of("America/Montreal"), 1000);

    private static final Rate TEST_RATE_4 =  new Rate(Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                    OffsetTime.parse("2021-08-31T07:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    OffsetTime.parse("2021-08-31T11:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    ZoneId.of("America/Montreal"), 1500);

    private static final Rate TEST_RATE_5 =  new Rate(Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                    OffsetTime.parse("2021-08-31T07:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    OffsetTime.parse("2021-08-31T11:00:00-04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    ZoneId.of("America/New_York"), 1500);

    @Test
    public void testEquals() {
        assertEquals(TEST_RATE_1, TEST_RATE_1);
        assertEquals(TEST_RATE_4, TEST_RATE_5);
        assertNotEquals(TEST_RATE_2, TEST_RATE_4);
        assertNotEquals(TEST_RATE_1, TEST_RATE_2);
        assertNotEquals(null, TEST_RATE_1);
    }

    @Test
    public void testHashCode() {
        assertEquals(TEST_RATE_4.hashCode(), TEST_RATE_5.hashCode());
        assertNotEquals(TEST_RATE_3.hashCode(), TEST_RATE_4.hashCode());
    }
}
