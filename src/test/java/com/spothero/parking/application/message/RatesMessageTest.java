package com.spothero.parking.application.message;

import com.spothero.parking.domain.Rate;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatesMessageTest
{

    public static final String AMERICA_CHICAGO = "America/Chicago";
    public static final String MON_TUES_THURS = "mon,tues,thurs";
    public static final String NINE_TO_TWENTY_ONE_HOURS = "0900-2100";
    public static final int FIFTEEN_DOLLARS = 1500;

    @Test
    public void rateMessageToRate() {
        RatesMessage.RateMessage rateMessage = new RatesMessage.RateMessage(MON_TUES_THURS, NINE_TO_TWENTY_ONE_HOURS,
                AMERICA_CHICAGO, FIFTEEN_DOLLARS);
        Rate rate = rateMessage.toRate();

        List<DayOfWeek> expectedDays = new ArrayList<>();
        expectedDays.add(DayOfWeek.MONDAY);
        expectedDays.add(DayOfWeek.TUESDAY);
        expectedDays.add(DayOfWeek.THURSDAY);
        assertEquals(expectedDays, rate.getDays());

        assertEquals(9, rate.getStart().getHour());
        assertEquals(0, rate.getStart().getMinute());

        assertEquals(ZoneId.of(AMERICA_CHICAGO), rate.getZoneId());

        assertEquals(1500, rate.getPrice());
    }

    @Test
    public void rateToRateMessage() {

        List<DayOfWeek> expectedDays = new ArrayList<>();
        expectedDays.add(DayOfWeek.MONDAY);
        expectedDays.add(DayOfWeek.TUESDAY);
        expectedDays.add(DayOfWeek.THURSDAY);

        OffsetTime start = OffsetTime.parse("09:00-05:00");
        OffsetTime end = OffsetTime.parse("21:00-05:00");

        Rate rate = new Rate(expectedDays, start, end, ZoneId.of(AMERICA_CHICAGO), 1500);
        RatesMessage.RateMessage rateMessage = RatesMessage.RateMessage.fromRate(rate);

        assertEquals(MON_TUES_THURS, rateMessage.getDays());
        assertEquals(NINE_TO_TWENTY_ONE_HOURS, rateMessage.getTimes());
        assertEquals(AMERICA_CHICAGO, rateMessage.getTz());
        assertEquals(FIFTEEN_DOLLARS, rateMessage.getPrice());
    }

}
