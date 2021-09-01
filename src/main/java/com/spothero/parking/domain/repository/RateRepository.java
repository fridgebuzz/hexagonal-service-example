package com.spothero.parking.domain.repository;

import com.spothero.parking.domain.Rate;

import java.time.DayOfWeek;
import java.util.List;
import java.util.SortedSet;

/**
 * Repository interface for rates
 */
public interface RateRepository
{
    /**
     * Delete all existing rates
     */
    void clear();

    /**
     * Add a rate
     *
     * @param rate rate to add
     */
    void addRate(Rate rate);

    /**
     * Add multiple rates
     *
     * @param rates list of rates to add
     */
    void addRates(List<Rate> rates);

    /**
     * @return all current rates
     */
    List<Rate> getAllRates();

    /**
     * Find rates for a given day of the week (e.g. WEDNESDAY)
     * @param dayOfWeek the day to find rates for
     * @return a set of rates sorted in ascending order by start time
     */
    SortedSet<Rate> findByDayOfWeek(DayOfWeek dayOfWeek);
}
