package com.jumia.domain.order;

import com.google.common.base.Preconditions;
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
        Preconditions.checkArgument(!monthIntervalFilters.isEmpty(), "List of Month Interval Filters is Empty.");
    }

    private void validateRangeDate(final LocalDateTime initialDate, final LocalDateTime finalDate) {
        Preconditions.checkState(initialDate.toLocalDate().isBefore(finalDate.toLocalDate()), "Invalid Range of Dates.");
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
