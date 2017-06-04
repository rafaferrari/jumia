package com.jumia.domain.order;

import com.jumia.domain.exception.ServiceException;
import com.jumia.datasource.product.ProductRepository;
import com.jumia.domain.item.Item;
import com.jumia.domain.product.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setup() {
        populateDatabase();
    }

    @Test
    public void test_should_get_all_orders_by_order_placed_date() throws ServiceException {
        // GIVEN 
        final LocalDateTime initialDate = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0);

        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
        monthFilters.add(new MonthIntervalFilter.MonthIntervalFilterBuilder(initialDate, 1, 6).build());

        final OrderDTO orderDTO = new OrderDTO.OrderDTOBuilder(initialDate, finalDate, monthFilters).build();

        // WHEN
        final StringBuilder campanhas = orderService.countAllByProductCreationDate(orderDTO);

        // THEN
        assertThat(campanhas.length()).isGreaterThan(1);
    }

    @Test
    public void test_should_not_get_all_orders_by_order_placed_date() throws ServiceException {
        // GIVEN 
        final LocalDateTime initialDate = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0);

        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
        monthFilters.add(new MonthIntervalFilter.MonthIntervalFilterBuilder(initialDate, 6, 9).build());

        final OrderDTO orderDTO = new OrderDTO.OrderDTOBuilder(initialDate, finalDate, monthFilters).build();

        // WHEN
        final StringBuilder campanhas = orderService.countAllByProductCreationDate(orderDTO);

        // THEN
        assertThat(campanhas.length()).isEqualTo(0);
    }

    @Test
    public void test_should_not_get_all_orders_with_invalid_placed_date() throws ServiceException {
        // GIVEN 
        final LocalDateTime initialDate = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0);

        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
        monthFilters.add(new MonthIntervalFilter.MonthIntervalFilterBuilder(initialDate, 7, 12).build());

        final OrderDTO orderDTO = new OrderDTO.OrderDTOBuilder(initialDate, finalDate, monthFilters).build();

        // WHEN
        final StringBuilder campanhas = orderService.countAllByProductCreationDate(orderDTO);

        // THEN
        assertThat(campanhas.length()).isGreaterThan(0);
    }

    @Test(expected = ServiceException.class)
    public void test_should_throw_exception_when_save_invalid_order() throws ServiceException {
        // GIVEN
        final Order order = null;

        // WHEN
        orderService.save(order);

        // THEN 
        // Catch the ServiceException
    }

    @Test(expected = ServiceException.class)
    public void test_should_throw_exception_when_get_all_orders_with_invalid_orderdto() throws ServiceException {
        // GIVEN
        final OrderDTO orderDTO = null;

        // WHEN
        orderService.countAllByProductCreationDate(orderDTO);

        // THEN 
        // Catch the ServiceException
    }

    private void populateDatabase() {
        try {
            final Product product = createProduct();
            final Set<Item> items = createItems(product);
            createOrder(items);
        } catch (final ServiceException e) {
            e.printStackTrace();
        }
    }

    private Product createProduct() {
        final Product product = new Product();
        product.setName("Cellphone");
        product.setCategory("Category");
        product.setPrice(BigDecimal.ONE);
        product.setWeight("100g");
        product.setCreationDate(LocalDateTime.of(2016, Month.MARCH, 2, 0, 0, 0));
        productRepository.save(product);
        return product;
    }

    private Set<Item> createItems(final Product produto) {
        final Item item = new Item();
        item.setCost(BigDecimal.ONE);
        item.setShippingFee(BigDecimal.ONE);
        item.setTaxAmount(BigDecimal.ONE);
        item.setProduct(produto);

        final Set<Item> items = new HashSet<>();
        items.add(item);
        return items;
    }

    private void createOrder(final Set<Item> items) throws ServiceException {
        final Order order = new Order();
        order.setCustomerName("Rafael");
        order.setCustomerContact("123456");
        order.setGrandTotal(BigDecimal.ONE);
        order.setShippingAddress("Street 1");
        order.setPlacedDate(LocalDateTime.of(2016, Month.MARCH, 4, 0, 0, 0));
        order.setItems(items);
        orderService.save(order);
    }

}
