package com.fridgebuzz.parking.domain.service;

import com.fridgebuzz.parking.domain.Rate;
import com.fridgebuzz.parking.domain.repository.RateRepository;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.SortedSet;

public class PriceService
{
    private final RateRepository rateRepository;

    public PriceService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public int getPrice(OffsetDateTime start, OffsetDateTime end) throws PriceNotAvailableException
    {
        if (start.getDayOfYear() != end.getDayOfYear())
        {
            // input spans more than one day
            throw new PriceNotAvailableException(start, end);
        }

        OffsetTime startTime = OffsetTime.from(start);
        OffsetTime endTime = OffsetTime.from(end);
        SortedSet<Rate> ratesForDay = rateRepository.findByDayOfWeek(start.getDayOfWeek()); // rates are sorted by start time
        Rate result = null;
        for (Rate rate: ratesForDay) {
            if (rate.getStart().isAfter(startTime)) {  // no need to search further
                break;
            }
            if (rateEncapsulatesTimeRange(rate, startTime, endTime)) {
                if (result != null) {
                    // more than one rate found
                    throw new PriceNotAvailableException(start, end);
                }
                else {
                    result = rate;
                }
            }
        }
        if (result == null) {
            // no rate found
            throw new PriceNotAvailableException(start, end);
        }
        return result.getPrice();
    }

    private boolean rateEncapsulatesTimeRange(Rate rate, OffsetTime start, OffsetTime end) {
        OffsetTime rateStart = rate.getStart();
        OffsetTime rateEnd = rate.getEnd();
        return ((rateStart.isBefore(start) || rateStart.isEqual(start)) && (rateEnd.isAfter(end) || rateEnd.isEqual(end)));
    }
}
