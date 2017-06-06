package com.jumia.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import org.slf4j.*;
import com.jumia.domain.exception.ServiceException;
import com.jumia.domain.order.*;

/**
 * Component Responsible For Process Orders.
 *
 * @author rafael.ferrari
 */
@Component
public class ProcessOrder {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    public void process(final ConsoleOptionsProcessor consoleOptions) {
        try {
            final Optional<OrderDTO> orderDTO = createOrderDTO(consoleOptions);
            LOGGER.info(orderService.countAllByProductCreationDate(orderDTO));
        } catch (final ServiceException e) {
            LOGGER.error("Error running application -> " + e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    private Optional<OrderDTO> createOrderDTO(final ConsoleOptionsProcessor consoleOptions) {
        return Optional.of(new OrderDTO(
                consoleOptions.getInitialDate().get(), consoleOptions.getFinalDate().get(), consoleOptions.getMonthFilters()));
    }

}
