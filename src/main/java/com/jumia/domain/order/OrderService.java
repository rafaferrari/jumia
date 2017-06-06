package com.jumia.domain.order;

import com.jumia.domain.exception.ServiceException;
import java.util.Optional;

/**
 * Service responsible for Order Domain Actions.
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
    String countAllByProductCreationDate(final Optional<OrderDTO> orderDTO) throws ServiceException;

    /**
     * Save new Order.
     *
     * @param order - Order to be saved.
     * @return Order saved.
     * @throws ServiceException
     */
    Optional<Order> save(final Optional<Order> order) throws ServiceException;

}
