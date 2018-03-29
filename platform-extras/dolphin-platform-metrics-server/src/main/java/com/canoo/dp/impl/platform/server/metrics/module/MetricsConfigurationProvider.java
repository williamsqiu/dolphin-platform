package com.canoo.dp.impl.platform.server.metrics.module;

import com.canoo.dp.impl.server.bootstrap.SimpleConfigurationProvider;

import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_ENDPOINT_DEFAULT;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_ENDPOINT_PROPERTY;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_NOOP_DEFAULT;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_NOOP_PROPERTY;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_ACTIVE_DEFAULT;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_ACTIVE_PROPERTY;

public class MetricsConfigurationProvider extends SimpleConfigurationProvider {

    public MetricsConfigurationProvider() {
        addBoolean(METRICS_ACTIVE_PROPERTY, METRICS_ACTIVE_DEFAULT);
        addBoolean(METRICS_NOOP_PROPERTY, METRICS_NOOP_DEFAULT);
        addString(METRICS_ENDPOINT_PROPERTY, METRICS_ENDPOINT_DEFAULT);
    }
}
