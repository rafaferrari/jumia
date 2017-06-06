package com.jumia.domain.order;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author rafael.ferrari
 */
@ActiveProfiles("test")
public class MonthIntervalFilterTest {

    @Test
    public void shouldCreateValidMonthIntervalFilters() {
        // GIVEN 
        final int resultExpected = 1;
        final int january = 1;
        final int march = 3;

        // WHEN
        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
        monthFilters.add(new MonthIntervalFilter(january, march));

        // THEN
        assertThat(monthFilters.size()).isEqualTo(resultExpected);
    }

    @Test(expected = DateTimeException.class)
    public void shouldThrowExceptionWhenCreateMonthIntervalFiltersWithInvalidMonth() {
        // GIVEN 
        final int january = 0;
        final int march = 13;

        // WHEN
        new MonthIntervalFilter(january, march);

        // THEN
        // Catch the DateTimeException
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenCreateMonthIntervalFiltersWithInvalidRangeOfMonth() {
        // GIVEN 
        final int january = 3;
        final int march = 1;

        // WHEN
        new MonthIntervalFilter(january, march);

        // THEN
        // Catch the IllegalStateException
    }

}
