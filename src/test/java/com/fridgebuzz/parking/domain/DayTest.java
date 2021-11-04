package com.fridgebuzz.parking.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayTest
{
    @Test
    public void invalidDayOfWeekThrows()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Day.of(8));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Day.of(0));
    }

    @Test
    public void valuesEqualDayOfWeek() {
        assertEquals(DayOfWeek.MONDAY.getValue(), Day.mon.getValue());
        assertEquals(DayOfWeek.TUESDAY.getValue(), Day.tues.getValue());
        assertEquals(DayOfWeek.WEDNESDAY.getValue(), Day.wed.getValue());
        assertEquals(DayOfWeek.THURSDAY.getValue(), Day.thurs.getValue());
        assertEquals(DayOfWeek.FRIDAY.getValue(), Day.fri.getValue());
        assertEquals(DayOfWeek.SATURDAY.getValue(), Day.sat.getValue());
        assertEquals(DayOfWeek.SUNDAY.getValue(), Day.sun.getValue());

    }
}
