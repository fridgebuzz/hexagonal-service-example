package com.spothero.parking.application.message;

import com.spothero.parking.domain.Day;
import com.spothero.parking.domain.Rate;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Representation of a collection of parking rates.
 *
 * @see com.spothero.parking.application.rest.RatesController
 */
public class RatesMessage implements Serializable
{
    private static final long serialVersionUID = 1225456101694898786L;

    private List<RateMessage> rateMessages;

    public RatesMessage() {}

    public RatesMessage(List<RateMessage> rateMessages)
    {
        this.rateMessages = rateMessages;
    }

    public List<RateMessage> getRates()
    {
        return rateMessages;
    }

    public void setRates(List<RateMessage> rates)
    {
        this.rateMessages = rates;
    }

    public static class RateMessage implements Serializable
    {
        private static final long serialVersionUID = -53188881497292731L;

        private String days;
        private String times;
        private String tz;
        private int price;

        public RateMessage() {}

        public RateMessage(String days, String times, String tz, int price)
        {
            this.days = days;
            this.times = times;
            this.tz = tz;
            this.price = price;
        }

        public String getDays()
        {
            return days;
        }

        public void setDays(String days)
        {
            this.days = days;
        }

        public String getTimes()
        {
            return times;
        }

        public void setTimes(String times)
        {
            this.times = times;
        }

        public String getTz()
        {
            return tz;
        }

        public void setTz(String tz)
        {
            this.tz = tz;
        }

        public int getPrice()
        {
            return price;
        }

        public void setPrice(int price)
        {
            this.price = price;
        }

        public Rate toRate() {

            // turn day abbreviations "mon", "tues" etc. into a set of enums
            String[] dayStrings = getDays().split(",");
            List<DayOfWeek> days = new ArrayList<>();
            for (String dayString : dayStrings) {
                days.add(DayOfWeek.of(Day.valueOf(dayString).getValue()));
            }

            // convert times to UTC
            ZoneId zoneId =  ZoneId.of(getTz());

            String[] timeStrings = getTimes().split("-");
            if (timeStrings.length != 2) throw new IllegalArgumentException("Time range must be formatted as \'HHmm-HHmm\'");
            OffsetTime startTime = formattedTimeToOffsetTime(zoneId, timeStrings[0]);
            OffsetTime endTime = formattedTimeToOffsetTime(zoneId, timeStrings[1]);

            return new Rate(days, startTime, endTime, zoneId, getPrice());
        }

        public static RateMessage fromRate(Rate rate) {
            StringBuilder dayBuilder = new StringBuilder();
            boolean firstDay = true;
            for (DayOfWeek day: rate.getDays()) {
                if (firstDay) {
                    firstDay = false;
                }
                else {
                    dayBuilder.append(",");
                }
                dayBuilder.append(Day.of(day.getValue()));
            }

            ZoneId zoneId = rate.getZoneId();
            String start = rate.getStart().toLocalTime().format(formatter);
            String end = rate.getEnd().toLocalTime().format(formatter);
            String timeString = start + "-" + end;

            return new RateMessage(dayBuilder.toString(), timeString, zoneId.toString(), rate.getPrice());
        }

        private OffsetTime formattedTimeToOffsetTime(ZoneId zoneId, String time) {
            LocalTime localTime = LocalTime.parse(time, formatter);
            return localTime.atOffset(zoneId.getRules().getOffset(Instant.now()));
        }

        @Override
        public String toString()
        {
            return "Rate{" +
                    "days='" + days + '\'' +
                    ", times='" + times + '\'' +
                    ", tz='" + tz + '\'' +
                    ", price=" + price +
                    '}';
        }

        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
    }
}
