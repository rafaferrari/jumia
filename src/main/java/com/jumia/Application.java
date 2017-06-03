package com.jumia;

import com.google.gson.JsonObject;
import com.jumia.domain.exception.ServiceException;
import com.jumia.domain.order.MonthFilterDTO;
import com.jumia.domain.order.Order;
import com.jumia.presentation.ConsoleOptionsBuilder;
import com.jumia.domain.order.OrderDTO;
import com.jumia.domain.order.OrderService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author rafael.ferrari
 */
@EnableJpaRepositories
@SpringBootApplication
public class Application {

    @Autowired
    private OrderService orderService;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(final String... arguments) {
        final ConfigurableApplicationContext context = SpringApplication.run(Application.class, arguments);
        final Application app = context.getBean(Application.class);
        app.run(arguments);
    }

    private void run(final String... args) {
        final Optional<JsonObject> parameters = new ConsoleOptionsBuilder(args)
                .addOptions()
                .validateArguments()
                .create();

        parameters.ifPresent(p -> {
            final LocalDateTime initialDate = LocalDateTime.parse(p.get("initialDate").getAsString(), DATE_TIME_FORMATTER);
            final LocalDateTime finalDate = LocalDateTime.parse(p.get("finalDate").getAsString(), DATE_TIME_FORMATTER);

            final List<MonthFilterDTO> monthFilters = new ArrayList<>();
            monthFilters.add(new MonthFilterDTO.MonthFilterDTOBuilder(initialDate, 1, 6).build());

            final OrderDTO orderDTO = new OrderDTO.OrderDTOBuilder(initialDate, finalDate, monthFilters).build();

            final List<Long> result;
            try {
                result = orderService.findAllByProductCreationDate(orderDTO);
                result.forEach(c -> {
                    System.out.println("Result -> " + c);
                });
            } catch (ServiceException ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

}
