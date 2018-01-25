package com.canoo.dp.impl.platform.data.mapping;

import com.canoo.dp.impl.platform.data.EntityWithId;

import java.io.Serializable;

public interface BeanConverter<ID extends Serializable, B, E extends EntityWithId<ID>> {

    E enrichtEntityByBean(E entity, B bean);

    B enrichtBeanByEntity(B bean, E entity);

}
