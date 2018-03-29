package com.canoo.dp.impl.platform.server.metrics;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.server.metrics.noop.NoopMeterRegistry;
import com.canoo.platform.metrics.Metrics;
import com.canoo.platform.metrics.Tag;
import com.canoo.platform.metrics.types.Counter;
import com.canoo.platform.metrics.types.Gauge;
import com.canoo.platform.metrics.types.Timer;
import com.google.common.util.concurrent.AtomicDouble;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MetricsImpl implements Metrics {

    private final static MetricsImpl INSTANCE = new MetricsImpl();

    private final Map<MeterIdentifier, Counter> noopCounterMap;

    private final Map<MeterIdentifier, Timer> noopTimerMap;

    private final Map<MeterIdentifier, Gauge> noopGaugeMap;

    private final AtomicReference<MeterRegistry> registry;

    public MetricsImpl() {
        registry = new AtomicReference<>(new NoopMeterRegistry());
        noopCounterMap = new HashMap<>();
        noopTimerMap = new HashMap<>();
        noopGaugeMap = new HashMap<>();
    }

    public void init(final MeterRegistry registry) {
        Assert.requireNonNull(registry, "registry");
        this.registry.set(registry);
    }

    private String[] convertTags(final Tag... tags) {
        return Arrays.asList(tags).stream()
                .map(t -> t.getKey())
                .collect(Collectors.toList())
                .toArray(new String[tags.length]);
    }

    @Override
    public Counter getOrCreateCounter(final String name, final Tag... tags) {
        final String[] tagNames = convertTags(tags);
        final io.micrometer.core.instrument.Counter counter = registry.get().counter(name, tagNames);
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

    @Override
    public Timer getOrCreateTimer(final String name, final Tag... tags) {
        final String[] tagNames = convertTags(tags);
        io.micrometer.core.instrument.Timer timer = registry.get().timer(name, tagNames);
        return new Timer() {
            @Override
            public void record(final long amount, final TimeUnit unit) {
                timer.record(amount, unit);
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public List<Tag> getTags() {
                return Collections.unmodifiableList(Arrays.asList(tags));
            }

            @Override
            public void close() throws Exception {
                timer.close();
            }
        };
    }

    @Override
    public Gauge getOrCreateGauge(final String name, final Tag... tags) {
        final String[] tagNames = convertTags(tags);
        final AtomicDouble internalValue = new AtomicDouble(0);

        io.micrometer.core.instrument.Gauge gauge = io.micrometer.core.instrument.Gauge
                .builder("name", internalValue, AtomicDouble::get)
                .tags(tagNames)
                .register(registry.get());

        return new Gauge() {
            @Override
            public void setValue(final double value) {
                internalValue.set(value);
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public List<Tag> getTags() {
                return Collections.unmodifiableList(Arrays.asList(tags));
            }

            @Override
            public void close() throws Exception {

            }
        };
    }

    public static MetricsImpl getInstance() {
        return INSTANCE;
    }
}
