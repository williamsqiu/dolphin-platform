package com.canoo.dp.impl.server.bootstrap.modules;

import com.canoo.dp.impl.server.servlet.ServerTimingFilter;
import com.canoo.platform.core.PlatformConfiguration;
import com.canoo.platform.server.spi.AbstractBaseModule;
import com.canoo.platform.server.spi.ModuleDefinition;
import com.canoo.platform.server.spi.ModuleInitializationException;
import com.canoo.platform.server.spi.ServerCoreComponents;
import org.apiguardian.api.API;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.EnumSet;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "1.0.0-RC5", status = INTERNAL)
@ModuleDefinition(order = 10)
public class ServerTimingModule extends AbstractBaseModule {

    public static final String MODULE_NAME = "serverTimingModule";

    public static final String FILTER_NAME = "ServerTimingFilter";

    public static final String SERVER_TIMING_MODULE_ACTIVE_PROPERTY_NAME = "timingActive";

    @Override
    protected String getActivePropertyName() {
        return SERVER_TIMING_MODULE_ACTIVE_PROPERTY_NAME;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public void initialize(final ServerCoreComponents coreComponents) throws ModuleInitializationException {
        final ServletContext servletContext = coreComponents.getInstance(ServletContext.class);
        final PlatformConfiguration configuration = coreComponents.getConfiguration();

        final Filter filter = new ServerTimingFilter(true);
        final FilterRegistration.Dynamic createdFilter = servletContext.addFilter(FILTER_NAME, filter);
        createdFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
