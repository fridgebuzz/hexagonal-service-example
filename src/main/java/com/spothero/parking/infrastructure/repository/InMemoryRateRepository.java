
package com.spothero.parking.infrastructure.repository;

import com.spothero.parking.domain.Rate;
import com.spothero.parking.domain.repository.RateRepository;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.*;

@Component
public class InMemoryRateRepository implements RateRepository
{
    // store rates by day of the week and sort by start time
    private Map<DayOfWeek, SortedSet<Rate>> rates = new HashMap<>();

    public void clear() {
        rates.clear();
    }

    @Override
    public void addRates(List<Rate> rates)
    {
        for (Rate rate : rates) {
            addRate(rate);
        }
    }

    @Override
    public void addRate(Rate rate) {
        for (DayOfWeek day: rate.getDays()) {
            rates.putIfAbsent(day, new TreeSet<>(Comparator.comparing(Rate::getStart)));
            SortedSet<Rate> dayRates = rates.get(day);
            dayRates.add(rate);
        }
    }

    @Override
    public List<Rate> getAllRates() {
        Set<Rate> allRates = new HashSet<>();
        Collection<SortedSet<Rate>> rateValues = rates.values();
        for (Set<Rate> rateValue : rateValues) {
            allRates.addAll(rateValue);
        }
        return new ArrayList<>(allRates);
    }

    @Override
    public SortedSet<Rate> findByDayOfWeek(DayOfWeek dayOfWeek)
    {
        return rates.get(dayOfWeek);
    }
}
