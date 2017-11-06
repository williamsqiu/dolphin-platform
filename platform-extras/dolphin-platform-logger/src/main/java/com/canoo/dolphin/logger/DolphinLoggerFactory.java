package com.canoo.dolphin.logger;

import com.canoo.dolphin.logger.spi.DolphinLoggerBridge;
import com.canoo.dolphin.logger.spi.DolphinLoggerBridgeFactory;
import com.canoo.dp.impl.platform.core.Assert;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class DolphinLoggerFactory implements ILoggerFactory {

    private final CopyOnWriteArrayList<String> markers = new CopyOnWriteArrayList<>();

    private final AtomicBoolean configured = new AtomicBoolean(false);

    private final ConcurrentMap<String, DolphinLogger> loggerMap = new ConcurrentHashMap();

    private final ConcurrentMap<String, Level> loggerLevelMap = new ConcurrentHashMap();

    private final List<DolphinLoggerBridge> bridges = new CopyOnWriteArrayList<>();

    private DolphinLoggerConfiguration configuration;

    @Override
    public synchronized Logger getLogger(final String name) {
        if (!configured.get()) {
            configure(new DolphinLoggerConfiguration());
        }
        DolphinLogger logger = this.loggerMap.get(name);
        if (logger != null) {
            return logger;
        } else {
            Level loggerLevel = loggerLevelMap.get(name);
            if(loggerLevel == null) {
                loggerLevel = configuration.getGlobalLevel();
            }
            DolphinLogger newInstance = new DolphinLogger(this, name, bridges, loggerLevel);
            DolphinLogger oldInstance = this.loggerMap.putIfAbsent(name, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }

    void reset() {
        this.loggerMap.clear();
    }

    public synchronized void configure(final DolphinLoggerConfiguration configuration) {
        Assert.requireNonNull(configuration, "configuration");
        bridges.clear();

        Iterator<DolphinLoggerBridgeFactory> iterator = ServiceLoader.load(DolphinLoggerBridgeFactory.class).iterator();
        while (iterator.hasNext()) {
            DolphinLoggerBridge bridge = iterator.next().create(configuration);
            if(bridge != null) {
                bridges.add(bridge);
            }
        }

        markers.clear();

        for(DolphinLogger logger : loggerMap.values()) {
            logger.updateBridges(Collections.unmodifiableList(bridges));
            Level level = loggerLevelMap.get(logger.getName());
            if(level == null) {
                level = configuration.getGlobalLevel();
            }
            logger.setLevel(level);
        }

        this.configuration = configuration;

        configured.set(true);
    }

    public synchronized List<String> addMarker(final String marker) {
        this.markers.add(Objects.requireNonNull(marker));
        return Collections.unmodifiableList(this.markers);
    }

    public synchronized List<String> removeMarker(final String marker) {
        this.markers.remove(Objects.requireNonNull(marker));
        return Collections.unmodifiableList(this.markers);
    }

    public synchronized List<String> addMarkers(final Collection<String> markers) {
        this.markers.addAll(Objects.requireNonNull(markers));
        return Collections.unmodifiableList(this.markers);
    }

    public synchronized List<String> removeMarkers(final Collection<String> markers) {
        this.markers.removeAll(Objects.requireNonNull(markers));
        return Collections.unmodifiableList(this.markers);
    }

    public List<String> getMarkers() {
        return Collections.unmodifiableList(markers);
    }

    public static void applyConfiguration(final DolphinLoggerConfiguration configuration) {
        final ILoggerFactory factory = LoggerFactory.getILoggerFactory();
        Objects.requireNonNull(factory);
        if(factory instanceof DolphinLoggerFactory) {
            ((DolphinLoggerFactory) factory).configure(configuration);
        } else {
            throw new IllegalStateException(LoggerFactory.class + " is not of type " + DolphinLoggerFactory.class);
        }
    }
}
