package com.canoo.dolphin.server.async;

import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class RemotingServlet extends AtmosphereServlet {

    @Override
    public void init(ServletConfig sc) throws ServletException {
        framework().addInitParameter(ApplicationConfig.PROPERTY_SESSION_SUPPORT, "true");
        framework().addInitParameter(ApplicationConfig.ATMOSPHERE_INTERCEPTORS, "org.atmosphere.interceptor.HeartbeatInterceptor");
        framework().addInitParameter(ApplicationConfig.HEARTBEAT_INTERVAL_IN_SECONDS, "3");
        framework().addInitParameter(ApplicationConfig.CLIENT_HEARTBEAT_INTERVAL_IN_SECONDS, "3");
        super.init(sc);
    }

}
