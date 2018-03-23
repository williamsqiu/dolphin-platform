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
package com.canoo.dp.impl.platform.server.metrics;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.metrics.Metric;
import com.canoo.platform.metrics.Tag;

import java.util.List;
import java.util.Objects;

public abstract class AbstractMeter implements Metric {

    private final MeterIdentifier identifier;

    private final AutoCloseable closeable;

    public AbstractMeter(final MeterIdentifier identifier, final AutoCloseable closeable) {
        this.identifier = Assert.requireNonNull(identifier, "identifier");
        this.closeable = Assert.requireNonNull(closeable, "closeable");
    }

    @Override
    public String getName() {
        return identifier.getName();
    }

    @Override
    public List<Tag> getTags() {
        return identifier.getTags();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AbstractMeter that = (AbstractMeter) o;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public void close() throws Exception {
        closeable.close();
    }
}
