package com.jumia.domain.order;

import com.jumia.datasource.item.ItemRepository;
import com.jumia.datasource.order.OrderRepository;
import com.jumia.domain.exception.ServiceException;
import com.jumia.domain.item.Item;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public StringBuilder countAllByProductCreationDate(final OrderDTO orderDTO) throws ServiceException {
        try {
            logger.info("Filtering Orders by Production Creating Date.");
            final StringBuilder result = new StringBuilder();
            final List<Item> items = itemRepository.findAllByOrderPlacedDate(orderDTO.getInitialDate(), orderDTO.getFinalDate());
            if (!items.isEmpty()) {
                orderDTO.getMonthIntervalFilters().forEach(c -> {
                    final long count = items.parallelStream().filter(isBetween(c.getInitialMonthDate(), c.getFinalMonthDate())).count();
                    result.append(String.format("%s-%s months: %s orders %n", c.getInitialMonthDate().getMonth(), c.getFinalMonthDate().getMonth(), count));
                });
            }
            return result;
        } catch (Exception e) {
            logger.error("Error Filtering Orders by Production Creating Date.");
            throw new ServiceException(e.getMessage());
        }
    }

    private static Predicate<Item> isBetween(final LocalDateTime initialDate, final LocalDateTime finalDate) {
        return p -> p.getProduct().getCreationDate().isAfter(initialDate)
                && p.getProduct().getCreationDate().isBefore(finalDate);
    }

    @Override
    public Optional<Order> save(final Order order) throws ServiceException {
        try {
            logger.info("Saving Order.");
            return Optional.of(orderRepository.save(order));
        } catch (final Exception e) {
            logger.error("Error saving/updating an Order.");
            throw new ServiceException(e.getMessage());
        }
    }

}
