package com.canoo.platform.samples.security;

import com.canoo.platform.server.spring.DolphinPlatformApplication;
import com.canoo.platform.server.spring.EnableSecurity;
import org.springframework.boot.SpringApplication;

@DolphinPlatformApplication
@EnableSecurity
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class);
    }
}
