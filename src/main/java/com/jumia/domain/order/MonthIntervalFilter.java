package com.jumia.domain.order;

import com.google.common.base.Preconditions;
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
        Preconditions.checkState(initialMonthFilter.compareTo(finalMonthFilter) < 0, "Invalid Month Intervals.");
    }

    public Month getInitialMonthFilter() {
        return initialMonthFilter;
    }

    public Month getFinalMonthFilter() {
        return finalMonthFilter;
    }

}
