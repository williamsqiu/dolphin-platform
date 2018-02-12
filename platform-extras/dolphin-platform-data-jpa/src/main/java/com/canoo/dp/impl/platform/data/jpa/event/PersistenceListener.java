package com.canoo.dp.impl.platform.data.jpa.event;

import com.canoo.dp.impl.platform.data.jpa.AbstractEntity;

/**
 * Listener for JPA events that can be registered as a lister to the {@link PersistenceListenerManager}
 */
public interface PersistenceListener {

    /**
     * Will be fired if an entity was persited
     * @param entity the persisted entity
     */
    void onEntityPersisted(AbstractEntity entity);

    /**
     * Will be fired if an entity was removed
     * @param entity the removed entity
     */
    void onEntityRemoved(AbstractEntity entity);

    /**
     * Will be fired if an entity was updated
     * @param entity the updated entity
     */
    void onEntityUpdated(AbstractEntity entity);

}
