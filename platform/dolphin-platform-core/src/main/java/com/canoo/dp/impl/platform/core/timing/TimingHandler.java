package com.canoo.dp.impl.platform.core.timing;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.ansi.AnsiUtils;
import com.canoo.platform.core.functional.Task;
import com.canoo.platform.core.timing.Timing;
import com.canoo.platform.core.timing.TimingGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TimingHandler {

    private final static Logger LOG = LoggerFactory.getLogger(TimingHandler.class);

    private final static String GROUP_NAME = "All threads";

    private final static ThreadLocal<ThreadTimingContext> contextLocal = new ThreadLocal<>();

    private final static TimingGroupImpl group = new TimingGroupImpl(GROUP_NAME);

    private static ThreadTimingContext getLocalContext() {
        final ThreadTimingContext context = Optional.ofNullable(contextLocal.get())
                .orElse(new ThreadTimingContext(Thread.currentThread()));
        contextLocal.set(context);
        if(!group.getContent().contains(context)) {
            group.add(context);
        }
        return context;
    }

    public static TimingGroup getGroup() {
        return group;
    }

    public static Timing start(final String name) {
        return start(name, null, ZonedDateTime.now());
    }

    public static Timing start(final String name, final ZonedDateTime startTime) {
        return start(name, null, startTime);
    }

    public static Timing start(final String name, final String description) {
        return start(name, description, ZonedDateTime.now());
    }

    public static Timing start(final String name, final String description, final ZonedDateTime startTime) {
        return getLocalContext().start(name, description, startTime);
    }

    public static Timing stop(final Timing timing) {
        return getLocalContext().stop(timing);
    }

    public static Timing record(final String name, Runnable task) {
        return record(name, null, task);
    }

    public static Timing record(final String name, final String description, Runnable task) {
        Assert.requireNonNull(task, "task");
        final Timing timing = start(name, description);
        try {
            task.run();
        } finally {
            return stop(timing);
        }
    }

    public static Timing recordTask(final String name, Task task) {
        return recordTask(name, null, task);
    }

    public static Timing recordTask(final String name, final String description, Task task) {
        Assert.requireNonNull(task, "task");
        final Timing timing = start(name, description);
        try {
            task.run();
        } finally {
            return stop(timing);
        }
    }

    public static <V> V record(final String name, Supplier<V> task) {
        return record(name, null, task);
    }

    public static <V> V record(final String name, final String description, Supplier<V> task) {
        Assert.requireNonNull(task, "task");
        final Timing timing = start(name, description);
        try {
            return task.get();
        } finally {
             stop(timing);
        }
    }

    public static Timing record(final String name, final ZonedDateTime startTime, final CompletableFuture<?> task) {
        return record(name, null, startTime, task);
    }

    public static Timing record(final String name, final CompletableFuture<?> task) {
        return record(name, null, ZonedDateTime.now(), task);
    }

    public static Timing record(final String name, final String description, final CompletableFuture<?> task) {
        return record(name, description, ZonedDateTime.now(), task);
    }

    public static Timing record(final String name, final String description, final ZonedDateTime startTime, final CompletableFuture<?> task) {
        Assert.requireNonNull(task, "task");
        final Timing timing = start(name, description, startTime);
        task.whenComplete((v, e) -> {
            stop(timing);
        });
        return timing;
    }

    public static void printAll() {
        AnsiUtils.printLinefeed();

        AnsiUtils.printHorizontalLine(95);
        AnsiUtils.printLinefeed();

        AnsiUtils.printVerticalLine();
        AnsiUtils.printSpaces(93);
        AnsiUtils.printVerticalLine();
        AnsiUtils.printLinefeed();

        AnsiUtils.printVerticalLine();
        AnsiUtils.printSpaces(2);
        AnsiUtils.set(AnsiUtils.ANSI_BLUE);
        AnsiUtils.print("Timing for group '");
        AnsiUtils.set(AnsiUtils.ANSI_BOLD);
        final String groupName = getGroup().getName();
        AnsiUtils.print(groupName);
        AnsiUtils.reset();
        AnsiUtils.set(AnsiUtils.ANSI_BLUE);
        AnsiUtils.print("'");
        AnsiUtils.reset();
        AnsiUtils.printSpaces(73 - groupName.length());
        AnsiUtils.printVerticalLine();
        AnsiUtils.printLinefeed();

        AnsiUtils.printVerticalLine();
        AnsiUtils.printHorizontalLine(93);
        AnsiUtils.printVerticalLine();
        AnsiUtils.printLinefeed();

        getGroup().getContent().forEach(c -> {
            AnsiUtils.printVerticalLine();
            AnsiUtils.printSpaces(4);
            AnsiUtils.set(AnsiUtils.ANSI_PURPLE);
            AnsiUtils.print("Timing for context '");
            AnsiUtils.set(AnsiUtils.ANSI_BOLD);
            final String contentName = c.getName();
            AnsiUtils.print(c.getName());
            AnsiUtils.reset();
            AnsiUtils.set(AnsiUtils.ANSI_PURPLE);
            AnsiUtils.print("'");
            AnsiUtils.reset();
            AnsiUtils.printSpaces(69 - contentName.length());
            AnsiUtils.printVerticalLine();
            AnsiUtils.printLinefeed();

            c.getTimings().forEach(t -> {
                AnsiUtils.printVerticalLine();
                AnsiUtils.printSpaces(8);
                AnsiUtils.set(AnsiUtils.ANSI_GREEN);
                AnsiUtils.printVerticalLine();
                AnsiUtils.print("--");
                AnsiUtils.printSpaces(2);
                AnsiUtils.print("Record '");
                AnsiUtils.set(AnsiUtils.ANSI_BOLD);
                final String name = t.getName();
                AnsiUtils.print(name);
                AnsiUtils.reset();
                AnsiUtils.set(AnsiUtils.ANSI_GREEN);
                AnsiUtils.print("'");
                AnsiUtils.reset();
                AnsiUtils.printSpaces(48 - name.length());
                AnsiUtils.set(AnsiUtils.ANSI_GREEN);
                AnsiUtils.print("Duration: ");
                final String time = t.getDuration().toMillis() + " ms";
                AnsiUtils.print(time);
                AnsiUtils.reset();
                AnsiUtils.printSpaces(16 - time.length());
                AnsiUtils.printVerticalLine();
                AnsiUtils.printLinefeed();
            });
            AnsiUtils.printVerticalLine();
            AnsiUtils.printSpaces(93);
            AnsiUtils.printVerticalLine();
            AnsiUtils.printLinefeed();
        });

        AnsiUtils.printVerticalLine();
        AnsiUtils.printHorizontalLine(93);
        AnsiUtils.printVerticalLine();
        AnsiUtils.printLinefeed();

        AnsiUtils.printLinefeed();
    }
}
