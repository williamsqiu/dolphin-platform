package com.canoo.impl.server.bootstrap.modules;

import com.canoo.impl.server.bootstrap.ServerCoreComponents;
import com.canoo.impl.server.config.PlatformConfiguration;
import com.canoo.impl.server.servlet.CrossSiteOriginFilter;
import com.canoo.platform.server.spi.ModuleDefinition;
import com.canoo.platform.server.spi.ModuleInitializationException;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.EnumSet;
import java.util.List;

@ModuleDefinition(CorsModule.CORS_MODULE)
public class CorsModule extends AbstractBaseModule {

    public static final String CORS_MODULE = "CorsModule";

    public static final String CORS_FILTER = "CorsFilter";

    public static final String CORS_MODULE_ACTIVE = "corsActive";

    @Override
    protected String getActivePropertyName() {
        return CORS_MODULE_ACTIVE;
    }

    @Override
    public void initialize(ServerCoreComponents coreComponents) throws ModuleInitializationException {
        final ServletContext servletContext = coreComponents.getServletContext();
        final PlatformConfiguration configuration = coreComponents.getConfiguration();
        final List<String> endpointList = configuration.getCorsEndpoints();

        final String[] endpoints = endpointList.toArray(new String[endpointList.size()]);
        final CrossSiteOriginFilter filter = new CrossSiteOriginFilter(configuration);
        final FilterRegistration.Dynamic createdFilter = servletContext.addFilter(CORS_FILTER, filter);
        createdFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, endpoints);
    }
}
