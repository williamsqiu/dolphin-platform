package com.canoo.dolphin.server.async;

import org.atmosphere.config.service.*;
import org.atmosphere.runtime.AtmosphereResource;
import org.atmosphere.runtime.AtmosphereResourceEvent;

@ManagedService(path = "/bla")
public class AsyncEndpoint {

    public AsyncEndpoint() {
        System.out.println("CREATED");
    }

    @Ready
    public void onReady(final AtmosphereResource resource) {
        System.out.println("READY WITH " + resource.transport().name());
    }

    @Heartbeat
    public void onHeartbeat(final AtmosphereResourceEvent event) {
        System.out.println("Heartbeat send by " + event.getResource());
    }

    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
        System.out.println("CLOSE");
    }

    @Message
    public String onMessage(String message)  {
        System.out.println("MESSAGE: " + message);
        return "YO!";
    }
}