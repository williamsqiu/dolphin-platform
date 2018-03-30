package com.canoo.dp.impl.platform.metrics;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.context.Context;
import io.micrometer.core.instrument.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class TagUtil {

    private TagUtil() {
    }

    public static List<Tag> convertTags(final Context... tags) {
        return convertTags(Arrays.asList(tags));
    }

    public static List<io.micrometer.core.instrument.Tag> convertTags(final List<Context> tags) {
        Assert.requireNonNull(tags, "tags");
        return tags.stream()
                .map(t -> io.micrometer.core.instrument.Tag.of(t.getType(), t.getValue()))
                .collect(Collectors.toList());
    }
}
