package com.canoo.platform.samples.lazyloading;

import com.canoo.platform.remoting.server.spring.EnableRemoting;
import com.canoo.platform.server.spring.DolphinPlatformApplication;
import org.springframework.boot.SpringApplication;

@DolphinPlatformApplication
@EnableRemoting
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class);
    }
}
