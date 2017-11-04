package com.canoo.dolphin.logger;

import com.canoo.dp.impl.platform.core.Assert;
import org.slf4j.event.Level;

public class DolphinLoggerUtils {

    private DolphinLoggerUtils() {}

    public static boolean isLevelEnabled(final Level baseLevel, final Level level) {
        Assert.requireNonNull(baseLevel, "baseLevel");
        Assert.requireNonNull(level, "level");
        return baseLevel.toInt() <= level.toInt();
    }

    public static boolean isLevelEnabled(final Level baseLevel, final String level) {
        Assert.requireNonNull(level, "level");
        return isLevelEnabled(baseLevel, Level.valueOf(level));
    }
}
