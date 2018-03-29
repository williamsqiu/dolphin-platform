package com.canoo.dp.impl.platform.server.metrics.util;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.server.metrics.TagImpl;
import com.canoo.platform.metrics.Tag;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TagUtil {

    private TagUtil() {}

    public static List<io.micrometer.core.instrument.Tag> convertTags(final Tag... tags) {
        return convertTags(Arrays.asList(tags));
    }

    public static List<io.micrometer.core.instrument.Tag> convertTags(final List<Tag> tags) {
        Assert.requireNonNull(tags, "tags");
        return tags.stream()
                .map(t -> io.micrometer.core.instrument.Tag.of(t.getKey(), t.getValue()))
                .collect(Collectors.toList());
    }

    public static Tag createPortTag(final HttpServletRequest request) {
        Assert.requireNonNull(request, "request");
        return new TagImpl("port", String.valueOf(request.getServerPort()));
    }

    public static Tag createMethodTag(final HttpServletRequest request) {
        Assert.requireNonNull(request, "request");
        final String method = Optional.ofNullable(request.getMethod()).orElse("UNKNOWN");
        return new TagImpl("method", method.isEmpty() ? "UNKNOWN" : method);
    }

    public static Tag createUriTag(final HttpServletRequest request) {
        Assert.requireNonNull(request, "request");
        final String uri = Optional.ofNullable(request.getRequestURI()).orElse("/");
        return new TagImpl("uri", uri.isEmpty() ? "root" : uri);
    }

    public static Tag createContextPathTag(final HttpServletRequest request) {
        Assert.requireNonNull(request, "request");
        final String path = Optional.ofNullable(request.getContextPath()).orElse("/");
        return new TagImpl("contextPath", path.isEmpty() ? "" : path);
    }

}
