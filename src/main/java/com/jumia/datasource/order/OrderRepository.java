package com.jumia.datasource.order;

import com.jumia.domain.order.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * @author rafael.ferrari
 */
public interface OrderRepository extends CrudRepository<Order, Long> {

}
