package com.canoo.dp.impl.platform.metrics;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.context.ContextImpl;
import com.canoo.platform.core.functional.Subscription;
import com.canoo.platform.metrics.Metrics;
import com.canoo.platform.core.context.Context;
import com.canoo.platform.metrics.types.Counter;
import com.canoo.platform.metrics.types.Gauge;
import com.canoo.platform.metrics.types.Timer;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


public class MetricsImpl implements Metrics {

    private final static Logger LOG = LoggerFactory.getLogger(MetricsImpl.class);

    private final static MetricsImpl INSTANCE = new MetricsImpl();

    private final AtomicReference<MeterRegistry> registry;

    private final List<Context> globalTags;

    private final ThreadLocal<List<Context>> contextTags;

    private MetricsImpl() {
        registry = new AtomicReference<>(new NoopMeterRegistry());
        this.globalTags = new CopyOnWriteArrayList<>();
        this.contextTags = new ThreadLocal<>();
    }

    public Subscription addGlobalContext(final Context tag) {
        Assert.requireNonNull(tag, "tag");
        globalTags.add(tag);
        return () -> globalTags.remove(tag);
    }

    public Subscription addLocalContext(final Context tag) {
        Assert.requireNonNull(tag, "tag");
        final List<Context> list = Optional.ofNullable(contextTags.get()).orElse(new CopyOnWriteArrayList<>());
        contextTags.set(list);
        list.add(tag);
        return () -> list.remove(tag);
    }

    public void init(final MeterRegistry registry) {
        Assert.requireNonNull(registry, "registry");
        this.registry.set(registry);
    }


    public List<Context> getGlobalTags() {
        return globalTags;
    }

    public List<Context> getContextTags() {
        return Optional.ofNullable(contextTags.get())
                .orElse(Collections.emptyList());
    }

    @Override
    public Counter getOrCreateCounter(final String name, final Context... tags) {
        final List<io.micrometer.core.instrument.Tag> tagList = new ArrayList<>();
        tagList.addAll(TagUtil.convertTags(tags));
        tagList.addAll(TagUtil.convertTags(globalTags));
        Optional.ofNullable(contextTags.get()).ifPresent(l -> tagList.addAll(TagUtil.convertTags(l)));
        final io.micrometer.core.instrument.Counter counter = registry.get()
                .counter(name, tagList);
        return new Counter() {
            @Override
            public void increment(final long amount) {
                if(amount < 0) {
                    LOG.warn("Counter metric can not be incremented with negative value!");
                }
                counter.increment(amount);
            }

            @Override
            public String getName() {
                return counter.getId().getName();
            }

            @Override
            public List<Context> getContext() {
                return counter.getId().getTags()
                        .stream()
                        .map(t -> new ContextImpl(t.getKey(), t.getValue()))
                        .collect(Collectors.toList());
            }

            @Override
            public void close() throws Exception {
                counter.close();
            }
        };
    }

    @Override
    public Timer getOrCreateTimer(final String name, final Context... tags) {
        final List<io.micrometer.core.instrument.Tag> tagList = new ArrayList<>();
        tagList.addAll(TagUtil.convertTags(tags));
        tagList.addAll(TagUtil.convertTags(globalTags));
        Optional.ofNullable(contextTags.get()).ifPresent(l -> tagList.addAll(TagUtil.convertTags(l)));
        io.micrometer.core.instrument.Timer timer = registry.get().timer(name, tagList);
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
            public List<Context> getContext() {
                return Collections.unmodifiableList(Arrays.asList(tags));
            }

            @Override
            public void close() throws Exception {
                timer.close();
            }
        };
    }

    @Override
    public Gauge getOrCreateGauge(final String name, final Context... tags) {
        final List<io.micrometer.core.instrument.Tag> tagList = new ArrayList<>();
        tagList.addAll(TagUtil.convertTags(tags));
        tagList.addAll(TagUtil.convertTags(globalTags));
        Optional.ofNullable(contextTags.get()).ifPresent(l -> tagList.addAll(TagUtil.convertTags(l)));
        final AtomicReference<Double> internalValue = new AtomicReference<Double>(0d);

        io.micrometer.core.instrument.Gauge gauge = io.micrometer.core.instrument.Gauge
                .builder("name", internalValue, r -> Optional.ofNullable(r.get()).orElse(0d))
                .tags(tagList)
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
            public List<Context> getContext() {
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
