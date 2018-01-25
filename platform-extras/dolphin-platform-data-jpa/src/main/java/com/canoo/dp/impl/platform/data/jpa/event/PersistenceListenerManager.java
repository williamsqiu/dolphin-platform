package com.canoo.dp.impl.platform.data.jpa.event;

import com.canoo.dp.impl.platform.data.jpa.AbstractEntity;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Listener for all JPA events. Defined as a singleton. {@link PersistenceListener} instances can be registered as listener.
 */
public class PersistenceListenerManager {

    private final static PersistenceListenerManager instance = new PersistenceListenerManager();

    private final List<PersistenceListener> listeners;

    private PersistenceListenerManager() {
        listeners = new CopyOnWriteArrayList<>();
    }

    /**
     * Returns the singleton
     * @return the singleton
     */
    public static PersistenceListenerManager getInstance() {
        return instance;
    }

    /**
     * Adds a listener that will be notified for all JPA events
     * @param listener the listener
     */
    public void addListener(PersistenceListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener
     * @param listener the listener
     */
    public void removeListener(PersistenceListener listener) {
        listeners.remove(listener);
    }

    /**
     * Removes all listeners
     */
    public void clear() {
        listeners.clear();
    }

    public void firePersisted(AbstractEntity entity) {
        for(PersistenceListener listener : listeners) {
            listener.onEntityPersisted(entity);
        }
    }

    public void fireRemoved(AbstractEntity entity) {
        for(PersistenceListener listener : listeners) {
            listener.onEntityRemoved(entity);
        }
    }

    public void fireUpdated(AbstractEntity entity) {
        for(PersistenceListener listener : listeners) {
            listener.onEntityUpdated(entity);
        }
    }
}
