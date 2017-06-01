package com.jumia.datasource;

import com.jumia.domain.order.Orders;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author rafael.ferrari
 */
public interface OrderRepository extends CrudRepository<Orders, Long> {

    /**
     * Search all Orders by Product Creation Date.
     *
     * @param initialDate - Initial Date to filter the Products.
     * @param finalDate - Final Date to filter the Products.
     * @return All Orders filtered by Product Creation Date.
     */
    @Query("SELECT o FROM Orders o LEFT JOIN o.items i")
    List<Orders> findAllByProductCreationDate();

}
