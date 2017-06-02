package com.jumia.domain.order;

import com.jumia.datasource.ItemRepository;
import com.jumia.datasource.OrderRepository;
import com.jumia.domain.item.Item;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rafael.ferrari
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Iterable<Order> findAllByProductCreationDate(final SearchOrderDTO orderDTO) {
        final List<Item> items = itemRepository.findAllByOrderPlacedDate(orderDTO.getInitialDate(), orderDTO.getFinalDate());

        System.out.printf("%d%n", Month.FEBRUARY.maxLength());

        final long count = items.parallelStream().filter(
                item -> item.getProduct().getCreationDate().toLocalDate().isBefore(orderDTO.getInitialDate().toLocalDate())).count();

        return null;
    }

    @Override
    public Optional<Order> save(final Order order) {
        return Optional.of(orderRepository.save(order));
    }

}
