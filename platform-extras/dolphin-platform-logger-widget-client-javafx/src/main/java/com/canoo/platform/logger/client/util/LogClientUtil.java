package com.canoo.platform.logger.client.util;

import com.canoo.impl.dp.logging.DolphinLoggerFactory;
import com.canoo.platform.logging.spi.LogMessage;
import com.canoo.platform.core.functional.Subscription;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LogClientUtil {

    public static BoundLogList<LogMessage> createObservableListFromLocalCache() {
        final ObservableList<LogMessage> list = FXCollections.observableArrayList(DolphinLoggerFactory.getLogCache());
        final Subscription subscription = DolphinLoggerFactory.addListener(l -> {
            final List<LogMessage> currentCache = Collections.unmodifiableList(DolphinLoggerFactory.getLogCache());
            Platform.runLater(() -> {
                final List<LogMessage> toRemove = list.stream().
                        filter(e -> !currentCache.contains(l)).
                        collect(Collectors.toList());

                list.removeAll(toRemove);

                final List<LogMessage> toAdd = currentCache.stream().
                        filter(e -> !list.contains(e)).
                        collect(Collectors.toList());

                list.addAll(toAdd);
            });
        });
        return new BoundLogList(subscription, list);
    }

}
