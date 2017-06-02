package com.jumia.domain.order;

import com.jumia.datasource.OrderRepository;
import java.time.LocalDateTime;
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

    @Override
    public Iterable<Order> findAllByProductCreationDate(final LocalDateTime initialDate, final LocalDateTime finalDate) {
        return orderRepository.findAllByProductCreationDate(initialDate, finalDate);
    }

    @Override
    public Optional<Order> save(final Order order) {
        return Optional.of(orderRepository.save(order));
    }

}
