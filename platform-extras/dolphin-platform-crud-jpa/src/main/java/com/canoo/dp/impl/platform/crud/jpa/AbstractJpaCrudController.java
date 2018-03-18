/*
 * Copyright 2015-2018 Canoo Engineering AG.
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
package com.canoo.dp.impl.platform.crud.jpa;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.crud.AbstractCrudController;
import com.canoo.dp.impl.platform.data.jpa.AbstractEntity;
import com.canoo.dp.impl.platform.data.jpa.SimpleCrudService;
import com.canoo.dp.impl.platform.data.jpa.event.PersistenceListener;
import com.canoo.dp.impl.platform.data.jpa.event.PersistenceListenerManager;
import com.canoo.dp.impl.platform.data.mapping.BeanConverter;
import com.canoo.platform.remoting.BeanManager;
import com.canoo.platform.remoting.server.ClientSessionExecutor;
import com.canoo.platform.remoting.server.RemotingContext;

import javax.annotation.PostConstruct;

public abstract class AbstractJpaCrudController<B, E extends AbstractEntity> extends AbstractCrudController<Long, B, E> {

    private final ClientSessionExecutor clientSessionExecutor;

    private final PersistenceListener persistenceListener;

    protected AbstractJpaCrudController(final Class modelClass, final Class entityClass, final BeanManager manager, final SimpleCrudService<E> crudService, final BeanConverter<Long, B, E> converter, final RemotingContext remotingContext) {
        super(modelClass, entityClass, manager, crudService, converter, Assert.requireNonNull(remotingContext, "remotingContext").getEventBus());
        this.clientSessionExecutor = remotingContext.createSessionExecutor();
        this.persistenceListener = new PersistenceListener() {
            @Override
            public void onEntityPersisted(final AbstractEntity entity) {}

            @Override
            public void onEntityRemoved(final AbstractEntity entity) {
                Assert.requireNonNull(entity, "entity");

                final E currentEntity = getEntity();
                if(currentEntity != null && currentEntity.equals(entity) && !remotingContext.isActive()) {
                    clientSessionExecutor.runLaterInClientSession(() -> onEntityRemovedConflict());
                }
            }

            @Override
            public void onEntityUpdated(final AbstractEntity entity) {
                Assert.requireNonNull(entity, "entity");

                final E currentEntity = getEntity();
                if(currentEntity != null && currentEntity.equals(entity) && !remotingContext.isActive()) {
                    clientSessionExecutor.runLaterInClientSession(() -> onEntityUpdatedConflict());
                }
            }
        };
        PersistenceListenerManager.getInstance().addListener(persistenceListener);
    }

    @PostConstruct
    protected void onDestroy() {
        super.onDestroy();
        PersistenceListenerManager.getInstance().removeListener(persistenceListener);
    }

    protected void onEntityRemovedConflict() {}

    protected void onEntityUpdatedConflict() {}
}
