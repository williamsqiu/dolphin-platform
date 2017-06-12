package com.canoo.impl.server.bootstrap.modules;

import com.canoo.dolphin.util.Assert;
import com.canoo.impl.server.servlet.HttpSessionMutexHolder;
import com.canoo.platform.server.spi.AbstractBaseModule;
import com.canoo.platform.server.spi.ModuleInitializationException;
import com.canoo.platform.server.spi.ServerCoreComponents;

import javax.servlet.ServletContext;

public class HttpMutexModule extends AbstractBaseModule {

    public static final String HTTP_MUTEX_MODULE = "HttpMutexModule";

    public static final String HTTP_MUTEX_MODULE_ACTIVE = "httpMutexModuleActive";

    @Override
    protected String getActivePropertyName() {
        return HTTP_MUTEX_MODULE_ACTIVE;
    }

    @Override
    public void initialize(final ServerCoreComponents coreComponents) throws ModuleInitializationException {
        Assert.requireNonNull(coreComponents, "coreComponents");
        final ServletContext servletContext = coreComponents.getServletContext();
        Assert.requireNonNull(servletContext, "servletContext");

        HttpSessionMutexHolder mutexHolder = new HttpSessionMutexHolder();
        servletContext.addListener(mutexHolder);
    }
}
