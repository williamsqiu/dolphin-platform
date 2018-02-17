package com.canoo.dp.impl.platform.data.audit;

import com.canoo.platform.core.DolphinRuntimeException;
import org.javers.repository.sql.DialectName;

public interface AuditUtils {

    String DB_H2 = "h2";

    String DB_POSTGRES = "postgres";

    String DB_MYSQL = "mysql";

    String DEFAULT_AUTHOR = "unknown";

    static DialectName convertToDialect(final String dialectName) {
        if(dialectName.equals(DB_H2)) {
            return DialectName.H2;
        }
        if(dialectName.equals(DB_POSTGRES)) {
            return DialectName.POSTGRES;
        }
        if(dialectName.equals(DB_MYSQL)) {
            return DialectName.MYSQL;
        }
        throw new DolphinRuntimeException("Dialect not supported");
    }

}
