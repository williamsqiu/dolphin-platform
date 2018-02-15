package com.canoo.dp.impl.server.servlet;

import com.canoo.dp.impl.server.timing.ServerTimingImpl;
import com.canoo.platform.server.timing.Metric;
import com.canoo.platform.server.timing.ServerTiming;
import org.apiguardian.api.API;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "1.0.0-RC5", status = INTERNAL)
public class ServerTimingFilter implements Filter {

    private final static ThreadLocal<ServerTimingImpl> timingLocal = new ThreadLocal<>();

    private final boolean addServerTiming;

    public ServerTimingFilter(final boolean addServerTiming) {
        this.addServerTiming = addServerTiming;
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final GenericResponseWrapper responseWrapper = new GenericResponseWrapper((HttpServletResponse) response);

        final ServerTimingImpl timing = new ServerTimingImpl();
        timingLocal.set(timing);
        try {
            final Metric totalMetric = timing.start("total", "total duration of the request");
            chain.doFilter(request, responseWrapper);
            totalMetric.stop();
        } finally {
            if(addServerTiming) {
                timing.dump((HttpServletResponse) response);
            } else {
                timing.clear();
            }
            timingLocal.set(null);
            responseWrapper.updateWrappedResponse();
        }
    }

    @Override
    public void destroy() {

    }


    public static ServerTiming getCurrentTiming() {
        return timingLocal.get();
    }
}
