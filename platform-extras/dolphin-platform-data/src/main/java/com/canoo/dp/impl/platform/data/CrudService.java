package com.canoo.dp.impl.platform.data;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Default Interface for CRUD-Services
 * @param <T> Type of the entity
 * @param <ID> Type of the technical id of the entity
 */
public interface CrudService<ID extends Serializable, E extends EntityWithId<ID>> {

    E createNewInstance();

    /**
     * Return all entities that are currently persited
     * @return all entities that are currently persited
     */
    List<E> findAll();

    /**
     * Return the persisted entity with the given id
     * @param id the id
     * @return the persisted entity with the given id
     */
    E findById(ID id);

    Optional<E> byId(ID id);

    Class<E> getDataClass();

    E reset(E entity);

    /**
     * Persists the given entity
     * @param toSave the entity that should be persisted
     */
    E save(E toSave);

    /**
     * removes the given entity from the persistence
     * @param toDelete the entity that should be removed
     */
    void delete(E toDelete);

}
