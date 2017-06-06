package com.jumia.domain.order;

import com.jumia.domain.exception.ServiceException;
import com.jumia.datasource.product.ProductRepository;
import com.jumia.domain.item.Item;
import com.jumia.domain.product.Product;
import com.jumia.domain.product.ProductCategoryEnum;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
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
    public void shouldGetAllOrdersByOrderPlacedDate() throws ServiceException {
        // GIVEN 
        final int expectedResult = 1;
        final LocalDateTime initialDate = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0);

        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
        monthFilters.add(new MonthIntervalFilter(1, 6));

        final OrderDTO orderDTO = new OrderDTO(initialDate, finalDate, monthFilters);

        // WHEN
        final String result = orderService.countAllByProductCreationDate(Optional.of(orderDTO));

        // THEN
        assertThat(result.length()).isGreaterThan(expectedResult);
    }

    @Test
    public void shouldNotGetAllOrdersByOrderPlacedDate() throws ServiceException {
        // GIVEN 
        final int expectedResult = 0;
        final LocalDateTime initialDate = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0);

        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
        monthFilters.add(new MonthIntervalFilter(6, 9));

        final OrderDTO orderDTO = new OrderDTO(initialDate, finalDate, monthFilters);

        // WHEN
        final String result = orderService.countAllByProductCreationDate(Optional.of(orderDTO));

        // THEN
        assertThat(result.length()).isEqualTo(expectedResult);
    }

    @Test
    public void shouldNotGetAllOrdersWithInvalidPlacedDate() throws ServiceException {
        // GIVEN 
        final int expectedResult = 0;
        final LocalDateTime initialDate = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0, 0);
        final LocalDateTime finalDate = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0);

        final List<MonthIntervalFilter> monthFilters = new ArrayList<>();
        monthFilters.add(new MonthIntervalFilter(7, 12));

        final OrderDTO orderDTO = new OrderDTO(initialDate, finalDate, monthFilters);

        // WHEN
        final String result = orderService.countAllByProductCreationDate(Optional.of(orderDTO));

        // THEN
        assertThat(result.length()).isGreaterThan(expectedResult);
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionWhenSaveInvalidOrder() throws ServiceException {
        // GIVEN
        final Optional<Order> order = Optional.of(new Order());

        // WHEN
        orderService.save(order);

        // THEN 
        // Catch the ServiceException
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTryToSaveInvalidOrder() throws ServiceException {
        // GIVEN
        final Optional<Order> order = Optional.empty();

        // WHEN
        orderService.save(order);

        // THEN 
        // Catch the IllegalArgumentException
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionWhenGetAllOrdersWithInvalidOrderDto() throws ServiceException {
        // GIVEN
        final OrderDTO orderDTO = null;

        // WHEN
        orderService.countAllByProductCreationDate(Optional.ofNullable(orderDTO));

        // THEN 
        // Catch the ServiceException
    }

    private void populateDatabase() {
        try {
            final Product product = createProduct();
            final Set<Item> items = createItems(product);
            createOrder(items);
        } catch (final ServiceException e) {
            throw new IllegalStateException(e);
        }
    }

    private Product createProduct() {
        final Product product = new Product();
        product.setName("Cellphone");
        product.setPrice(BigDecimal.ONE);
        product.setWeight("100g");
        product.setCategory(ProductCategoryEnum.ELETRONIC);
        product.setCreationDate(LocalDateTime.of(2016, Month.MARCH, 2, 0, 0, 0));
        productRepository.save(product);
        return product;
    }

    private Set<Item> createItems(final Product product) {
        final Item item = new Item();
        item.setCost(BigDecimal.ONE);
        item.setShippingFee(BigDecimal.ONE);
        item.setTaxAmount(BigDecimal.ONE);
        item.setProduct(product);

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
        orderService.save(Optional.of(order));
    }

}
