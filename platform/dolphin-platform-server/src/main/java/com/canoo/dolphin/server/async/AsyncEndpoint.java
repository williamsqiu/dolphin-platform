package com.canoo.dolphin.server.async;

import com.canoo.dolphin.server.context.DolphinContext;
import com.canoo.dolphin.server.context.DolphinContextUtils;
import com.canoo.dolphin.util.DolphinRemotingException;
import org.atmosphere.config.service.*;
import org.atmosphere.runtime.AtmosphereResource;
import org.atmosphere.runtime.AtmosphereResourceEvent;
import org.opendolphin.core.comm.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@ManagedService(path = "/dolphin")
public class AsyncEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncEndpoint.class);


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
    public String onMessage(String message) {

        return "huhu";
    }

    public String onMessage2(String message) {
        try {
            DolphinContext currentContext = DolphinContextUtils.getContextForCurrentThread();
            if (currentContext == null) {
                throw new RuntimeException("Can not find or create matching dolphin context in session " + "ARHH!");
            }

            //String userAgent = request.getHeader("user-agent");
            //LOG.trace("receiving Request for DolphinContext {} in http session {} from client with user-agent {}", currentContext.getId(), httpSession.getId(), userAgent);

            final List<Command> commands = new ArrayList<>();
            try {
                commands.addAll(currentContext.getDolphin().getServerConnector().getCodec().decode(message));
            } catch (Exception e) {
                throw new RuntimeException("Can not parse request! (DolphinContext " + currentContext.getId() + ")", e);
            }
            LOG.trace("Request for DolphinContext {} in http session {} contains {} commands", currentContext.getId(), "", commands.size());


            final List<Command> results = new ArrayList<>();
            try {
                results.addAll(currentContext.handle(commands));
            } catch (Exception e) {
                throw new RuntimeException("Can not handle the commands (DolphinContext " + currentContext.getId() + ")", e);
            }

            try {
                final String jsonResponse = currentContext.getDolphin().getServerConnector().getCodec().encode(results);
                return jsonResponse;
            } catch (Exception e) {
                throw new RuntimeException("Can not write response!", e);
            }
        } catch (Exception e) {
            throw new DolphinRemotingException("Unexpected Dolphin Platform error", e);
        }
    }
}