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
package com.canoo.impl.dp.logging.bridges;

import com.canoo.dp.impl.platform.core.ansi.AnsiUtils;
import com.canoo.platform.logging.DolphinLoggerConfiguration;
import com.canoo.platform.logging.spi.DolphinLoggerBridge;
import com.canoo.platform.logging.spi.LogMessage;
import org.slf4j.event.Level;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class SimpleDolphinLogger implements DolphinLoggerBridge {

    private final DateFormat dateFormat;

    public SimpleDolphinLogger(final DolphinLoggerConfiguration configuration) {
        this.dateFormat = Objects.requireNonNull(configuration.getDateFormat());
    }

    @Override
    public void log(final LogMessage logMessage) {
        final String textColor = Optional.ofNullable(logMessage.getLevel()).
                map(l -> {
                    if (l.equals(Level.ERROR)) {
                        return AnsiUtils.ANSI_RED;
                    }
                    if (l.equals(Level.WARN)) {
                        return AnsiUtils.ANSI_YELLOW;
                    }
                    if (l.equals(Level.INFO)) {
                        return AnsiUtils.ANSI_BLUE;
                    }
                    return AnsiUtils.ANSI_CYAN;
                }).orElse(AnsiUtils.ANSI_CYAN);

        final StringBuilder buf = new StringBuilder();
        buf.append(AnsiUtils.ANSI_WHITE);
        final Date timestamp = Date.from(logMessage.getTimestamp().toInstant());
        buf.append(dateFormat.format(timestamp));
        buf.append(AnsiUtils.ANSI_RESET);

        buf.append(" ");

        buf.append(AnsiUtils.ANSI_BOLD);
        buf.append(textColor);
        buf.append(logMessage.getLevel());

        buf.append(" - ");

        buf.append(logMessage.getMessage());
        buf.append(AnsiUtils.ANSI_RESET);

        buf.append(AnsiUtils.ANSI_WHITE);
        buf.append(" - ");

        buf.append(logMessage.getLoggerName());


        if (!logMessage.getMarker().isEmpty()) {
            buf.append(" - [");
            for (String marker : logMessage.getMarker()) {
                buf.append(marker);
                if (logMessage.getMarker().indexOf(marker) < logMessage.getMarker().size() - 1) {
                    buf.append(", ");
                }
            }
            buf.append("]");
        }
        buf.append(" - ");
        buf.append(logMessage.getThreadName());
        buf.append(AnsiUtils.ANSI_RESET);

        if (logMessage.getThrowable() != null) {
            buf.append(AnsiUtils.ANSI_RED);
            buf.append(System.lineSeparator());
            buf.append(logMessage.getExceptionDetail());
            buf.append(AnsiUtils.ANSI_RESET);
        }
        print(buf.toString());
    }


    private synchronized void print(final String message) {
        System.out.println(message);
    }
}
