package com.canoo.dp.impl.platform.server.metrics.servlet;

import com.canoo.dp.impl.platform.core.Assert;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MetricsServlet extends HttpServlet {

    private final static Logger LOG = LoggerFactory.getLogger(MetricsServlet.class);

    private final PrometheusMeterRegistry prometheusRegistry;

    public MetricsServlet(final PrometheusMeterRegistry prometheusRegistry) {
        this.prometheusRegistry = Assert.requireNonNull(prometheusRegistry, "prometheusRegistry");
    }

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Metrics servlet called");
        final String response = prometheusRegistry.scrape();
        resp.getOutputStream().write(response.getBytes());
    }
}
