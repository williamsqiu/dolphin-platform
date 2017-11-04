package com.canoo.dolphin.logger.impl;

import org.slf4j.spi.MDCAdapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThreadLocalMDCAdapter implements MDCAdapter {

    private ThreadLocal<Map<String, String>> mapThreadLocal = new ThreadLocal<>();

    @Override
    public void put(String key, String val) {
        getMap().put(key, val);
    }

    @Override
    public String get(String key) {
        return getMap().get(key);
    }

    @Override
    public void remove(String key) {
        getMap().remove(key);
    }

    @Override
    public void clear() {
        getMap().clear();
    }

    @Override
    public Map<String, String> getCopyOfContextMap() {
        return Collections.unmodifiableMap(getMap());
    }

    @Override
    public void setContextMap(Map<String, String> contextMap) {
        mapThreadLocal.set(contextMap);
    }

    private Map<String, String> getMap() {
        Map<String, String> map = mapThreadLocal.get();
        if(map == null) {
            map = new HashMap<>();
            mapThreadLocal.set(map);
        }
        return map;
    }
}
