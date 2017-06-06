package com.jumia.datasource.product;

import org.springframework.data.repository.CrudRepository;
import com.jumia.domain.product.Product;

/**
 * @author rafael.ferrari
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

}
