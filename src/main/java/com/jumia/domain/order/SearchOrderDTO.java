package com.jumia.domain.order;

import java.time.LocalDateTime;

/**
 * @author rafael.ferrari
 */
public class SearchOrderDTO {

    private LocalDateTime initialDate;
    private LocalDateTime finalDate;

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

}
