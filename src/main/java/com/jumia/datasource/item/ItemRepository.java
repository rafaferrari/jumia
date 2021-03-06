package com.jumia.datasource.item;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jumia.domain.item.Item;

/**
 * @author rafael.ferrari
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Search all Items by Order Placed Date.
     *
     * @param initialDate - Initial Date to use in filter.
     * @param finalDate - Final Date to use in filter.
     * @return All Items filtered by Order Placed Date.
     */
    @Query("SELECT i FROM ITEM i JOIN FETCH i.order o WHERE o.placedDate BETWEEN :initialDate AND :finalDate")
    List<Item> findAllByOrderPlacedDate(@Param("initialDate") final LocalDateTime initialDate, @Param("finalDate") final LocalDateTime finalDate);

}
