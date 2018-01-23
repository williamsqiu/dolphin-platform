package com.canoo.dolphin.samples.security;

import com.canoo.platform.remoting.server.spring.EnableRemoting;
import com.canoo.platform.server.spring.DolphinPlatformApplication;
import com.canoo.platform.server.spring.EnableSecurity;
import org.springframework.boot.SpringApplication;

@DolphinPlatformApplication
@EnableRemoting
@EnableSecurity
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

}
