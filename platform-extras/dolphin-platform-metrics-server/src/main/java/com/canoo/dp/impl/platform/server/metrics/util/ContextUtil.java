package com.canoo.dp.impl.platform.server.metrics.util;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.context.ContextImpl;
import com.canoo.platform.core.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContextUtil {

    private ContextUtil() {}

    public static List<io.micrometer.core.instrument.Tag> convertTags(final Context... tags) {
        return convertTags(Arrays.asList(tags));
    }

    public static List<io.micrometer.core.instrument.Tag> convertTags(final List<Context> tags) {
        Assert.requireNonNull(tags, "tags");
        return tags.stream()
                .map(t -> io.micrometer.core.instrument.Tag.of(t.getType(), t.getValue()))
                .collect(Collectors.toList());
    }

    public static Context createPortTag(final HttpServletRequest request) {
        Assert.requireNonNull(request, "request");
        return new ContextImpl("port", String.valueOf(request.getServerPort()));
    }

    public static Context createMethodTag(final HttpServletRequest request) {
        Assert.requireNonNull(request, "request");
        final String method = Optional.ofNullable(request.getMethod()).orElse("UNKNOWN");
        return new ContextImpl("method", method.isEmpty() ? "UNKNOWN" : method);
    }

    public static Context createUriTag(final HttpServletRequest request) {
        Assert.requireNonNull(request, "request");
        final String uri = Optional.ofNullable(request.getRequestURI()).orElse("/");
        return new ContextImpl("uri", uri.isEmpty() ? "root" : uri);
    }

    public static Context createContextPathTag(final HttpServletRequest request) {
        Assert.requireNonNull(request, "request");
        final String path = Optional.ofNullable(request.getContextPath()).orElse("/");
        return new ContextImpl("contextPath", path.isEmpty() ? "" : path);
    }

}
