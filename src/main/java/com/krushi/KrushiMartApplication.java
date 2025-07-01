package com.krushi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.krushi")
@EnableJpaRepositories(basePackages = "com.krushi.repository")
@EntityScan(basePackages = "com.krushi.model")
public class KrushiMartApplication {
    public static void main(String[] args) {
        SpringApplication.run(KrushiMartApplication.class, args);
    }
}
