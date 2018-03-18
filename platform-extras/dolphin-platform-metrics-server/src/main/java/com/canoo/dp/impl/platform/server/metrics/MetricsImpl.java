package com.canoo.dp.impl.platform.server.metrics;

import com.canoo.dp.impl.platform.server.metrics.noop.NoopCounter;
import com.canoo.dp.impl.platform.server.metrics.noop.NoopGauge;
import com.canoo.dp.impl.platform.server.metrics.noop.NoopTimer;
import com.canoo.platform.metrics.Metrics;
import com.canoo.platform.metrics.Tag;
import com.canoo.platform.metrics.types.Counter;
import com.canoo.platform.metrics.types.Gauge;
import com.canoo.platform.metrics.types.Timer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class MetricsImpl implements Metrics {

    private final AtomicBoolean noop;

    private final Map<MeterIdentifier, Counter> noopCounterMap;

    private final Map<MeterIdentifier, Timer> noopTimerMap;

    private final Map<MeterIdentifier, Gauge> noopGaugeMap;

    private final MeterRegistry registry;

    public MetricsImpl(final boolean noop) {
        this.noop = new AtomicBoolean(noop);
        noopCounterMap = new HashMap<>();
        noopTimerMap = new HashMap<>();
        noopGaugeMap = new HashMap<>();
        registry = new SimpleMeterRegistry();
    }

    @Override
    public Counter getOrCreateCounter(final String name, final Tag... tags) {
        if (noop.get()) {
            final MeterIdentifier identifier = new MeterIdentifier(name, tags);
            return noopCounterMap.computeIfAbsent(identifier, i -> new NoopCounter(i, () -> noopCounterMap.remove(i)));
        } else {
            final String[] tagNames = Arrays.asList(tags).stream().map(t -> t.getKey()).collect(Collectors.toList()).toArray(new String[tags.length]);
            final io.micrometer.core.instrument.Counter counter = registry.counter(name, tagNames);
            return new Counter() {
                @Override
                public void increment(final long amount) {
                    counter.increment(amount);
                }

                @Override
                public String getName() {
                    return counter.getId().getName();
                }

                @Override
                public List<Tag> getTags() {
                    return counter.getId().getTags()
                            .stream()
                            .map(t -> new TagImpl(t.getKey(), t.getValue()))
                            .collect(Collectors.toList());
                }

                @Override
                public void close() throws Exception {
                    counter.close();
                }
            };
        }
    }

    @Override
    public Timer getOrCreateTimer(final String name, final Tag... tags) {
        if (noop.get()) {
            final MeterIdentifier identifier = new MeterIdentifier(name, tags);
            return noopTimerMap.computeIfAbsent(identifier, i -> new NoopTimer(i, () -> noopCounterMap.remove(i)));
        } else {
            throw new RuntimeException("Not yet implemented");
        }
    }

    @Override
    public Gauge getOrCreateGauge(final String name, final Tag... tags) {
        if (noop.get()) {
            final MeterIdentifier identifier = new MeterIdentifier(name, tags);
            return noopGaugeMap.computeIfAbsent(identifier, i -> new NoopGauge(i, () -> noopCounterMap.remove(i)));
        } else {
            throw new RuntimeException("Not yet implemented");
        }
    }
}
