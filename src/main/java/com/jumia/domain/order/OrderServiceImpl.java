package com.jumia.domain.order;

import com.jumia.datasource.item.ItemRepository;
import com.jumia.datasource.order.OrderRepository;
import com.jumia.domain.exception.ServiceException;
import com.jumia.domain.item.Item;
import java.time.Month;
import java.util.*;
import java.util.Optional;
import java.util.function.Predicate;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rafael.ferrari
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public StringBuilder countAllByProductCreationDate(final Optional<OrderDTO> orderDTO) throws ServiceException {
        logger.debug("Filtering Orders by Production Creating Date.");
        try {
            final StringBuilder result = new StringBuilder();
            final List<Item> items = itemRepository.findAllByOrderPlacedDate(orderDTO.get().getInitialDate(), orderDTO.get().getFinalDate());
            if (!items.isEmpty()) {
                orderDTO.get().getMonthIntervalFilters().forEach(c -> {
                    final long count = items.parallelStream().filter(isBetween(c.getInitialMonthFilter(), c.getFinalMonthFilter())).count();
                    result.append(String.format("%n %s-%s months: %s orders", c.getInitialMonthFilter(), c.getFinalMonthFilter(), count));
                });
            }
            return result;
        } catch (final Exception e) {
            logger.error("Error Filtering Orders by Production Creating Date.");
            throw new ServiceException(e.getMessage());
        }
    }

    private static Predicate<Item> isBetween(final Month initialMonth, final Month finalMonth) {
        return p -> p.getProduct().getCreationDate().getMonth().compareTo(initialMonth) >= 0
                && p.getProduct().getCreationDate().getMonth().compareTo(finalMonth) <= 0;
    }

    @Override
    public Optional<Order> save(final Optional<Order> order) throws ServiceException {
        logger.debug("Saving Order.");
        if (!order.isPresent()) {
            throw new IllegalArgumentException("Invalid Order to Save. The Order is empty.");
        }
        try {
            return Optional.of(orderRepository.save(order.get()));
        } catch (final Exception e) {
            logger.error("Error saving/updating an Order.");
            throw new ServiceException(e.getMessage());
        }
    }

}
