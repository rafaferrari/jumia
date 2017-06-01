package com.jumia.domain.order;

import java.util.Optional;

/**
 * @author rafael.ferrari
 */
public interface OrderService {

    Iterable<Orders> findAll();
    
    Optional<Orders> save(final Orders order);

}
