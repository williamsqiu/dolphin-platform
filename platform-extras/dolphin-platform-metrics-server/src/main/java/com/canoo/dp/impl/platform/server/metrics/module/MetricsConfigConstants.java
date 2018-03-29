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

    boolean METRICS_NOOP_DEFAULT = true;

    String APPLICATION_TAG_NAME = "application";

    String HOST_NAME_TAG_NAME = "hostName";

    String CANONICAL_HOST_NAME_TAG_NAME = "canonicalHostName";

    String HOST_ADDRESS_TAG_NAME = "hostAddress";

}
