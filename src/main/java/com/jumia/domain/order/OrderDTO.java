package com.jumia.domain.order;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rafael.ferrari
 */
public class OrderDTO {

    private final LocalDateTime initialDate;
    private final LocalDateTime finalDate;
    private final List<MonthIntervalFilter> monthIntervalFilters;

    public OrderDTO(final LocalDateTime initialDate, final LocalDateTime finalDate, final List<MonthIntervalFilter> monthIntervalFilters) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.monthIntervalFilters = monthIntervalFilters;
        validateMonthIntervalFilters(monthIntervalFilters);
        validateRangeDate(initialDate, finalDate);
    }

    private void validateMonthIntervalFilters(final List<MonthIntervalFilter> monthIntervalFilters) {
        if (monthIntervalFilters.isEmpty()) {
            throw new IllegalStateException("List of Month Interval Filters is Empty.");
        }
    }

    private void validateRangeDate(final LocalDateTime initialDate, final LocalDateTime finalDate) {
        if (initialDate.toLocalDate().isAfter(finalDate.toLocalDate())) {
            throw new IllegalStateException("Invalid Range of Dates.");
        }
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public List<MonthIntervalFilter> getMonthIntervalFilters() {
        return monthIntervalFilters;
    }

}
