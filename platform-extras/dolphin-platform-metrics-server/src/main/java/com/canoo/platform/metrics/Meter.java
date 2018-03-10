package com.canoo.platform.metrics;

import java.util.List;

public interface Meter extends AutoCloseable {

    String getName();

    List<MeterTag> getTags();
}
