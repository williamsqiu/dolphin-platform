package com.canoo.dp.impl.server.security.spring;

import com.canoo.dp.impl.platform.server.metrics.MetricsImpl;
import com.canoo.platform.metrics.Metrics;
import org.apiguardian.api.API;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "1.0.0", status = INTERNAL)
@Configuration
public class MetricsBeanFactory {

    @Bean("metrics")
    @ApplicationScope
    public Metrics createMetrics() {
        return MetricsImpl.getInstance();
    }
}
