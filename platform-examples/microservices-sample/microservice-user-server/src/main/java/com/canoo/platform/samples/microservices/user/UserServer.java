package com.canoo.platform.samples.microservices.user;

import com.canoo.platform.server.spring.DolphinPlatformApplication;
import org.springframework.boot.SpringApplication;

@DolphinPlatformApplication
public class UserServer {

    public static void main(String[] args) {
        SpringApplication.run(UserServer.class);
    }
}
