package com.canoo.dp.impl.platform.data;

import java.io.Serializable;

/**
 * Basic interface for all entity types that are defined by an id
 * @param <T> type of the id
 */
public interface EntityWithId<T extends Serializable> extends Serializable {

    /**
     * Returns the id
     * @return the id
     */
    T getId();

    /**
     * Sets the id of the entity
     * @param id
     */
    void setId(T id);
}
