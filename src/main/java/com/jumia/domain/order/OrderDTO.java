package com.jumia.domain.order;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rafael.ferrari
 */
public class OrderDTO {

    private final LocalDateTime initialDate;
    private final LocalDateTime finalDate;
    private final List<MonthFilterDTO> monthFiltersDTO;

    private OrderDTO(final OrderDTOBuilder builder) {
        this.initialDate = builder.initialDate;
        this.finalDate = builder.finalDate;
        this.monthFiltersDTO = builder.monthFiltersDTO;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public List<MonthFilterDTO> getMonthFiltersDTO() {
        return monthFiltersDTO;
    }

    public static class OrderDTOBuilder {

        private LocalDateTime initialDate;
        private LocalDateTime finalDate;
        private final List<MonthFilterDTO> monthFiltersDTO;

        public OrderDTOBuilder(final LocalDateTime initialDate, final LocalDateTime finalDate, final List<MonthFilterDTO> monthFiltersDTO) {
            this.initialDate = initialDate;
            this.finalDate = finalDate;
            this.monthFiltersDTO = monthFiltersDTO;
        }

        public OrderDTO build() {
            validateMonthOrderDtos(monthFiltersDTO);
            validateRangeDate(initialDate, finalDate);
            return new OrderDTO(this);
        }

        private void validateMonthOrderDtos(final List<MonthFilterDTO> monthOrderDtos) {
            if (monthOrderDtos.isEmpty()) {
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
