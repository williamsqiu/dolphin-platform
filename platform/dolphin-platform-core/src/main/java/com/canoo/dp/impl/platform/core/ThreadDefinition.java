package com.canoo.dp.impl.platform.core;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.WeakHashMap;

public class ThreadDefinition {

    private final static Map<Thread, ThreadDefinition> mapping = new WeakHashMap<>();

    private final String name;

    private final String id;

    private ThreadDefinition(final Thread thread) {
        this.name = Assert.requireNonNull(thread, "thread").getName();
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ThreadDefinition that = (ThreadDefinition) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    public static ThreadDefinition getForCurrentThread() {
        final Thread thread = Thread.currentThread();
        return getForThread(thread);
    }

    public static synchronized ThreadDefinition getForThread(final Thread thread) {
        Assert.requireNonNull(thread, "thread");
        return mapping.computeIfAbsent(thread, t -> new ThreadDefinition(t));
    }
}
