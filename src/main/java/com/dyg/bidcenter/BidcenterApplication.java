package com.dyg.bidcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BidcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BidcenterApplication.class, args);
    }

}
