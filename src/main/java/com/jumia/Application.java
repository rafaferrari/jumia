package com.jumia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.jumia.presentation.Executor;

/**
 * Responsible for Configure and Start Application.
 *
 * @author rafael.ferrari
 */
@EntityScan(
        basePackageClasses = {Application.class, Jsr310JpaConverters.class}
)
@EnableJpaRepositories
@SpringBootApplication
public class Application {

    public static void main(final String... arguments) {
        final ApplicationContext context = SpringApplication.run(Application.class, arguments);
        context.getBean(Executor.class).run(arguments);
    }

}
