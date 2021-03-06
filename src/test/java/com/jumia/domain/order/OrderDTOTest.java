package com.jumia.domain.order;

import com.jumia.domain.exception.ServiceException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author rafael.ferrari
 */
@ActiveProfiles("test")
public class OrderDTOTest {

    @Test
    public void shouldCreateValidOrderDto() {
        // GIVEN 
        final LocalDateTime initialDate = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0);

        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
        monthFilters.add(new MonthIntervalFilter(1, 6));

        // WHEN
        final OrderDTO orderDTO = new OrderDTO(initialDate, finalDate, monthFilters);

        // THEN
        assertThat(orderDTO.getInitialDate()).isEqualByComparingTo(initialDate);
        assertThat(orderDTO.getFinalDate()).isEqualByComparingTo(finalDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCreateOrderDtoWithoutListMonthFilters() throws ServiceException {
        // GIVEN 
        final LocalDateTime initialDate = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0);
        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();

        // WHEN
        new OrderDTO(initialDate, finalDate, monthFilters);

        // THEN 
        // Catch the IllegalArgumentException
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenCreateOrderDtoWithInvalidDateRange() throws ServiceException {
        // GIVEN 
        final LocalDateTime initialDate = LocalDateTime.of(2017, Month.JANUARY, 10, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0);

        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
        monthFilters.add(new MonthIntervalFilter(1, 6));

        // WHEN
        new OrderDTO(initialDate, finalDate, monthFilters);

        // THEN 
        // Catch the IllegalStateException
    }

}
