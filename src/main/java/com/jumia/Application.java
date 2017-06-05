package com.jumia;

import com.google.gson.JsonObject;
import com.jumia.domain.exception.ServiceException;
import com.jumia.domain.order.MonthIntervalFilter;
import com.jumia.presentation.ConsoleOptionsBuilder;
import com.jumia.domain.order.OrderDTO;
import com.jumia.domain.order.OrderService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author rafael.ferrari
 */
@EntityScan(
        basePackageClasses = {Application.class, Jsr310JpaConverters.class}
)
@EnableJpaRepositories
@SpringBootApplication
public class Application {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    public static void main(final String... arguments) {
        final ConfigurableApplicationContext context = SpringApplication.run(Application.class, arguments);
        final Application app = context.getBean(Application.class);
        app.run(arguments);
    }

    private void run(final String... arguments) {
        logger.debug("Running application.");
        final Optional<JsonObject> parameters = createConsoleOptions(arguments);
        parameters.ifPresent(p -> {
            final LocalDateTime initialDate = LocalDateTime.parse(p.get("initialDate").getAsString(), DATE_TIME_FORMATTER);
            final LocalDateTime finalDate = LocalDateTime.parse(p.get("finalDate").getAsString(), DATE_TIME_FORMATTER);
            final List<MonthIntervalFilter> monthFilters = parseMonthFilters(p.get("monthSort").getAsString());            
            try {
                final OrderDTO orderDTO = new OrderDTO.OrderDTOBuilder(initialDate, finalDate, monthFilters).build();
                logger.info(orderService.countAllByProductCreationDate(Optional.of(orderDTO)).toString());
            } catch (final ServiceException e) {
                //TODO
                e.printStackTrace();
            }
        });
    }

    private Optional<JsonObject> createConsoleOptions(final String... arguments) {
        logger.debug("Creating Console Options With Console Args.");
        return new ConsoleOptionsBuilder(arguments)
                .addOptions()
                .validateArguments()
                .create();
    }

    private List<MonthIntervalFilter> parseMonthFilters(final String monthSort) {
        logger.debug("Parsing of monthFilters values.");
        final List<MonthIntervalFilter> monthIntervalFilters = new ArrayList<>();
        final String[] filters = monthSort.replaceAll("\\s+", "").split(",");
        for (final String monthFilter : filters) {
            if (!monthFilter.matches("(\\d{1,2})-(\\d{1,2})")) {
                throw new IllegalArgumentException("Invalid value inputed for monthFilters.");
            }
            final String[] resultValues = monthFilter.split("-");
            monthIntervalFilters.add(new MonthIntervalFilter.MonthIntervalFilterBuilder(
                    Integer.parseInt(resultValues[0]), Integer.parseInt(resultValues[1])).build());
        }
        return monthIntervalFilters;
    }

}
