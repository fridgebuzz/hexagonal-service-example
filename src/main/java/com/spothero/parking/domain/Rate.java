package com.spothero.parking.domain;

import java.time.DayOfWeek;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.util.List;

public class Rate
{
    private List<DayOfWeek> days;
    private OffsetTime start;
    private OffsetTime end;
    private ZoneId zoneId;
    private int price;


    public Rate(List<DayOfWeek> days, OffsetTime start, OffsetTime end, ZoneId zoneId, int price)
    {
        this.days = days;
        this.start = start;
        this.end = end;
        this.zoneId = zoneId;
        this.price = price;
    }

    public List<DayOfWeek> getDays()
    {
        return days;
    }

    public OffsetTime getStart()
    {
        return start;
    }

    public OffsetTime getEnd()
    {
        return end;
    }

    public int getPrice()
    {
        return price;
    }

    public ZoneId getZoneId()
    {
        return zoneId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Rate rate = (Rate) o;

        if (price != rate.price)
        {
            return false;
        }
        if (!days.equals(rate.days))
        {
            return false;
        }
        if (!start.equals(rate.start))
        {
            return false;
        }
        return end.equals(rate.end);
    }

    @Override
    public int hashCode()
    {
        int result = days.hashCode();
        result = 31 * result + start.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + price;
        return result;
    }

    @Override
    public String toString()
    {
        return "Rate{" +
                "days=" + days +
                ", start=" + start +
                ", end=" + end +
                ", price=" + price +
                ", originalTimeZone=" + zoneId +
                '}';
    }
}
