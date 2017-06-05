package com.jumia.domain.order;

import com.jumia.domain.exception.ServiceException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author rafael.ferrari
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class OrderDTOTest {

    @Test
    public void test_should_create_valid_orderdto() {
        // GIVEN 
        final LocalDateTime initialDate = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0);

        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
        monthFilters.add(new MonthIntervalFilter.MonthIntervalFilterBuilder(1, 6).build());

        // WHEN
        final OrderDTO orderDTO = new OrderDTO.OrderDTOBuilder(initialDate, finalDate, monthFilters).build();

        // THEN
        assertThat(orderDTO.getInitialDate()).isEqualByComparingTo(initialDate);
        assertThat(orderDTO.getFinalDate()).isEqualByComparingTo(finalDate);
    }

    @Test(expected = IllegalStateException.class)
    public void test_should_throw_exception_when_create_orderdto_without_list_month_filters() throws ServiceException {
        // GIVEN 
        final LocalDateTime initialDate = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0);
        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();

        // WHEN
        new OrderDTO.OrderDTOBuilder(initialDate, finalDate, monthFilters).build();

        // THEN 
        // Catch the ServiceException
    }

    @Test(expected = IllegalStateException.class)
    public void test_should_throw_exception_when_create_orderdto_with_invalid_date_range() throws ServiceException {
        // GIVEN 
        final LocalDateTime initialDate = LocalDateTime.of(2017, Month.JANUARY, 10, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0);

        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
        monthFilters.add(new MonthIntervalFilter.MonthIntervalFilterBuilder(1, 6).build());

        // WHEN
        new OrderDTO.OrderDTOBuilder(initialDate, finalDate, monthFilters).build();

        // THEN 
        // Catch the ServiceException
    }

}
