package com.canoo.dp.impl.platform.server.metrics.servlet;

import com.canoo.dp.impl.platform.metrics.MetricsImpl;
import com.canoo.dp.impl.server.context.ContextServerUtil;
import com.canoo.platform.core.context.Context;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RequestMetricsFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final long startTime = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } finally {
            final long timeInMs = System.currentTimeMillis() - startTime;
            final Context methodTag = ContextServerUtil.createMethodTag((HttpServletRequest) request);
            final Context uriTag = ContextServerUtil.createUriTag((HttpServletRequest) request);
            final Context contextPathTag = ContextServerUtil.createContextPathTag((HttpServletRequest) request);
            final Context portTag = ContextServerUtil.createPortTag((HttpServletRequest) request);
            MetricsImpl.getInstance()
                    .getOrCreateTimer("request", contextPathTag, uriTag, methodTag, portTag)
                    .record(timeInMs, TimeUnit.MILLISECONDS);
            MetricsImpl.getInstance()
                    .getOrCreateCounter("requestCounter", contextPathTag, uriTag, methodTag, portTag)
                    .increment();
        }
    }

    @Override
    public void destroy() {}
}
