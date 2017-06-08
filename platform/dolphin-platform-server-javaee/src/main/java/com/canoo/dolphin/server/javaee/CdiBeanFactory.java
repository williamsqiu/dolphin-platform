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
package com.canoo.dolphin.server.javaee;

import com.canoo.dolphin.BeanManager;
import com.canoo.dolphin.server.binding.PropertyBinder;
import com.canoo.dolphin.server.binding.impl.PropertyBinderImpl;
import com.canoo.dolphin.server.context.DolphinContext;
import com.canoo.dolphin.server.context.DolphinContextCommunicationHandler;
import com.canoo.dolphin.server.event.DolphinEventBus;
import com.canoo.dolphin.util.Assert;
import com.canoo.impl.server.bootstrap.PlatformBootstrap;
import com.canoo.impl.server.client.ClientSessionProvider;
import com.canoo.platform.server.client.ClientSession;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * Factory that provides all needed Dolphin Platform extensions as CDI beans.
 *
 * @author Hendrik Ebbers
 */
@ApplicationScoped
public class CdiBeanFactory {

    @Produces
    @ClientScoped
    public BeanManager createManager() {
        final ClientSessionProvider provider = PlatformBootstrap.getServerCoreComponents().getInstance(ClientSessionProvider.class);
        Assert.requireNonNull(provider, "provider");
        final DolphinContext context = DolphinContextCommunicationHandler.getContext(provider.getCurrentDolphinSession());
        Assert.requireNonNull(context, "context");
        return context.getBeanManager();
    }

    @Produces
    @ClientScoped
    public ClientSession createDolphinSession() {
        final ClientSessionProvider provider = PlatformBootstrap.getServerCoreComponents().getInstance(ClientSessionProvider.class);
        Assert.requireNonNull(provider, "provider");
        return provider.getCurrentDolphinSession();
    }

    @Produces
    @ApplicationScoped
    public DolphinEventBus createEventBus() {
        return PlatformBootstrap.getServerCoreComponents().getInstance(DolphinEventBus.class);
    }

    @Produces
    @ApplicationScoped
    public PropertyBinder createPropertyBinder() {
        return new PropertyBinderImpl();
    }
}
