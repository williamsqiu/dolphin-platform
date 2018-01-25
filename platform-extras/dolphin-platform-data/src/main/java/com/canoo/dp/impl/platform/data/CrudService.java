package com.canoo.dp.impl.platform.data;

import java.io.Serializable;
import java.util.List;

/**
 * Default Interface for CRUD-Services
 * @param <T> Type of the entity
 * @param <ID> Type of the technical id of the entity
 */
public interface CrudService<ID extends Serializable, T extends EntityWithId<ID>> {

    T createNewInstance();

    /**
     * Return all entities that are currently persited
     * @return all entities that are currently persited
     */
    List<T> findAll();

    /**
     * Return the persisted entity with the given id
     * @param id the id
     * @return the persisted entity with the given id
     */
    T findById(ID id);

    /**
     * Persists the given entity
     * @param toSave the entity that should be persisted
     */
    void save(T toSave);

    /**
     * removes the given entity from the persistence
     * @param toDelete the entity that should be removed
     */
    void delete(T toDelete);

}
