package com.canoo.impl.server.bootstrap.modules;

import com.canoo.impl.server.bootstrap.ServerCoreComponents;
import com.canoo.impl.server.config.PlatformConfiguration;
import com.canoo.impl.server.servlet.HttpSessionTimeoutListener;
import com.canoo.platform.server.spi.ModuleDefinition;
import com.canoo.platform.server.spi.ModuleInitializationException;

import javax.servlet.ServletContext;

@ModuleDefinition(HttpTimeoutModule.HTTP_TIMEOUT_MODULE)
public class HttpTimeoutModule extends AbstractBaseModule {

    public static final String HTTP_TIMEOUT_MODULE = "HttpTimeoutModule";

    public static final String HTTP_TIMEOUT_ACTIVE = "httpTimeoutActive";

    @Override
    protected String getActivePropertyName() {
        return HTTP_TIMEOUT_ACTIVE;
    }

    @Override
    public void initialize(ServerCoreComponents coreComponents) throws ModuleInitializationException {
        final ServletContext servletContext = coreComponents.getServletContext();
        final PlatformConfiguration configuration = coreComponents.getConfiguration();

        HttpSessionTimeoutListener sessionCleaner = new HttpSessionTimeoutListener(configuration);
        servletContext.addListener(sessionCleaner);
    }
}
