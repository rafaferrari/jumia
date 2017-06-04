package com.jumia.domain.order;

import com.jumia.domain.exception.ServiceException;
import java.util.Optional;

/**
 * Service responsible for Order actions.
 * 
 * @author rafael.ferrari
 */
public interface OrderService {

    /**
     * Count All Orders Grouping by Product Creation Date.
     *
     * @param orderDTO - Object with data to filter Orders.
     * @return Count Orders filtered by Product Creation Date.
     * @throws ServiceException
     */
    StringBuilder countAllByProductCreationDate(final OrderDTO orderDTO) throws ServiceException;

    /**
     * Save new Order.
     *
     * @param order - Order to be saved.
     * @return Order saved.
     * @throws ServiceException
     */
    Optional<Order> save(final Order order) throws ServiceException;

}
