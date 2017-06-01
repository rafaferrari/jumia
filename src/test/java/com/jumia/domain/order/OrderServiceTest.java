package com.jumia.domain.order;

import com.jumia.datasource.ItemRepository;
import com.jumia.datasource.ProductRepository;
import com.jumia.domain.item.Item;
import com.jumia.domain.product.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.hibernate.service.spi.ServiceException;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setup() throws ServiceException {
        orderService.save(populateOrder("Rafael", LocalDate.of(2017, Month.JANUARY, 10)));
    }

    private Product createProduct() {
        final Product product = new Product();
        product.setName("Cellphone");
        product.setCategory("Category");
        product.setPrice(BigDecimal.ONE);
        product.setWeight("100g");
        product.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 10, 0, 0, 0));
        productRepository.save(product);
        return product;
    }

    private Set<Item> createItems() {
        final Item item = new Item();
        item.setCost(BigDecimal.ONE);
        item.setShippingFee(BigDecimal.ONE);
        item.setTaxAmount(BigDecimal.ONE);
        item.setProduct(createProduct());
        itemRepository.save(item);

        final Set<Item> itens = new HashSet<>();
        itens.add(item);
        return itens;
    }

    private Orders populateOrder(final String customerName, final LocalDate creationDate) {
        final Orders order = new Orders();
        order.setCustomerName(customerName);
        order.setCustomerContact("123456");
        order.setGrandTotal(BigDecimal.ONE);
        order.setShippingAddress("Street 1");
        order.setCreationDate(creationDate);
        order.setItems(createItems());
        return order;
    }

    @Test
    public void test_get_all_orders() throws ServiceException {
        // GIVEN 
        final Long total = 1L;

        // WHEN
        final Iterable<Orders> campanhas = orderService.findAll();

        // THEN
        assertThat(campanhas.spliterator().getExactSizeIfKnown()).isEqualTo(total);
    }

    @Test
    public void test_get_all_orders_by_product_creation_date() throws ServiceException {
        // GIVEN 
        final Iterable<Product> products = productRepository.findAll();
        final Long total = 1L;
        final LocalDateTime initialDate = LocalDateTime.of(2016, Month.MARCH, 10, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2018, Month.MARCH, 10, 0, 0, 0);

        // WHEN
        final Iterable<Orders> campanhas = orderService.findAllByProductCreationDate(initialDate, finalDate);

        // THEN
        assertThat(campanhas.spliterator().getExactSizeIfKnown()).isEqualTo(total);
    }

}
