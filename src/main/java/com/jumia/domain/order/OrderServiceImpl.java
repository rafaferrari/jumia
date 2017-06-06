package com.jumia.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Preconditions;
import java.time.Month;
import java.util.*;
import org.slf4j.*;
import java.util.Optional;
import java.util.function.Predicate;
import com.jumia.datasource.item.ItemRepository;
import com.jumia.datasource.order.OrderRepository;
import com.jumia.domain.exception.ServiceException;
import com.jumia.domain.item.Item;

/**
 * @author rafael.ferrari
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public String countAllByProductCreationDate(final Optional<OrderDTO> orderDTO) throws ServiceException {
        try {
            final StringBuilder result = new StringBuilder();
            final List<Item> items = itemRepository.findAllByOrderPlacedDate(orderDTO.get().getInitialDate(), orderDTO.get().getFinalDate());
            if (!items.isEmpty()) {
                orderDTO.get().getMonthIntervalFilters().forEach(c -> {
                    final long count = items.stream().filter(isBetween(c.getInitialMonthFilter(), c.getFinalMonthFilter())).count();
                    result.append(String.format("%n %s-%s months: %s orders", c.getInitialMonthFilter(), c.getFinalMonthFilter(), count));
                });
            }
            return result.toString();
        } catch (final Exception e) {
            LOGGER.error("Error Filtering Orders by Production Creating Date.");
            throw new ServiceException(e.getMessage());
        }
    }

    private static Predicate<Item> isBetween(final Month initialMonth, final Month finalMonth) {
        return p -> p.getProduct().getCreationDate().getMonth().compareTo(initialMonth) >= 0
                && p.getProduct().getCreationDate().getMonth().compareTo(finalMonth) <= 0;
    }

    @Override
    public Optional<Order> save(final Optional<Order> order) throws ServiceException {
        Preconditions.checkArgument(order.isPresent(), "Invalid Order to Save. The Order is empty.");
        try {
            return Optional.of(orderRepository.save(order.get()));
        } catch (final Exception e) {
            LOGGER.error("Error saving/updating an Order.");
            throw new ServiceException(e.getMessage());
        }
    }

}
