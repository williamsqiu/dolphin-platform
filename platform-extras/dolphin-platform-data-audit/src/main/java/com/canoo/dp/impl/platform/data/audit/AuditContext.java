package com.canoo.dp.impl.platform.data.audit;

import com.canoo.dp.impl.platform.data.jpa.AbstractEntity;
import com.canoo.dp.impl.platform.data.jpa.event.PersistenceListener;
import com.canoo.dp.impl.platform.data.jpa.event.PersistenceListenerManager;
import com.canoo.platform.core.DolphinRuntimeException;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.javers.repository.sql.ConnectionProvider;
import org.javers.repository.sql.DialectName;
import org.javers.repository.sql.JaversSqlRepository;
import org.javers.repository.sql.SqlRepositoryBuilder;

import java.util.List;

public class AuditContext {

    private final Javers javers;

    public AuditContext(final ConnectionProvider connectionProvider, final String dialect, final String schema) {
        this(connectionProvider, convertToDialect(dialect), schema);
    }

    public AuditContext(final ConnectionProvider connectionProvider, final DialectName dialect, final String schema) {
        final JaversSqlRepository repository = SqlRepositoryBuilder.sqlRepository()
                .withConnectionProvider(connectionProvider)
                .withDialect(dialect)
                .withSchema(schema)
                .build();
        this.javers = JaversBuilder.javers()
                .registerJaversRepository(repository)
                .build();

        PersistenceListenerManager.getInstance().addListener(new PersistenceListener() {
            @Override
            public void onEntityPersisted(final AbstractEntity entity) {
                if(isAuditable(entity)) {
                    javers.commit(getAuthor(), entity);
                }
            }

            @Override
            public void onEntityRemoved(final AbstractEntity entity) {
                if(isAuditable(entity)) {
                    javers.commitShallowDelete(getAuthor(), entity);
                }
            }

            @Override
            public void onEntityUpdated(final AbstractEntity entity) {
                if(isAuditable(entity)) {
                    javers.commit(getAuthor(), entity);
                }
            }
        });
    }

    private static DialectName convertToDialect(final String dialectName) {
        if(dialectName.equals("h2")) {
            return DialectName.H2;
        }
        if(dialectName.equals("postgres")) {
            return DialectName.POSTGRES;
        }
        if(dialectName.equals("mysql")) {
            return DialectName.MYSQL;
        }
        throw new DolphinRuntimeException("Dialect not supported");
    }

    private <T extends  AbstractEntity> boolean isAuditable(final T entity) {
        if(entity.getClass().isAnnotationPresent(Auditable.class)) {
            return true;
        }
        return false;
    }

    private String getAuthor() {
        return "unknown";
    }

    public <T extends  AbstractEntity> List<CdoSnapshot> getChanges(final T entity) {
        if(isAuditable(entity)) {
            return javers.findSnapshots(QueryBuilder.byInstanceId(entity.getId(), entity.getClass()).build());
        } else {
            throw new DolphinRuntimeException("Entity not auditable");
        }
    }
}
