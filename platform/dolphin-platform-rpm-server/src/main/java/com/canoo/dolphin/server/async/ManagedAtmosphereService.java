package com.canoo.dolphin.server.async;

import org.atmosphere.config.service.*;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;

@ManagedService
public class ManagedAtmosphereService {

    Logger LOG = LoggerFactory.getLogger(ManagedAtmosphereService.class);

    public void send(final AtmosphereResource resource, String message) {
        try {
            resource.getResponse().write(message);
            resource.getResponse().flushBuffer();
        } catch (Exception e) {
            throw new RuntimeException("Error in write", e);
        }
    }

    @Ready
    public void onReady(final AtmosphereResource resource) {
        LOG.info("READY for connection {} in session {} with transport {}", resource.uuid(), resource.session().getId(), resource.transport());

        resource.suspend();
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(1_000);
                        LOG.info("Sending ping for connection {} in session {}", resource.uuid(), resource.session().getId());
                        send(resource, "Ping (" + i + ")");
                    } catch (Exception e) {
                        throw new RuntimeException("Error in Ping-Loop", e);
                    }
                }
            }
        });
    }

    @Resume
    public void onResume(final AtmosphereResource resource) {
        LOG.info("RESUME for connection {} in session {}", resource.uuid(), resource.session().getId());
    }

    @Heartbeat
    public void onHeartbeat(final AtmosphereResourceEvent event) {
        LOG.info("HEARTBEAT for connection {} in session {}", event.getResource().uuid(), event.getResource().session().getId());
    }

    @Disconnect
    public void onDisconnect(final AtmosphereResourceEvent event) {
        if (event.isCancelled()) {
            onCancelled(event);
        } else if (event.isClosedByClient()) {
            onClosed(event);
        } else {
            LOG.info("DISCONNECT for connection {} in session {}", event.getResource().uuid(), event.getResource().session().getId());
        }
    }

    public void onClosed(final AtmosphereResourceEvent event) {
        LOG.info("CLOSED for connection {} in session {}", event.getResource().uuid(), event.getResource().session().getId());
    }

    public void onCancelled(final AtmosphereResourceEvent event) {
        LOG.info("CANCELED for connection {} in session {}", event.getResource().uuid(), event.getResource().session().getId());
    }

    @Message
    public void onMessage(final AtmosphereResource resource, String message) throws IOException {
        LOG.info("MESSAGE for connection {} in session {} - {}", resource.uuid(), resource.session().getId(), message);
    }
}
