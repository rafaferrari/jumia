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

    private OrderDTO(final OrderDTOBuilder builder) {
        this.initialDate = builder.initialDate;
        this.finalDate = builder.finalDate;
        this.monthIntervalFilters = builder.monthIntervalFilters;
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

    public static class OrderDTOBuilder {

        private LocalDateTime initialDate;
        private LocalDateTime finalDate;
        private final List<MonthIntervalFilter> monthIntervalFilters;

        public OrderDTOBuilder(final LocalDateTime initialDate, final LocalDateTime finalDate, final List<MonthIntervalFilter> monthIntervalFilters) {
            this.initialDate = initialDate;
            this.finalDate = finalDate;
            this.monthIntervalFilters = monthIntervalFilters;
        }

        public OrderDTO build() {
            validateMonthIntervalFilters(monthIntervalFilters);
            validateRangeDate(initialDate, finalDate);
            return new OrderDTO(this);
        }

        private void validateMonthIntervalFilters(final List<MonthIntervalFilter> monthIntervalFilters) {
            if (monthIntervalFilters.isEmpty()) {
                throw new IllegalStateException("List of Month Filters is Empty.");
            }
        }

        private void validateRangeDate(final LocalDateTime initialDate, final LocalDateTime finalDate) {
            if (initialDate.toLocalDate().isAfter(finalDate.toLocalDate())) {
                throw new IllegalStateException("Invalid Range of Dates.");
            }
        }

    }

}
