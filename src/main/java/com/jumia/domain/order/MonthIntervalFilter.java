package com.jumia.domain.order;

import java.time.DateTimeException;
import java.time.Month;

/**
 * @author rafael.ferrari
 */
public class MonthIntervalFilter {

    private final Month initialMonthFilter;
    private final Month finalMonthFilter;
    private final int initialMonth;
    private final int finalMonth;

    private MonthIntervalFilter(final MonthIntervalFilterBuilder builder) {
        this.initialMonthFilter = builder.initialMonthFilter;
        this.finalMonthFilter = builder.finalMonthFilter;
        this.initialMonth = builder.initialMonth;
        this.finalMonth = builder.finalMonth;
    }

    public Month getInitialMonthFilter() {
        return initialMonthFilter;
    }

    public Month getFinalMonthFilter() {
        return finalMonthFilter;
    }

    public static class MonthIntervalFilterBuilder {

        private Month initialMonthFilter;
        private Month finalMonthFilter;
        private final int initialMonth;
        private final int finalMonth;

        public MonthIntervalFilterBuilder(final int initialMonth, final int finalMonth) {
            this.initialMonth = isValidMonth(initialMonth);
            this.finalMonth = isValidMonth(finalMonth);
        }

        private int isValidMonth(final int month) {
            if (month < 1 || month > 12) {
                throw new DateTimeException("Invalid value for MonthOfYear: " + month);
            }
            return month;
        }

        public MonthIntervalFilter build() {
            isValidMonthInterval(initialMonth, finalMonth);

            initialMonthFilter = Month.of(initialMonth);
            finalMonthFilter = Month.of(finalMonth);

            return new MonthIntervalFilter(this);
        }

        private void isValidMonthInterval(final int initialMonth, final int finalMonth) {
            if (initialMonth >= finalMonth) {
                throw new IllegalStateException("Invalid Month Intervals.");
            }
        }

    }

}
