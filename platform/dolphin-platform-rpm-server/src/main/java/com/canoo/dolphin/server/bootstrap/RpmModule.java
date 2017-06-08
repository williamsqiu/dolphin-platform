/*
 * Copyright 2015-2017 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dolphin.server.bootstrap;

import com.canoo.dolphin.server.config.RemotingConfiguration;
import com.canoo.dolphin.server.context.DefaultDolphinContextFactory;
import com.canoo.dolphin.server.context.DolphinContextCommunicationHandler;
import com.canoo.dolphin.server.context.DolphinContextFactory;
import com.canoo.dolphin.server.controller.ControllerValidationException;
import com.canoo.dolphin.server.event.DolphinEventBus;
import com.canoo.dolphin.server.event.impl.AbstractEventBus;
import com.canoo.dolphin.server.event.impl.EventBusProvider;
import com.canoo.dolphin.server.servlet.DolphinPlatformServlet;
import com.canoo.impl.server.beans.ManagedBeanFactory;
import com.canoo.impl.server.bootstrap.ServerCoreComponents;
import com.canoo.impl.server.bootstrap.modules.ClientSessionModule;
import com.canoo.impl.server.client.ClientSessionProvider;
import com.canoo.impl.server.config.PlatformConfiguration;
import com.canoo.impl.server.scanner.ClasspathScanner;
import com.canoo.platform.server.spi.ModuleDefinition;
import com.canoo.platform.server.spi.ModuleInitializationException;
import com.canoo.platform.server.spi.ServerModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import static com.canoo.dolphin.server.servlet.ServletConstants.DOLPHIN_SERVLET_NAME;

@ModuleDefinition(value = "RPM", order = 101)
public class RpmModule implements ServerModule {

    private static final Logger LOG = LoggerFactory.getLogger(RpmModule.class);

    @Override
    public List<String> getModuleDependencies() {
        return Collections.singletonList(ClientSessionModule.CLIENT_SESSION_MODULE);
    }

    @Override
    public boolean shouldBoot(PlatformConfiguration configuration) {
        return false;
    }

    @Override
    public void initialize(ServerCoreComponents coreComponents) throws ModuleInitializationException {
        LOG.info("Starting Dolphin Platform");
        try{
            final ServletContext servletContext = coreComponents.getServletContext();
            final ClasspathScanner classpathScanner = coreComponents.getClasspathScanner();
            final ManagedBeanFactory beanFactory = coreComponents.getManagedBeanFactory();
            final RemotingConfiguration configuration = new RemotingConfiguration(coreComponents.getConfiguration());
            final ClientSessionProvider sessionProvider = coreComponents.getInstance(ClientSessionProvider.class);
            final DolphinContextFactory dolphinContextFactory = new DefaultDolphinContextFactory(configuration, sessionProvider, beanFactory, classpathScanner);
            final DolphinContextCommunicationHandler communicationHandler = new DolphinContextCommunicationHandler(sessionProvider, dolphinContextFactory);
            servletContext.addServlet(DOLPHIN_SERVLET_NAME, new DolphinPlatformServlet(communicationHandler)).addMapping(configuration.getDolphinPlatformServletMapping());
            LOG.debug("Dolphin Platform initialized under context \"" + servletContext.getContextPath() + "\"");
            LOG.debug("Dolphin Platform endpoint defined as " + configuration.getDolphinPlatformServletMapping());


            Iterator<EventBusProvider> iterator = ServiceLoader.load(EventBusProvider.class).iterator();
            boolean providerFound = false;
            while (iterator.hasNext()) {
                EventBusProvider provider = iterator.next();
                if (configuration.getEventbusType().equals(provider.getType())) {
                    if(providerFound) {
                        throw new IllegalStateException("More than 1 event bus provider found");
                    }
                    LOG.debug("Using event bus of type {} with provider class {}", provider.getType(), provider.getClass());
                    providerFound = true;
                    DolphinEventBus eventBus = provider.create(configuration);
                    if(eventBus instanceof AbstractEventBus) {
                        ((AbstractEventBus) eventBus).init(sessionProvider, null);
                    }
                    coreComponents.provideInstance(DolphinEventBus.class, eventBus);
                }
            }


        }catch (ControllerValidationException cve){
            throw new ModuleInitializationException("Can not start Remote Presentation Model support based on bad controller definition", cve);
        }
    }

    public static DolphinEventBus createEventBus(RemotingConfiguration configuration) {
        Iterator<EventBusProvider> iterator = ServiceLoader.load(EventBusProvider.class).iterator();
        while (iterator.hasNext()) {
            EventBusProvider provider = iterator.next();
            if (configuration.getEventbusType().equals(provider.getType())) {
                LOG.debug("Using event bus of type {} with provider class {}", provider.getType(), provider.getClass());
                return provider.create(configuration);
            }
        }
        throw new IllegalArgumentException("No event bus provider of type " + configuration.getEventbusType() + " found!");
    }

}
