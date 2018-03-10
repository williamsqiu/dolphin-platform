package com.canoo.dp.impl.platform.server.metrics;

import com.canoo.dp.impl.platform.server.metrics.noop.NoopCounter;
import com.canoo.dp.impl.platform.server.metrics.noop.NoopGauge;
import com.canoo.dp.impl.platform.server.metrics.noop.NoopTimer;
import com.canoo.platform.metrics.MeterTag;
import com.canoo.platform.metrics.Metrics;
import com.canoo.platform.metrics.types.Counter;
import com.canoo.platform.metrics.types.Gauge;
import com.canoo.platform.metrics.types.Timer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class MetricsImpl implements Metrics {

    private final AtomicBoolean noop;

    private Map<MeterIdentifier, Counter> noopCounterMap;

    private Map<MeterIdentifier, Timer> noopTimerMap;

    private Map<MeterIdentifier, Gauge> noopGaugeMap;


    public MetricsImpl(final boolean noop) {
        this.noop = new AtomicBoolean(noop);
        noopCounterMap = new HashMap<>();
        noopTimerMap = new HashMap<>();
        noopGaugeMap = new HashMap<>();
    }

    @Override
    public Counter getOrCreateCounter(final String name, final MeterTag... tags) {
        if(noop.get()) {
            final MeterIdentifier identifier = new MeterIdentifier(name, tags);
            return noopCounterMap.computeIfAbsent(identifier, i -> new NoopCounter(i, () -> noopCounterMap.remove(i)));
        } else {
            throw new RuntimeException("Not yet implemented");
        }
    }

    @Override
    public Timer getOrCreateTimer(final String name, final MeterTag... tags) {
        if(noop.get()) {
            final MeterIdentifier identifier = new MeterIdentifier(name, tags);
            return noopTimerMap.computeIfAbsent(identifier, i -> new NoopTimer(i, () -> noopCounterMap.remove(i)));
        } else {
            throw new RuntimeException("Not yet implemented");
        }
    }

    @Override
    public Gauge getOrCreateGauge(final String name, final MeterTag... tags) {
        if(noop.get()) {
            final MeterIdentifier identifier = new MeterIdentifier(name, tags);
            return noopGaugeMap.computeIfAbsent(identifier, i -> new NoopGauge(i, () -> noopCounterMap.remove(i)));
        } else {
            throw new RuntimeException("Not yet implemented");
        }
    }
}
