/*
 * Copyright 2015-2018 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
