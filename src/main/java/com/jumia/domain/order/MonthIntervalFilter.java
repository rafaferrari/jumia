package com.jumia.domain.order;

import java.time.Month;

/**
 * @author rafael.ferrari
 */
public class MonthIntervalFilter {

    private final Month initialMonthFilter;
    private final Month finalMonthFilter;

    public MonthIntervalFilter(final int initialMonth, final int finalMonth) {
        initialMonthFilter = Month.of(initialMonth);
        finalMonthFilter = Month.of(finalMonth);
        isValidMonthInterval();
    }

    private void isValidMonthInterval() {
        if (initialMonthFilter.compareTo(finalMonthFilter) > 0) {
            throw new IllegalStateException("Invalid Month Intervals.");
        }
    }

    public Month getInitialMonthFilter() {
        return initialMonthFilter;
    }

    public Month getFinalMonthFilter() {
        return finalMonthFilter;
    }

}
