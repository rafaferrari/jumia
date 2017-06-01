package com.jumia.domain.order;

import com.jumia.domain.item.Item;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Before
    public void setup() throws ServiceException {
        orderService.save(populateOrder("Rafael", LocalDate.of(2017, Month.JANUARY, 1)));
    }

    private Set<Item> populateItems() {
        final Item item = new Item();
        item.setCost(BigDecimal.ONE);
        item.setShippingFee(BigDecimal.ONE);
        item.setTaxAmount(BigDecimal.ONE);

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
        order.setItens(populateItems());
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

}
