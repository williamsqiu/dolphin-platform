package com.canoo.dp.impl.platform.data.mapping;

import com.canoo.dp.impl.platform.data.EntityWithId;

import java.io.Serializable;
import java.util.List;

public interface BeanMapper {

    <ID extends Serializable, B, E extends EntityWithId<ID>> B toBean(E entity);

    <ID extends Serializable, B, E extends EntityWithId<ID>> E toEntity(B bean);

    <ID extends Serializable, B, E extends EntityWithId<ID>> List<B> toBeanList(List<E> entityList);

    <ID extends Serializable, B, E extends EntityWithId<ID>> List<E> toEntityList(List<B> beanList);
}
