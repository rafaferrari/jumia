package com.jumia.domain.order;

import com.jumia.datasource.ItemRepository;
import com.jumia.datasource.OrderRepository;
import com.jumia.domain.exception.ServiceException;
import com.jumia.domain.item.Item;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private static Predicate<Item> isBetween(final LocalDateTime initialDate, final LocalDateTime finalDate) {
        return p -> p.getProduct().getCreationDate().isAfter(initialDate)
                && p.getProduct().getCreationDate().isBefore(finalDate);
    }

    @Override
    public List<Long> findAllByProductCreationDate(final OrderDTO orderDTO) {
        final List<Long> teste = new ArrayList<>();
        final List<Item> items = itemRepository.findAllByOrderPlacedDate(orderDTO.getInitialDate(), orderDTO.getFinalDate());
        orderDTO.getMonthFiltersDTO().forEach(c -> {
            final long count = items.parallelStream().filter(isBetween(c.getInitialMonthDate(), c.getFinalMonthDate())).count();
            teste.add(count);
        });
        return teste;
    }

    @Override
    public Optional<Order> save(final Order order) throws ServiceException {
        try {
            logger.error("Saving Order.");
            return Optional.of(orderRepository.save(order));
        } catch (final Exception e) {
            logger.error("Error saving/updating an Order.");
            throw new ServiceException(e.getMessage());
        }
    }

}
