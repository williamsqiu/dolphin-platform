package com.canoo.dp.impl.platform.client;

import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.core.PlatformThreadFactory;

import java.net.CookieStore;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class DefaultClientConfiguration implements ClientConfiguration {

    private final Properties internalProperties = new Properties();

    @Override
    public <T> T getObjectProperty(final String key) {
        return (T) internalProperties.get(key);
    }

    @Override
    public <T> T getObjectProperty(final String key, final T defaultValue) {
        if (containsProperty(key)) {
            return getObjectProperty(key);
        } else {
            return defaultValue;
        }
    }

    @Override
    public <T> void setObjectProperty(final String key, final T value) {
        internalProperties.put(key, value);
    }

    @Override
    public boolean containsProperty(final String key) {
        return internalProperties.containsKey(key);
    }

    @Override
    public Set<String> getPropertyKeys() {
        return internalProperties.stringPropertyNames();
    }

    @Override
    public Executor getUiExecutor() {
        return getObjectProperty(UI_EXECUTOR);
    }

    @Override
    public ExecutorService getBackgroundExecutor() {
        return getObjectProperty(BACKGROUND_EXECUTOR);
    }

    @Override
    public PlatformThreadFactory getDolphinPlatformThreadFactory() {
        return getObjectProperty(THREAD_FACTORY);
    }

    @Override
    public CookieStore getCookieStore() {
        return getObjectProperty(COOKIE_STORE);
    }

    @Override
    public void setUiExecutor(final Executor executor) {
        internalProperties.put(UI_EXECUTOR, executor);
    }

    @Override
    public void setBackgroundExecutor(final ExecutorService service) {
        internalProperties.put(BACKGROUND_EXECUTOR, service);
    }

    @Override
    public void setDolphinPlatformThreadFactory(final PlatformThreadFactory factory) {
        internalProperties.put(THREAD_FACTORY, factory);
    }

    @Override
    public void setCookieStore(final CookieStore cookieStore) {
        internalProperties.put(COOKIE_STORE, cookieStore);
    }

    @Override
    public void setStringProperty(final String key, final String value) {
        setObjectProperty(key, value);
    }

    @Override
    public void setBooleanProperty(final String key, final boolean value) {
        setObjectProperty(key, value);
    }

    @Override
    public void setIntProperty(final String key, final int value) {
        setObjectProperty(key, value);
    }

    @Override
    public void setLongProperty(final String key, final long value) {
        setObjectProperty(key, value);
    }

    @Override
    public void setListProperty(final String key, final List<String> value) {
        setObjectProperty(key, value);
    }

    @Override
    public String getProperty(final String key, final String defaultValue) {
        return getObjectProperty(key, defaultValue);
    }

    @Override
    public boolean getBooleanProperty(final String key, final boolean defaultValue) {
        return getObjectProperty(key, defaultValue);
    }

    @Override
    public int getIntProperty(final String key, final int defaultValue) {
        return getObjectProperty(key, defaultValue);
    }

    @Override
    public long getLongProperty(final String key, final long defaultValue) {
        return getObjectProperty(key, defaultValue);
    }

    @Override
    public List<String> getListProperty(final String key, final List<String> defaultValues) {
        return getObjectProperty(key, defaultValues);
    }
}
