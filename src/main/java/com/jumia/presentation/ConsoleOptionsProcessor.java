package com.jumia.presentation;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.jumia.domain.order.MonthIntervalFilter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Component Responsible For Process Console Parameters.
 *
 * @author rafael.ferrari
 */
public class ConsoleOptionsProcessor {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String[] arguments;
    private Optional<LocalDateTime> initialDate;
    private Optional<LocalDateTime> finalDate;
    private List<MonthIntervalFilter> monthFilters;
    private Optional<JsonObject> parameters;

    public ConsoleOptionsProcessor(final String... arguments) {
        this.arguments = arguments;
    }

    public void process() {
        parameters = createConsoleOptions(arguments);
        parameters.ifPresent(p -> {
            initialDate = Optional.of(LocalDateTime.parse(p.get("initialDate").getAsString(), DATE_TIME_FORMATTER));
            finalDate = Optional.of(LocalDateTime.parse(p.get("finalDate").getAsString(), DATE_TIME_FORMATTER));
            monthFilters = parseMonthFilters(p.get("monthSort").getAsString());
        });
    }

    private Optional<JsonObject> createConsoleOptions(final String... arguments) {
        return new ConsoleOptionsBuilder(arguments)
                .addOptions()
                .validateArguments()
                .create();
    }

    private List<MonthIntervalFilter> parseMonthFilters(final String monthSort) {
        final List<MonthIntervalFilter> monthIntervalFilters = new ArrayList<>();

        final String[] filters = monthSort.replaceAll("\\s+", "").split(",");
        for (final String monthFilter : filters) {
            Preconditions.checkArgument(monthFilter.matches("(\\d{1,2})-(\\d{1,2})"), "Invalid value inputed for monthFilters.");

            final String[] resultValues = monthFilter.split("-");
            monthIntervalFilters.add(new MonthIntervalFilter(Integer.parseInt(resultValues[0]), Integer.parseInt(resultValues[1])));
        }

        return monthIntervalFilters;
    }

    public boolean isPresent() {
        return parameters.isPresent();
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

}
