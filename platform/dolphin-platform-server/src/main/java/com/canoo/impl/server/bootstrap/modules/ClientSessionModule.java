package com.canoo.impl.server.bootstrap.modules;

import com.canoo.dolphin.util.Assert;
import com.canoo.dolphin.util.Callback;
import com.canoo.impl.server.beans.ManagedBeanFactory;
import com.canoo.impl.server.bootstrap.ServerCoreComponents;
import com.canoo.impl.server.client.*;
import com.canoo.impl.server.config.PlatformConfiguration;
import com.canoo.impl.server.scanner.ClasspathScanner;
import com.canoo.platform.server.ServerListener;
import com.canoo.platform.server.client.ClientSession;
import com.canoo.platform.server.client.ClientSessionListener;
import com.canoo.platform.server.spi.ModuleDefinition;
import com.canoo.platform.server.spi.ModuleInitializationException;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@ModuleDefinition(ClientSessionModule.CLIENT_SESSION_MODULE)
public class ClientSessionModule extends AbstractBaseModule {

    public static final String CLIENT_SESSION_MODULE = "ClientSessionModule";

    public static final String CLIENT_SESSION_MODULE_ACTIVE = "clientSessionActive";

    public static final String DOLPHIN_CLIENT_ID_FILTER_NAME = "clientIdFilter";

    @Override
    protected String getActivePropertyName() {
        return CLIENT_SESSION_MODULE_ACTIVE;
    }

    @Override
    public void initialize(final ServerCoreComponents coreComponents) throws ModuleInitializationException {
        Assert.requireNonNull(coreComponents, "coreComponents");

        final ServletContext servletContext = coreComponents.getServletContext();
        final PlatformConfiguration configuration = coreComponents.getConfiguration();
        final ClasspathScanner classpathScanner = coreComponents.getClasspathScanner();
        final ManagedBeanFactory beanFactory = coreComponents.getManagedBeanFactory();

        final ClientSessionLifecycleHandlerImpl lifecycleHandler = new ClientSessionLifecycleHandlerImpl();
        coreComponents.provideInstance(ClientSessionLifecycleHandler.class, lifecycleHandler);
                coreComponents.provideInstance(ClientSessionProvider.class, new ClientSessionProvider() {
            @Override
            public ClientSession getCurrentDolphinSession() {
                return lifecycleHandler.getCurrentDolphinSession();
            }
        });


        final ClientSessionManager clientSessionManager = new ClientSessionManager(configuration, lifecycleHandler);

        final List<String> endpointList = configuration.getIdFilterUrlMappings();
        final String[] endpoints = endpointList.toArray(new String[endpointList.size()]);
        final ClientSessionFilter filter = new ClientSessionFilter(clientSessionManager);
        final FilterRegistration.Dynamic createdFilter = servletContext.addFilter(DOLPHIN_CLIENT_ID_FILTER_NAME, filter);
        createdFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, endpoints);

        final HttpSessionCleanerListener sessionCleaner = new HttpSessionCleanerListener(clientSessionManager);
        servletContext.addListener(sessionCleaner);

        final Set<Class<?>> listeners = classpathScanner.getTypesAnnotatedWith(ServerListener.class);
        for (final Class<?> listenerClass : listeners) {
            if (ClientSessionListener.class.isAssignableFrom(listenerClass)) {
                final ClientSessionListener listener = (ClientSessionListener) beanFactory.createDependendInstance(listenerClass);

                lifecycleHandler.addSessionDestroyedListener(new Callback<ClientSession>() {
                    @Override
                    public void call(ClientSession clientSession) {
                        listener.sessionDestroyed(clientSession);
                    }
                });
                lifecycleHandler.addSessionCreatedListener(new Callback<ClientSession>() {
                    @Override
                    public void call(ClientSession clientSession) {
                        listener.sessionCreated(clientSession);
                    }
                });
            }
        }



    }
}
