package com.jumia.domain.order;

import java.time.DateTimeException;
import java.time.LocalDateTime;

/**
 * @author rafael.ferrari
 */
public class MonthFilterDTO {

    private final LocalDateTime initialOrderDate;
    private final LocalDateTime initialMonthDate;
    private final LocalDateTime finalMonthDate;
    private final int initialMonth;
    private final int finalMonth;

    private MonthFilterDTO(final MonthFilterDTOBuilder builder) {
        this.initialOrderDate = builder.initialOrderDate;
        this.initialMonthDate = builder.initialMonthDate;
        this.finalMonthDate = builder.finalMonthDate;
        this.initialMonth = builder.initialMonth;
        this.finalMonth = builder.finalMonth;
    }

    public LocalDateTime getInitialMonthDate() {
        return initialMonthDate;
    }

    public LocalDateTime getFinalMonthDate() {
        return finalMonthDate;
    }

    public static class MonthFilterDTOBuilder {

        private final LocalDateTime initialOrderDate;
        private LocalDateTime initialMonthDate;
        private LocalDateTime finalMonthDate;
        private final int initialMonth;
        private final int finalMonth;

        public MonthFilterDTOBuilder(final LocalDateTime initialOrderDate, final int initialMonth, final int finalMonth) {
            this.initialOrderDate = initialOrderDate;
            this.initialMonth = isValidMonth(initialMonth);
            this.finalMonth = isValidMonth(finalMonth);
        }

        private int isValidMonth(final int month) {
            if (month < 1 || month > 12) {
                throw new DateTimeException("Invalid value for MonthOfYear: " + month);
            }
            return month;
        }

        public MonthFilterDTO build() {
            isValidMonthInterval(initialMonth, finalMonth);
            this.initialMonthDate = initialOrderDate.withMonth(initialMonth);
            this.finalMonthDate = initialOrderDate.withMonth(finalMonth);
            return new MonthFilterDTO(this);
        }

        private void isValidMonthInterval(final int initialMonth, final int finalMonth) {
            if (initialMonth >= finalMonth) {
                throw new IllegalStateException("Invalid Month Intervals.");
            }
        }

    }

}
