package com.canoo.dp.impl.platform.server.metrics.module;

public interface MetricsConfigConstants {

    String MODULE_NAME = "MetricsModule";

    String METRICS_SERVLET_NAME = "metrics";

    String METRICS_SERVLET_FILTER_NAME = "metricsFilter";

    String METRICS_ACTIVE_PROPERTY = "metrics.active";

    boolean METRICS_ACTIVE_DEFAULT = true;

    String METRICS_ENDPOINT_PROPERTY = "metrics.endpoint";

    String METRICS_ENDPOINT_DEFAULT = "/metrics";

    String METRICS_NOOP_PROPERTY = "metrics.noop";

    boolean METRICS_NOOP_DEFAULT = false;

}
