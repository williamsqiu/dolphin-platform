package com.canoo.platform.metrics;

import com.canoo.platform.core.context.Context;

import java.util.List;

public interface Metric extends AutoCloseable {

    String getName();

    List<Context> getContext();
}
