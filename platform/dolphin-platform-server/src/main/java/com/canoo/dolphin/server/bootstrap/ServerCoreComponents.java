package com.canoo.dolphin.server.bootstrap;

import com.canoo.dolphin.concurrency.DolphinPlatformThreadFactory;
import com.canoo.dolphin.server.config.PlatformConfiguration;
import com.canoo.dolphin.util.Assert;

import javax.servlet.ServletContext;

public class ServerCoreComponents {

    private final ServletContext servletContext;

    private final PlatformConfiguration configuration;

    private final DolphinPlatformThreadFactory threadFactory;

    public ServerCoreComponents(final ServletContext servletContext, final PlatformConfiguration configuration, final DolphinPlatformThreadFactory threadFactory) {
        this.servletContext = Assert.requireNonNull(servletContext, "servletContext");
        this.configuration = Assert.requireNonNull(configuration, "configuration");
        this.threadFactory = Assert.requireNonNull(threadFactory, "threadFactory");
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public PlatformConfiguration getConfiguration() {
        return configuration;
    }

    public DolphinPlatformThreadFactory getThreadFactory() {
        return threadFactory;
    }
}
