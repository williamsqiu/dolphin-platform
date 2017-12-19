package com.canoo.platform.samples.security;

import com.canoo.platform.remoting.server.spring.EnableRemoting;
import com.canoo.platform.server.spring.DolphinPlatformApplication;
import com.canoo.platform.server.spring.EnableSecurity;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;

@DolphinPlatformApplication
@EnableRemoting
@EnableSecurity
public class Server {

    public static void main(String[] args) {
        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();

        SpringApplication.run(Server.class);
    }
}
