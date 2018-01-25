package com.canoo.dp.impl.platform.data.mapping;

import com.canoo.dp.impl.platform.data.EntityWithId;

import java.io.Serializable;
import java.util.List;

public interface BeanMapper {

    <ID extends Serializable, B, E extends EntityWithId<ID>> B toBean(E entity, Class<B> beanClass);

    <ID extends Serializable, B, E extends EntityWithId<ID>> E toEntity(B bean, Class<E> entityClass);

    <ID extends Serializable, B, E extends EntityWithId<ID>> List<B> toBeanList(List<E> entityList, Class<B> beanClass);

    <ID extends Serializable, B, E extends EntityWithId<ID>> List<E> toEntityList(List<B> beanList, Class<E> entityClass);
}
