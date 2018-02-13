package com.canoo.dp.impl.platform.core.timing;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.timing.TimingContext;
import com.canoo.platform.core.timing.TimingGroup;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class TimingGroupImpl implements TimingGroup {

    private final String name;

    private final String description;

    private final String id;

    private final List<TimingContext> content;

    public TimingGroupImpl(final String name) {
        this(name, null);
    }

    public TimingGroupImpl(final String name, final String description) {
        this.name = Assert.requireNonBlank(name, "name");
        this.description = description;
        this.content = new CopyOnWriteArrayList<>();
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void add(final TimingContext context) {
        Assert.requireNonNull(context, "context");
        content.add(context);
    }

    public void remove(final TimingContext context) {
        Assert.requireNonNull(context, "context");
        content.remove(context);
    }

    @Override
    public List<TimingContext> getContent() {
        return Collections.unmodifiableList(content);
    }
}
