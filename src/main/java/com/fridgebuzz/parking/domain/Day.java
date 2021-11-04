package com.fridgebuzz.parking.domain;

/**
 * An enum to support the use of "mon", "tues", "wed" etc. in the rate requests and responses.
 *
 * A Day in String form can be converted to {@link java.time.DayOfWeek} using:
 *
 * <code>
 *     DayOfWeek.of(Day.valueOf(dayString).getValue())
 * </code>
 *
 * A DayOfWeek enum can be converted to a Day String form using:
 *
 * <code>
 *     Day.of(dayOfWeek.getValue())
 * </code>
 */
public enum Day
{
    mon,
    tues,
    wed,
    thurs,
    fri,
    sat,
    sun;

    /**
     * @return an integer for the day of the week, where "mon" is 1 and "sun" is 7.
     */
    public int getValue() {
        return ordinal() + 1;
    }

    /**
     * @param dayOfWeek an integer from 1 to 7
     * @return the Day corresponding to the given value where Day.mon is 1 and Day.sun is 7
     */
    public static Day of(int dayOfWeek) {
        if (dayOfWeek < 1 || dayOfWeek > 7) {
            throw new IllegalArgumentException("Invalid value for Day: " + dayOfWeek);
        }
        return Day.values()[dayOfWeek - 1];
    }
}
