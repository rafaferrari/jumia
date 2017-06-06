package com.jumia.presentation;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.jumia.domain.order.MonthIntervalFilter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Component Responsible For Process Console Parameters.
 *
 * @author rafael.ferrari
 */
public class ConsoleOptionsProcessor {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
    private final String[] arguments;
    private Optional<JsonObject> parameters;
    private Optional<LocalDateTime> initialDate;
    private Optional<LocalDateTime> finalDate;

    public ConsoleOptionsProcessor(final String... arguments) {
        this.arguments = arguments;
    }

    public void process() {
        this.parameters = createConsoleOptions(arguments);
        this.parameters.ifPresent(p -> {
            populateInitialDate(p.get("initialDate").getAsString());
            populateFinalDate(p.get("finalDate").getAsString());
            populateMonthFilters(p.get("monthSort").getAsString());
        });
    }

    private Optional<JsonObject> createConsoleOptions(final String... arguments) {
        return new ConsoleOptionsBuilder(arguments)
                .addOptions()
                .validateArguments()
                .create();
    }

    private void populateInitialDate(final String initialDate) {
        this.initialDate = Optional.of(LocalDateTime.parse(initialDate, DATE_TIME_FORMATTER));
    }

    private void populateFinalDate(final String finalDate) {
        this.finalDate = Optional.of(LocalDateTime.parse(finalDate, DATE_TIME_FORMATTER));
    }

    private void populateMonthFilters(final String monthSort) {
        final String[] filters = monthSort.replaceAll("\\s+", "").split(",");
        for (final String monthFilter : filters) {
            Preconditions.checkArgument(monthFilter.matches("(\\d{1,2})-(\\d{1,2})"), "Invalid value inputed for monthFilters.");

            final String[] resultValues = monthFilter.split("-");
            this.monthFilters.add(new MonthIntervalFilter(Integer.parseInt(resultValues[0]), Integer.parseInt(resultValues[1])));
        }
    }

    public Optional<LocalDateTime> getInitialDate() {
        return initialDate;
    }

    public Optional<LocalDateTime> getFinalDate() {
        return finalDate;
    }

    public List<MonthIntervalFilter> getMonthFilters() {
        return monthFilters;
    }

    public boolean isPresent() {
        return parameters.isPresent();
    }

}
