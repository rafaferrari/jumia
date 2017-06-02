package com.jumia.datasource;

import com.jumia.domain.order.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author rafael.ferrari
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Search all Orders by Product Creation Date.
     *
     * @param initialDate - Initial Date to use in filter.
     * @param finalDate - Final Date to use in filter.
     * @return All Orders filtered by Product Creation Date.
     */
    @Query("SELECT o FROM product_order o JOIN FETCH o.items i WHERE i.product.creationDate BETWEEN :initialDate AND :finalDate")
    List<Order> findAllByProductCreationDate(@Param("initialDate") final LocalDateTime initialDate, @Param("finalDate") final LocalDateTime finalDate);

}
