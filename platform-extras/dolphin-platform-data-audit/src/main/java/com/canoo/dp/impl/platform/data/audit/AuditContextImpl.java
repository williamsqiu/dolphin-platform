package com.canoo.dp.impl.platform.data.audit;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.data.jpa.AbstractEntity;
import com.canoo.dp.impl.platform.data.jpa.event.PersistenceListener;
import com.canoo.dp.impl.platform.data.jpa.event.PersistenceListenerManager;
import com.canoo.platform.core.DolphinRuntimeException;
import com.canoo.platform.data.audit.AuditContext;
import com.canoo.platform.data.audit.Auditable;
import com.canoo.platform.data.audit.ChangeType;
import com.canoo.platform.data.audit.Snapshot;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.core.metamodel.object.SnapshotType;
import org.javers.repository.jql.QueryBuilder;
import org.javers.repository.sql.ConnectionProvider;
import org.javers.repository.sql.DialectName;
import org.javers.repository.sql.JaversSqlRepository;
import org.javers.repository.sql.SqlRepositoryBuilder;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class AuditContextImpl implements AuditContext {

    private final Javers javers;

    private final Supplier<String> currentUserSupplier;

    public AuditContextImpl(final ConnectionProvider connectionProvider, final String dialect, final String schema, final Supplier<String> currentUserSupplier) {
        this(connectionProvider, AuditUtils.convertToDialect(dialect), schema, currentUserSupplier);
    }

    public AuditContextImpl(final ConnectionProvider connectionProvider, final DialectName dialect, final String schema, final Supplier<String> currentUserSupplier) {
        Assert.requireNonNull(connectionProvider, "connectionProvider");
        Assert.requireNonNull(dialect, "dialect");
        Assert.requireNonNull(schema, "schema");
        final JaversSqlRepository repository = SqlRepositoryBuilder.sqlRepository()
                .withConnectionProvider(connectionProvider)
                .withDialect(dialect)
                .withSchema(schema)
                .build();
        this.javers = JaversBuilder.javers()
                .registerJaversRepository(repository)
                .build();

        this.currentUserSupplier = Assert.requireNonNull(currentUserSupplier, "currentUserSupplier");

        PersistenceListenerManager.getInstance().addListener(new PersistenceListener() {
            @Override
            public void onEntityPersisted(final AbstractEntity entity) {
                if (isAuditable(entity)) {
                    javers.commit(getCurrentUser(), entity);
                }
            }

            @Override
            public void onEntityRemoved(final AbstractEntity entity) {
                if (isAuditable(entity)) {
                    javers.commitShallowDelete(getCurrentUser(), entity);
                }
            }

            @Override
            public void onEntityUpdated(final AbstractEntity entity) {
                if (isAuditable(entity)) {
                    javers.commit(getCurrentUser(), entity);
                }
            }
        });
    }

    public <T extends AbstractEntity> boolean isAuditable(final T entity) {
        Assert.requireNonNull(entity, "entity");
        if (entity.getClass().isAnnotationPresent(Auditable.class)) {
            return true;
        }
        return false;
    }

    @Override
    public <T extends AbstractEntity> List<Snapshot> getChanges(final T entity) {
        return null;
    }

    @Override
    public <T extends AbstractEntity> List<Snapshot> getChanges(final T entity, final int limit) {
        return null;
    }

    private String getCurrentUser() {
        return currentUserSupplier.get();
    }

    private <T extends AbstractEntity> List<CdoSnapshot> getJaversChanges(final T entity, final int limit) {
        Assert.requireNonNull(entity, "entity");
        if (isAuditable(entity)) {
            final QueryBuilder builder = QueryBuilder.byInstanceId(entity.getId(), entity.getClass());
            if (limit > 0) {
                builder.limit(limit);
            }
            return javers.findSnapshots(builder.build());
        } else {
            throw new DolphinRuntimeException("Entity not auditable");
        }
    }

    private Snapshot convert(final CdoSnapshot javersSnapshot) {
        Assert.requireNonNull(javersSnapshot, "javersSnapshot");
        final long version = javersSnapshot.getVersion();
        final ChangeType type = Optional.ofNullable(javersSnapshot.getType()).map(t -> {
            if (t.equals(SnapshotType.INITIAL)) {
                return ChangeType.CREATED;
            }
            if (t.equals(SnapshotType.TERMINAL)) {
                return ChangeType.DELETED;
            }
            return ChangeType.UPDATED;
        }).orElseThrow(() -> new IllegalStateException("Can not define change type for javers snapshot"));


        Assert.requireNonNull(javersSnapshot.getCommitMetadata(), "commitMetadata");
        final String author = javersSnapshot.getCommitMetadata().getAuthor();
        final ZonedDateTime commitDate = ZonedDateTime.of(javersSnapshot.getCommitMetadata().getCommitDate(), ZoneId.systemDefault());
        final List<String> changedProperties = Collections.unmodifiableList(javersSnapshot.getChanged());
        final Map<String, Object> properties = new HashMap<>();
        javersSnapshot.getState().forEachProperty((n, v) -> properties.put(n, v));

        return new Snapshot() {

            @Override
            public long getVersion() {
                return version;
            }

            @Override
            public String getAuthor() {
                return author;
            }

            @Override
            public ZonedDateTime getCommitDate() {
                return commitDate;
            }

            @Override
            public ChangeType getChangeType() {
                return type;
            }

            @Override
            public <V> V getValue(final String name) {
                Assert.requireNonBlank(name, "name");
                if(properties.containsKey(name)) {
                    return (V) properties.get(name);
                } else {
                    throw new IllegalArgumentException("No property with name '" + name + "' found!");
                }
            }

            @Override
            public List<String> getChangedProperties() {
                return changedProperties;
            }
        };
    }
}
