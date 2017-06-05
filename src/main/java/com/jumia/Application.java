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

    private void run(final String... args) {
        final Optional<JsonObject> parameters = createConsoleOptions(args);
        parameters.ifPresent(p -> {
            final LocalDateTime initialDate = LocalDateTime.parse(p.get("initialDate").getAsString(), DATE_TIME_FORMATTER);
            final LocalDateTime finalDate = LocalDateTime.parse(p.get("finalDate").getAsString(), DATE_TIME_FORMATTER);

            //TODO
            final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
            monthFilters.add(new MonthIntervalFilter.MonthIntervalFilterBuilder(1, 3).build());
            monthFilters.add(new MonthIntervalFilter.MonthIntervalFilterBuilder(4, 6).build());

            final OrderDTO orderDTO = new OrderDTO.OrderDTOBuilder(initialDate, finalDate, monthFilters).build();
            try {
                logger.info(orderService.countAllByProductCreationDate(orderDTO).toString());
            } catch (ServiceException e) {
                //TODO
                e.printStackTrace();
            }
        });
    }

    private Optional<JsonObject> createConsoleOptions(final String... args) {
        logger.debug("Creating Console Options With Console Args.");
        return new ConsoleOptionsBuilder(args)
                .addOptions()
                .validateArguments()
                .create();
    }

}
