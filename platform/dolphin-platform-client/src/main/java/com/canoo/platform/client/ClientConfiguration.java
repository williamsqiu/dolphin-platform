package com.canoo.platform.client;

import com.canoo.platform.core.PlatformConfiguration;
import com.canoo.platform.core.PlatformThreadFactory;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public interface ClientConfiguration extends PlatformConfiguration {

    Executor getUiExecutor();

    ExecutorService getBackgroundExecutor();

    PlatformThreadFactory getDolphinPlatformThreadFactory();

    void setUiExecutor(Executor executor);

    void getBackgroundExecutor(ExecutorService service);

    void setDolphinPlatformThreadFactory(PlatformThreadFactory factory);

    void setStringProperty(final String key, final String value);

    void setBooleanProperty(final String key, boolean value);

    void setIntProperty(final String key, int value);

    void setLongProperty(final String key, long value);

    void setListProperty(final String key, final List<String> value);

    <T> T getObjectProperty(final String key);

    <T> T getObjectProperty(final String key, final T defaultValue);
}
