package com.malyshev2202.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@EnableJpaRepositories("com.malyshev2202.store.backend.repo.sqlRepo")
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class StoreApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(StoreApplication.class, args);


    }

}
