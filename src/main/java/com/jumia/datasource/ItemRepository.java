package com.jumia.datasource;

import com.jumia.domain.item.Item;
import org.springframework.data.repository.CrudRepository;

/**
 * @author rafael.ferrari
 */
public interface ItemRepository extends CrudRepository<Item, Long> {

}
