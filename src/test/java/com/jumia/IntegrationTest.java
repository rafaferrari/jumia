package com.jumia;

import com.jumia.presentation.Executor;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;

/**
 * @author rafael.ferrari
 */
@SpringBootTest
@ActiveProfiles("test")
public class IntegrationTest {

    final TestLogger LOGGER = TestLoggerFactory.getTestLogger(Executor.class);

    @After
    public void clearLoggers() {
        TestLoggerFactory.clear();
    }

    @Test
    public void shouldReturnOrder() {
        // GIVEN
        final String[] arguments = new String[6];
        arguments[0] = "-initialDate";
        arguments[1] = "2016-01-01 00:00:00";
        arguments[2] = "-finalDate";
        arguments[3] = "2017-01-01 00:00:00";
        arguments[4] = "-monthSort";
        arguments[5] = "1-3";

        // WHEN
        Application.main(arguments);

        // THEN
        assertThat(LOGGER.getLoggingEvents().get(0).getMessage()).contains("JANUARY-MARCH months: 1 orders");
    }
    
    @Test
    public void shouldNotReturnOrder() {
        // GIVEN
        final String[] arguments = new String[6];
        arguments[0] = "-initialDate";
        arguments[1] = "2016-01-01 00:00:00";
        arguments[2] = "-finalDate";
        arguments[3] = "2017-01-01 00:00:00";
        arguments[4] = "-monthSort";
        arguments[5] = "11-12";

        // WHEN
        Application.main(arguments);

        // THEN
        assertThat(LOGGER.getLoggingEvents().get(0).getMessage()).contains("NOVEMBER-DECEMBER months: 0 orders");
    }

    @Test
    public void shouldExecuteHelpArgumentWithoutErrors() {
        // GIVEN
        final String[] arguments = new String[1];
        arguments[0] = "-help";

        // WHEN
        Application.main(arguments);

        // THEN
        // Execute without errors.
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenRunWithInvalidArguments() {
        // GIVEN
        final String[] arguments = new String[4];
        arguments[0] = "-initialDate";
        arguments[1] = "2016-01-01 00:00:00";
        arguments[2] = "-finalDate";
        arguments[3] = "2017-01-01 00:00:00";

        // WHEN
        Application.main(arguments);

        // THEN 
        // Catch the IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenRunWithInvalidMonthFilterArgument() {
        // GIVEN
        final String[] arguments = new String[6];
        arguments[0] = "-initialDate";
        arguments[1] = "2016-01-01 00:00:00";
        arguments[2] = "-finalDate";
        arguments[3] = "2017-01-01 00:00:00";
        arguments[4] = "-monthSort";
        arguments[5] = "1-x";

        // WHEN
        Application.main(arguments);

        // THEN 
        // Catch the IllegalArgumentException
    }

}
