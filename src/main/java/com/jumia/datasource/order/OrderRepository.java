package com.jumia.datasource.order;

import com.jumia.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author rafael.ferrari
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
