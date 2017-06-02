package com.jumia.domain.order;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author rafael.ferrari
 */
public interface OrderService {

    Iterable<Orders> findAllByProductCreationDate(final LocalDateTime initialDate, final LocalDateTime finalDate);

    Optional<Orders> save(final Orders order);

}
