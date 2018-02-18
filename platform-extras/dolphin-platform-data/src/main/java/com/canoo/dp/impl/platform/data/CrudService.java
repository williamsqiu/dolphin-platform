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
package com.canoo.dp.impl.platform.data;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

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

    Optional<T> byId(ID id);

    Class<T> getDataClass();

    /**
     * Persists the given entity
     * @param toSave the entity that should be persisted
     */
    T save(T toSave);

    /**
     * removes the given entity from the persistence
     * @param toDelete the entity that should be removed
     */
    void delete(T toDelete);

}
