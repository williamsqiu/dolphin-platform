package com.canoo.dp.impl.platform.data.mapping;

import com.canoo.dp.impl.platform.data.EntityWithId;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public interface BeanMapper {

    <ID extends Serializable, B, E extends EntityWithId<ID>> E toEntity(B bean, Class<E> entityClass);

    default <ID extends Serializable, B, E extends EntityWithId<ID>> List<E> toEntityList(List<B> beanList, Class<E> entityClass) {
        return beanList.stream()
                .map(b -> toEntity(b, entityClass))
                .collect(Collectors.toList());
    }

    <ID extends Serializable, B, E extends EntityWithId<ID>> B updateBean(E entity, B bean, Class<B> beanClass);

}
