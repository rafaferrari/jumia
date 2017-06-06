package com.jumia.presentation;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.JsonObject;
import java.util.*;
import org.slf4j.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.jumia.domain.exception.ServiceException;
import com.jumia.domain.order.MonthIntervalFilter;
import com.jumia.domain.order.OrderDTO;
import com.jumia.domain.order.OrderService;

/**
 * Component responsible for receive console arguments.
 *
 * @author rafael.ferrari
 */
@Component
public class Executor {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    public void run(final String... arguments) {
        final Optional<JsonObject> parameters = createConsoleOptions(arguments);
        parameters.ifPresent(p -> {
            final LocalDateTime initialDate = LocalDateTime.parse(p.get("initialDate").getAsString(), DATE_TIME_FORMATTER);
            final LocalDateTime finalDate = LocalDateTime.parse(p.get("finalDate").getAsString(), DATE_TIME_FORMATTER);
            final List<MonthIntervalFilter> monthFilters = parseMonthFilters(p.get("monthSort").getAsString());

            final OrderDTO orderDTO = new OrderDTO(initialDate, finalDate, monthFilters);
            try {
                LOGGER.info(orderService.countAllByProductCreationDate(Optional.of(orderDTO)));
            } catch (final ServiceException e) {
                LOGGER.error("Error running application -> " + e.getMessage());
                throw new IllegalStateException(e);
            }
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

}
