package com.jumia;

import com.jumia.domain.order.Order;
import com.jumia.presentation.OrderDtoBuilder;
import com.jumia.domain.order.SearchOrderDTO;
import com.jumia.domain.order.OrderService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author rafael.ferrari
 */
@EnableJpaRepositories
@SpringBootApplication
public class Application {

    @Autowired
    private OrderService orderService;

    public static void main(final String... arguments) {
        final ConfigurableApplicationContext context = SpringApplication.run(Application.class, arguments);
        final Application app = context.getBean(Application.class);
        app.run(arguments);
    }

    private void run(final String... args) {
        final Optional<SearchOrderDTO> orderDTO = new OrderDtoBuilder(args)
                .addOptions()
                .validateArguments()
                .create();
        
        orderDTO.ifPresent(o -> {
            final Iterable<Order> result = orderService.findAllByProductCreationDate(o);
            result.forEach(c -> {
                System.out.println(c.getCustomerName());
            });
        });
    }

}
