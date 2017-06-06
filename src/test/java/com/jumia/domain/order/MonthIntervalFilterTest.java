package com.jumia.domain.order;

import java.time.DateTimeException;
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
public class MonthIntervalFilterTest {

    @Test
    public void test_should_create_valid_month_interval_filters() {
        // GIVEN 
        final int january = 1;
        final int march = 3;

        // WHEN
        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
        monthFilters.add(new MonthIntervalFilter(january, march));

        // THEN
        assertThat(monthFilters.size()).isEqualTo(1);
    }

    @Test(expected = DateTimeException.class)
    public void test_should_throw_exception_when_create_month_interval_filters_with_invalid_month() {
        // GIVEN 
        final int january = 0;
        final int march = 13;

        // WHEN
        new MonthIntervalFilter(january, march);

        // THEN
        // Catch the DateTimeException
    }

    @Test(expected = IllegalStateException.class)
    public void test_should_throw_exception_when_create_month_interval_filters_with_invalid_range_of_month() {
        // GIVEN 
        final int january = 3;
        final int march = 1;

        // WHEN
        new MonthIntervalFilter(january, march);

        // THEN
        // Catch the IllegalStateException
    }

}
