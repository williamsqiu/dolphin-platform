package com.canoo.dp.impl.platform.metrics;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.context.Context;
import io.micrometer.core.instrument.Tag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class TagUtil {

    private TagUtil() {
    }

    public static List<Tag> convertTags(final Context... contexts) {
        final List<Context> contextsList = Arrays.asList(contexts);
        return convertTags(new HashSet<>(contextsList));
    }

    public static List<Tag> convertTags(final Set<Context> contexts) {
        Assert.requireNonNull(contexts, "contexts");
        return contexts.stream()
                .map(t -> Tag.of(t.getType(), t.getValue()))
                .collect(Collectors.toList());
    }
}
