package com.canoo.dp.impl.platform.data.mapping;

import com.canoo.dp.impl.platform.data.EntityWithId;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public class BeanMapperImpl implements BeanMapper {

    private static final WeakHashMap<Object, Map<Class<?>, Serializable>> beanToIdMapper = new WeakHashMap<>();

    private synchronized <ID extends Serializable, B, E extends EntityWithId<ID>> ID getEntityIdForBean(final B bean, final Class<E> entityClass) {
        return (ID) Optional.ofNullable(beanToIdMapper.get(bean)).map(m -> m.get(entityClass)).orElse(null);
    }

    @Override
    public <ID extends Serializable, B, E extends EntityWithId<ID>> B toBean(final E entity) {
        return null;
    }

    @Override
    public <ID extends Serializable, B, E extends EntityWithId<ID>> E toEntity(final B bean) {
        return null;
    }

    @Override
    public <ID extends Serializable, B, E extends EntityWithId<ID>> List<B> toBeanList(final List<E> entityList) {
        return null;
    }

    @Override
    public <ID extends Serializable, B, E extends EntityWithId<ID>> List<E> toEntityList(final List<B> beanList) {
        return null;
    }
}
