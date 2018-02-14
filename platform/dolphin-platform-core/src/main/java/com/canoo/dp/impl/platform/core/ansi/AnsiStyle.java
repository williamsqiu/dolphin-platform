package com.canoo.dp.impl.platform.core.ansi;

import java.io.PrintStream;

public class AnsiStyle {

    private AnsiColor foregroundColor;

    private AnsiColor backgroundColor;

    private boolean bold;

    public boolean isBold() {
        return bold;
    }

    public void setBold(final boolean bold) {
        this.bold = bold;
    }

    public AnsiColor getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(final AnsiColor foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public AnsiColor getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(final AnsiColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void print(final String text) {
        print(text, System.out);
    }

    public void print(final String text, final PrintStream stream) {
        try {
            if (backgroundColor != null) {
                stream.print(backgroundColor.backgroundCode());
            }
            if (foregroundColor != null) {
                stream.print(foregroundColor.foregroundCode());
            }
            if (isBold()) {
                stream.print(AnsiUtils.ANSI_BOLD);
            }
            stream.print(text);
        } finally {
            stream.print(AnsiUtils.ANSI_RESET);
        }
    }
}
