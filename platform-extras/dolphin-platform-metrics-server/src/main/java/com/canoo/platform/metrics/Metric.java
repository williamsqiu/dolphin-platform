package com.canoo.platform.metrics;

import java.util.List;

public interface Metric extends AutoCloseable {

    String getName();

    List<Tag> getTags();
}
