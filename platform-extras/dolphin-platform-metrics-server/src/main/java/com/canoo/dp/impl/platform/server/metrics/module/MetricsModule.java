package com.canoo.dp.impl.platform.server.metrics.module;

import com.canoo.dp.impl.platform.server.metrics.MetricsImpl;
import com.canoo.dp.impl.platform.server.metrics.TagImpl;
import com.canoo.dp.impl.platform.server.metrics.servlet.MetricsHttpSessionListener;
import com.canoo.dp.impl.platform.server.metrics.servlet.MetricsServlet;
import com.canoo.dp.impl.platform.server.metrics.servlet.RequestMetricsFilter;
import com.canoo.platform.core.PlatformConfiguration;
import com.canoo.platform.server.spi.AbstractBaseModule;
import com.canoo.platform.server.spi.ModuleDefinition;
import com.canoo.platform.server.spi.ServerCoreComponents;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.apiguardian.api.API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.net.InetAddress;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import static com.canoo.dp.impl.platform.core.PlatformConstants.APPLICATION_NAME_DEFAULT;
import static com.canoo.dp.impl.platform.core.PlatformConstants.APPLICATION_NAME_PROPERTY;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.APPLICATION_TAG_NAME;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.CANONICAL_HOST_NAME_TAG_NAME;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.HOST_ADDRESS_TAG_NAME;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.HOST_NAME_TAG_NAME;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_ACTIVE_PROPERTY;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_ENDPOINT_PROPERTY;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_NOOP_PROPERTY;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_SERVLET_FILTER_NAME;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.METRICS_SERVLET_NAME;
import static com.canoo.dp.impl.platform.server.metrics.module.MetricsConfigConstants.MODULE_NAME;
import static com.canoo.dp.impl.platform.server.metrics.util.TagUtil.convertTags;
import static com.canoo.dp.impl.server.servlet.ServletConstants.ALL_URL_MAPPING;
import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "1.0.0", status = INTERNAL)
@ModuleDefinition
public class MetricsModule extends AbstractBaseModule {

    private final static Logger LOG = LoggerFactory.getLogger(MetricsModule.class);

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
            MetricsImpl.getInstance().addGlobalTag(new TagImpl(APPLICATION_TAG_NAME, configuration.getProperty(APPLICATION_NAME_PROPERTY, APPLICATION_NAME_DEFAULT)));

            try {
                final InetAddress address = InetAddress.getLocalHost();
                MetricsImpl.getInstance().addGlobalTag(new TagImpl(HOST_NAME_TAG_NAME, address.getHostName()));
                MetricsImpl.getInstance().addGlobalTag(new TagImpl(CANONICAL_HOST_NAME_TAG_NAME, address.getCanonicalHostName()));
                MetricsImpl.getInstance().addGlobalTag(new TagImpl(HOST_ADDRESS_TAG_NAME, address.getHostAddress()));
            } catch (Exception e) {
                LOG.error("Can not define InetAddress for metrics!", e);
            }

            final PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

            final List<Tag> tagList = Collections.unmodifiableList(convertTags(MetricsImpl.getInstance().getGlobalTags()));

            new ClassLoaderMetrics(tagList).bindTo(prometheusRegistry);
            new JvmMemoryMetrics(tagList).bindTo(prometheusRegistry);
            new JvmGcMetrics(tagList).bindTo(prometheusRegistry);
            new ProcessorMetrics(tagList).bindTo(prometheusRegistry);
            new JvmThreadMetrics(tagList).bindTo(prometheusRegistry);

            servletContext.addFilter(METRICS_SERVLET_FILTER_NAME, new RequestMetricsFilter())
                    .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, ALL_URL_MAPPING);

            servletContext.addListener(new MetricsHttpSessionListener());

            servletContext.addServlet(METRICS_SERVLET_NAME, new MetricsServlet(prometheusRegistry))
                    .addMapping(configuration.getProperty(METRICS_ENDPOINT_PROPERTY));

            MetricsImpl.getInstance().init(prometheusRegistry);
        }
    }
}
