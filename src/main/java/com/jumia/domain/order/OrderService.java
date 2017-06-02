package com.jumia.domain.order;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author rafael.ferrari
 */
public interface OrderService {

    /**
     * Find All Products filtering by Product Creation Date.
     * 
     * @param initialDate - Initial Date to use in filter.
     * @param finalDate - Final Date to use in filter.
     * @return All Orders filtered by Product Creation Date.
     */
    Iterable<Order> findAllByProductCreationDate(final LocalDateTime initialDate, final LocalDateTime finalDate);

    /**
     * Save new Order.
     * 
     * @param order - Order to be saved.
     * @return Order saved.
     */
    Optional<Order> save(final Order order);

}
