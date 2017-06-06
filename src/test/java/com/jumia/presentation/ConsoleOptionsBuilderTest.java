package com.jumia.presentation;

import com.google.gson.JsonObject;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author rafael.ferrari
 */
@SpringBootTest
@ActiveProfiles("test")
public class ConsoleOptionsBuilderTest {

    @Test
    public void shouldCreateConsoleOptions() {
        // GIVEN
        final String[] arguments = new String[6];
        arguments[0] = "-initialDate";
        arguments[1] = "2016-01-01 00:00:00";
        arguments[2] = "-finalDate";
        arguments[3] = "2017-01-01 00:00:00";
        arguments[4] = "-monthSort";
        arguments[5] = "1-3";

        // WHEN
        final Optional<JsonObject> parameters = new ConsoleOptionsBuilder(arguments)
                .addOptions()
                .validateArguments()
                .create();

        // THEN
        assertThat(parameters.isPresent()).isEqualTo(true);
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
        new ConsoleOptionsBuilder(arguments)
                .addOptions()
                .validateArguments()
                .create();

        // THEN 
        // Catch the IllegalArgumentException
    }

    @Test
    public void shouldExecuteHelpArgumentWithoutErrors() {
        // GIVEN
        final String[] arguments = new String[1];
        arguments[0] = "-help";

        // WHEN
        final Optional<JsonObject> parameters = new ConsoleOptionsBuilder(arguments)
                .addOptions()
                .validateArguments()
                .create();

        // THEN
        // Execute without errors.
    }

}
