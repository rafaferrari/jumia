package com.jumia.datasource.product;

import com.jumia.domain.product.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * @author rafael.ferrari
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

}
