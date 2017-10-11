package com.canoo.dp.impl.platform.client;

import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.core.PlatformThreadFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class DefaultClientConfiguration implements ClientConfiguration {

    @Override
    public <T> T getObjectProperty(String key, T defaultValue) {
        return null;
    }

    @Override
    public Executor getUiExecutor() {
        return null;
    }

    @Override
    public ExecutorService getBackgroundExecutor() {
        return null;
    }

    @Override
    public PlatformThreadFactory getDolphinPlatformThreadFactory() {
        return null;
    }

    @Override
    public void setUiExecutor(Executor executor) {

    }

    @Override
    public void getBackgroundExecutor(ExecutorService service) {

    }

    @Override
    public void setDolphinPlatformThreadFactory(PlatformThreadFactory factory) {

    }

    @Override
    public void setStringProperty(String key, String value) {

    }

    @Override
    public void setBooleanProperty(String key, boolean value) {

    }

    @Override
    public void setIntProperty(String key, int value) {

    }

    @Override
    public void setLongProperty(String key, long value) {

    }

    @Override
    public void setListProperty(String key, List<String> value) {

    }

    @Override
    public <T> T getObjectProperty(String key) {
        return null;
    }

    @Override
    public boolean containsProperty(String key) {
        return false;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return null;
    }

    @Override
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        return false;
    }

    @Override
    public int getIntProperty(String key, int defaultValue) {
        return 0;
    }

    @Override
    public long getLongProperty(String key, long defaultValue) {
        return 0;
    }

    @Override
    public List<String> getListProperty(String key, List<String> defaultValues) {
        return null;
    }

    @Override
    public Set<String> getPropertyKeys() {
        return null;
    }
}
