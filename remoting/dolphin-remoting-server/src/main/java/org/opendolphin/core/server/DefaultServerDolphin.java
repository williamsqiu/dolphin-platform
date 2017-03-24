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
package org.opendolphin.core.server;

import org.opendolphin.core.server.action.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * The default implementation of the Dolphin facade on the server side.
 * Responsibility: single access point for dolphin capabilities.
 * Collaborates with server model store and current response.
 * Threading model: confined to a single controller thread.
 */
public class DefaultServerDolphin implements ServerDolphin {

    private static final Logger LOG = Logger.getLogger(DefaultServerDolphin.class.getName());

    /**
     * the server model store is unique per user session
     */
    private final ServerModelStore serverModelStore;

    /**
     * the serverConnector is unique per user session
     */
    private final ServerConnector serverConnector;

    private AtomicBoolean initialized = new AtomicBoolean(false);

    public DefaultServerDolphin(ServerModelStore serverModelStore, ServerConnector serverConnector) {
        this.serverModelStore = serverModelStore;
        this.serverConnector = serverConnector;
        this.serverConnector.setServerModelStore(serverModelStore);
    }

    protected DefaultServerDolphin() {
        this(new ServerModelStore(), new ServerConnector());
    }

    @Override
    public ServerModelStore getModelStore() {
        return serverModelStore;
    }

    @Override
    public ServerConnector getServerConnector() {
        return serverConnector;
    }

    public void registerDefaultActions() {
        if (initialized.getAndSet(true)) {
            LOG.warning("attempt to initialize default actions more than once!");
            return;
        }
        register(new StoreValueChangeAction());
        register(new StoreAttributeAction());
        register(new CreatePresentationModelAction());
        register(new DeletePresentationModelAction());
    }

    public void register(DolphinServerAction action) {
        action.setServerDolphin(this);
        serverConnector.register(action);
    }

    /**
     * Create a presentation model on the server side, add it to the model store, and send a command to
     * the client, advising him to do the same.
     *
     * @throws IllegalArgumentException if a presentation model for this id already exists. No commands are sent in this case.
     */
    public ServerPresentationModel presentationModel(String id, String presentationModelType, DTO dto) {
        List<ServerAttribute> attributes = new ArrayList<ServerAttribute>();
        for (final Slot slot : dto.getSlots()) {
            final ServerAttribute result = new ServerAttribute(slot.getPropertyName(), slot.getValue(), slot.getQualifier());
            result.silently(new Runnable() {
                @Override
                public void run() {
                    result.setValue(slot.getValue());
                }

            });
            ((ArrayList<ServerAttribute>) attributes).add(result);
        }
        ServerPresentationModel model = new ServerPresentationModel(id, attributes, serverModelStore);
        model.setPresentationModelType(presentationModelType);
        getServerModelStore().add(model);
        return model;
    }

    public ServerModelStore getServerModelStore() {
        return serverModelStore;
    }
}
