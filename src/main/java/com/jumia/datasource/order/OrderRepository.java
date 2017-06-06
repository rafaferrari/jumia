package com.jumia.datasource.order;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jumia.domain.order.Order;

/**
 * @author rafael.ferrari
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
