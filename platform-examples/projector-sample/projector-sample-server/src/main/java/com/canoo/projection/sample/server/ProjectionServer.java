package com.canoo.projection.sample.server;

import com.canoo.platform.remoting.server.spring.EnableRemoting;
import com.canoo.platform.server.spring.DolphinPlatformApplication;
import org.springframework.boot.SpringApplication;

@DolphinPlatformApplication
@EnableRemoting
public class ProjectionServer {

    public static void main(String[] args) {
        SpringApplication.run(ProjectionServer.class);
    }

}
