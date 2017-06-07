package com.jumia.presentation;

import com.jumia.domain.exception.ServiceException;
import java.util.Optional;
import com.jumia.domain.order.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author rafael.ferrari
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProcessOrderTest {

    @SpyBean
    private OrderService orderService;

    @Test(expected = ServiceException.class)
    public void shouldNotProcessOrderWithSuccess() throws ServiceException {
        // GIVEN
        when(orderService.countAllByProductCreationDate(Optional.empty())).thenThrow(ServiceException.class);

        // WHEN
        new ProcessOrder().process(Optional.empty());

        // THEN
        // Catch the IllegalStateException
    }

}
