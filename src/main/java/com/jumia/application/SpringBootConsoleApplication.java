package com.jumia.application;

import com.jumia.domain.order.OrderService;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author rafael.ferrari
 */
@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {

    @Autowired
    private OrderService orderService;

    public static void main(final String... args) {
        final SpringApplication app = new SpringApplication(SpringBootConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(final String... args) {
        final LocalTime currentTime = LocalTime.now();
        System.out.println("The current local time is: " + currentTime);
    
        if (args.length == 0) {
            throw new IllegalStateException("Please...");
        }

        //TODO
    }

}
