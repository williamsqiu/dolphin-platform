package com.canoo.platform.data.audit;

import com.canoo.dp.impl.platform.data.jpa.AbstractEntity;

import java.util.List;

public interface AuditContext {

    <T extends AbstractEntity> boolean isAuditable(T entity);

    <T extends  AbstractEntity> List<Snapshot> getChanges(T entity);

    <T extends  AbstractEntity> List<Snapshot> getChanges(T entity, int limit);
}
