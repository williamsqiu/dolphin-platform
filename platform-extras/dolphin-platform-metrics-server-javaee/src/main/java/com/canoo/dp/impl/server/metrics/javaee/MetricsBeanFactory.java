package com.canoo.dp.impl.server.metrics.javaee;

import com.canoo.dp.impl.platform.metrics.MetricsImpl;
import com.canoo.platform.metrics.Metrics;
import org.apiguardian.api.API;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.19.0", status = INTERNAL)
@ApplicationScoped
public class MetricsBeanFactory {

    @Produces
    @ApplicationScoped
    public Metrics createDolphinSession() {
        return MetricsImpl.getInstance();
    }
}
