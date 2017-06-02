package com.jumia.domain.order;

import java.util.Optional;

/**
 * @author rafael.ferrari
 */
public interface OrderService {

    /**
     * Find All Products filtering by Product Creation Date.
     *
     * @param orderDTO - Object with data to filter Orders.
     * @return All Orders filtered by Product Creation Date.
     */
    Iterable<Order> findAllByProductCreationDate(final SearchOrderDTO orderDTO);

    /**
     * Save new Order.
     *
     * @param order - Order to be saved.
     * @return Order saved.
     */
    Optional<Order> save(final Order order);

}
