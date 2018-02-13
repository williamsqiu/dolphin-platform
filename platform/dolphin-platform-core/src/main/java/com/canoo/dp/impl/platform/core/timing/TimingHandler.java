package com.canoo.dp.impl.platform.core.timing;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.ansi.AnsiOut;
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
        AnsiOut.printLinefeed();

        AnsiOut.printHorizontalLine(95);
        AnsiOut.printLinefeed();

        AnsiOut.printVerticalLine();
        AnsiOut.printSpaces(93);
        AnsiOut.printVerticalLine();
        AnsiOut.printLinefeed();

        AnsiOut.printVerticalLine();
        AnsiOut.printSpaces(2);
        AnsiOut.set(AnsiOut.ANSI_BLUE);
        AnsiOut.print("Timing for group '");
        AnsiOut.set(AnsiOut.ANSI_BOLD);
        final String groupName = getGroup().getName();
        AnsiOut.print(groupName);
        AnsiOut.reset();
        AnsiOut.set(AnsiOut.ANSI_BLUE);
        AnsiOut.print("'");
        AnsiOut.reset();
        AnsiOut.printSpaces(73 - groupName.length());
        AnsiOut.printVerticalLine();
        AnsiOut.printLinefeed();

        AnsiOut.printVerticalLine();
        AnsiOut.printHorizontalLine(93);
        AnsiOut.printVerticalLine();
        AnsiOut.printLinefeed();

        getGroup().getContent().forEach(c -> {
            AnsiOut.printVerticalLine();
            AnsiOut.printSpaces(4);
            AnsiOut.set(AnsiOut.ANSI_PURPLE);
            AnsiOut.print("Timing for context '");
            AnsiOut.set(AnsiOut.ANSI_BOLD);
            final String contentName = c.getName();
            AnsiOut.print(c.getName());
            AnsiOut.reset();
            AnsiOut.set(AnsiOut.ANSI_PURPLE);
            AnsiOut.print("'");
            AnsiOut.reset();
            AnsiOut.printSpaces(69 - contentName.length());
            AnsiOut.printVerticalLine();
            AnsiOut.printLinefeed();

            c.getTimings().forEach(t -> {
                AnsiOut.printVerticalLine();
                AnsiOut.printSpaces(8);
                AnsiOut.set(AnsiOut.ANSI_GREEN);
                AnsiOut.printVerticalLine();
                AnsiOut.print("--");
                AnsiOut.printSpaces(2);
                AnsiOut.print("Record '");
                AnsiOut.set(AnsiOut.ANSI_BOLD);
                final String name = t.getName();
                AnsiOut.print(name);
                AnsiOut.reset();
                AnsiOut.set(AnsiOut.ANSI_GREEN);
                AnsiOut.print("'");
                AnsiOut.reset();
                AnsiOut.printSpaces(48 - name.length());
                AnsiOut.set(AnsiOut.ANSI_GREEN);
                AnsiOut.print("Duration: ");
                final String time = t.getDuration().toMillis() + " ms";
                AnsiOut.print(time);
                AnsiOut.reset();
                AnsiOut.printSpaces(16 - time.length());
                AnsiOut.printVerticalLine();
                AnsiOut.printLinefeed();
            });
            AnsiOut.printVerticalLine();
            AnsiOut.printSpaces(93);
            AnsiOut.printVerticalLine();
            AnsiOut.printLinefeed();
        });

        AnsiOut.printVerticalLine();
        AnsiOut.printHorizontalLine(93);
        AnsiOut.printVerticalLine();
        AnsiOut.printLinefeed();

        AnsiOut.printLinefeed();
    }
}
