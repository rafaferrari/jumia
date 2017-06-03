package com.jumia.domain.order;

import com.jumia.domain.exception.ServiceException;
import java.util.List;
import java.util.Optional;

/**
 * Service responsible for Order actions.
 * 
 * @author rafael.ferrari
 */
public interface OrderService {

    /**
     * Find All Products filtering by Product Creation Date.
     *
     * @param orderDTO - Object with data to filter Orders.
     * @return All Orders filtered by Product Creation Date.
     * @throws ServiceException
     */
    List<Long> findAllByProductCreationDate(final OrderDTO orderDTO) throws ServiceException;

    /**
     * Save new Order.
     *
     * @param order - Order to be saved.
     * @return Order saved.
     * @throws ServiceException
     */
    Optional<Order> save(final Order order) throws ServiceException;

}
