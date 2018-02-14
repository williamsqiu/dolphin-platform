package com.canoo.dp.impl.platform.core.ansi;

import static com.canoo.dp.impl.platform.core.ansi.AnsiUtils.BACKGROUND_PREFIX;
import static com.canoo.dp.impl.platform.core.ansi.AnsiUtils.FOREGROUND_PREFIX;
import static com.canoo.dp.impl.platform.core.ansi.AnsiUtils.PREFIX;
import static com.canoo.dp.impl.platform.core.ansi.AnsiUtils.SUFFIX;

public enum AnsiColor {
    BLACK("0"),
    RED("1"),
    GREEN("2"),
    YELLOW("3"),
    BLUE("4"),
    PURPLE("5"),
    CYAN("6"),
    WHITE("7");

    private final String colorIdentifier;

    AnsiColor(final String colorIdentifier) {
        this.colorIdentifier = colorIdentifier;
    }

    public String foregroundCode() {
        return PREFIX + FOREGROUND_PREFIX + colorIdentifier + SUFFIX;
    }

    public String backgroundCode() {
        return PREFIX + BACKGROUND_PREFIX + colorIdentifier + SUFFIX;
    }
}
