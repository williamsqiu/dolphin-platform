package com.canoo.dp.impl.platform.server.metrics.module;

import com.canoo.dp.impl.platform.server.metrics.MetricsImpl;
import com.canoo.platform.core.PlatformConfiguration;
import com.canoo.platform.server.spi.AbstractBaseModule;
import com.canoo.platform.server.spi.ModuleDefinition;
import com.canoo.platform.server.spi.ServerCoreComponents;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.apiguardian.api.API;

import javax.servlet.ServletContext;

import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_ACTIVE_PROPERTY;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_ENDPOINT_PROPERTY;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_NOOP_PROPERTY;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_SERVLET_NAME;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.MODULE_NAME;
import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "1.0.0", status = INTERNAL)
@ModuleDefinition
public class MetricsModule extends AbstractBaseModule {


    @Override
    protected String getActivePropertyName() {
        return METRICS_ACTIVE_PROPERTY;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public void initialize(final ServerCoreComponents coreComponents) {
        final PlatformConfiguration configuration = coreComponents.getConfiguration();
        final ServletContext servletContext = coreComponents.getInstance(ServletContext.class);

        if(!configuration.getBooleanProperty(METRICS_NOOP_PROPERTY, true)) {
            final PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
            new ClassLoaderMetrics().bindTo(prometheusRegistry);
            new JvmMemoryMetrics().bindTo(prometheusRegistry);
            new JvmGcMetrics().bindTo(prometheusRegistry);
            new ProcessorMetrics().bindTo(prometheusRegistry);
            new JvmThreadMetrics().bindTo(prometheusRegistry);

            servletContext.addServlet(METRICS_SERVLET_NAME, new MetricsServlet(prometheusRegistry))
                    .addMapping(configuration.getProperty(METRICS_ENDPOINT_PROPERTY));
            MetricsImpl.getInstance().init(prometheusRegistry);
        }
    }
}
