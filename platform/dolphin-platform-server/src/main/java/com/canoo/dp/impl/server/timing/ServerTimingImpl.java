package com.canoo.dp.impl.server.timing;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.server.timing.Metric;
import com.canoo.platform.server.timing.ServerTiming;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.canoo.dp.impl.platform.core.http.HttpHeaderConstants.SERVER_TIMING_HEADER;
import static com.canoo.dp.impl.platform.core.http.HttpHeaderConstants.SERVER_TIMING_HEADER_DESC;
import static com.canoo.dp.impl.platform.core.http.HttpHeaderConstants.SERVER_TIMING_HEADER_DUR;

public class ServerTimingImpl implements ServerTiming {

    private List<Metric> metrics = new ArrayList<>();

    public void clear() {
        metrics.clear();
    }

    public void dump(final HttpServletResponse response) {
        Assert.requireNonNull(response, "response");
        //Sample:   serverTiming: 'A;dur=2521.46147;desc="/users/me",B;dur=102.022688;desc="getUser"',

        final String headerName = SERVER_TIMING_HEADER;
        final String content = metrics.stream().map(m -> convert(m)).reduce("", (a,b) -> a + "," + b);

        if(content.length() > 0) {
            response.addHeader(headerName, content.substring(1));
        }

        clear();
    }

    private String convert(final Metric metric) {
        Assert.requireNonNull(metric, "metric");
        final Duration duration = metric.getDuration();
        final String description = metric.getDescription();

        final String durPart = Optional.ofNullable(duration)
                .map(d -> ";" + SERVER_TIMING_HEADER_DUR + (Math.max(1.0, d.toNanos()) / 1000000.0))
                .orElse("");
        final String descPart = Optional.ofNullable(description)
                .map(d -> ";" + SERVER_TIMING_HEADER_DESC + "\"" + d + "\"")
                .orElse("");

        return metric.getName() + durPart + descPart;
    }

    @Override
    public Metric start(final String name, final String description) {
        final Metric metric = new MetricImpl(name, description);
        metrics.add(metric);
        return metric;
    }
}
