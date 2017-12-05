package com.canoo.platform.samples.microservices.product;

import com.canoo.platform.server.spring.DolphinPlatformApplication;
import org.springframework.boot.SpringApplication;

@DolphinPlatformApplication
public class ProductServer {

    public static void main(String[] args) {
        SpringApplication.run(ProductServer.class);
    }
}
