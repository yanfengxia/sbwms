package com.beta.ms.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableCaching
@Slf4j
public class UserServiceApplication {
    public static void main (String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
