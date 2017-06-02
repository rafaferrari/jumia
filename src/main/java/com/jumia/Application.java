package com.jumia;

import com.jumia.presentation.NotificadorBuilder;
import com.google.gson.JsonObject;
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

    public static void main(final String... args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Application app = context.getBean(Application.class);
        app.run(args);
    }

    public void run(final String... args) {
        final Optional<JsonObject> jsonObjectOpt = new NotificadorBuilder(args)
                .addOptions()
                .validateArguments()
                .create();

    }

}
