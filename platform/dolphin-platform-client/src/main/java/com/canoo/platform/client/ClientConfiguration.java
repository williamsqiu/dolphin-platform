package com.canoo.platform.client;

import com.canoo.platform.core.PlatformConfiguration;
import com.canoo.platform.core.PlatformThreadFactory;

import java.net.CookieStore;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public interface ClientConfiguration extends PlatformConfiguration {

    String UI_EXECUTOR = "platform.uiExecutor";

    String BACKGROUND_EXECUTOR = "platform.backgroundExecutor";

    String THREAD_FACTORY = "platform.threadFactory";

    String COOKIE_STORE = "platform.cookieStore";

    Executor getUiExecutor();

    ExecutorService getBackgroundExecutor();

    PlatformThreadFactory getDolphinPlatformThreadFactory();

    CookieStore getCookieStore();

    void setCookieStore(CookieStore cookieStore);

    void setUiExecutor(Executor executor);

    void setBackgroundExecutor(ExecutorService service);

    void setDolphinPlatformThreadFactory(PlatformThreadFactory factory);

    void setStringProperty(final String key, final String value);

    void setBooleanProperty(final String key, boolean value);

    void setIntProperty(final String key, int value);

    void setLongProperty(final String key, long value);

    void setListProperty(final String key, final List<String> value);

    <T> void setObjectProperty(final String key, T value);

    <T> T getObjectProperty(final String key);

    <T> T getObjectProperty(final String key, final T defaultValue);
}
