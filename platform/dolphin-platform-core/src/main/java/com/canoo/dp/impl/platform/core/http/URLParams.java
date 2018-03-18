package com.canoo.dp.impl.platform.core.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class URLParams {

    private Map<String, String> map;

    private URLParams() {
        this.map = new HashMap<>();
    }

    public static URLParams of(final String key, final String value) {
        final URLParams params = new URLParams();
        params.and(key, value);
        return params;
    }

    public static URLParams of(final String key, final int value) {
        final URLParams params = new URLParams();
        params.and(key, value);
        return params;
    }

    public static URLParams of(final String key, final long value) {
        final URLParams params = new URLParams();
        params.and(key, value);
        return params;
    }

    public static URLParams of(final String key, final boolean value) {
        final URLParams params = new URLParams();
        params.and(key, value);
        return params;
    }

    public URLParams and(final String key, final String value) {
        map.put(key, value);
        return this;
    }

    public URLParams and(final String key, final int value) {
        map.put(key, "" + value);
        return this;
    }

    public URLParams and(final String key, final long value) {
        map.put(key, "" + value);
        return this;
    }

    public URLParams and(final String key, final boolean value) {
        map.put(key, "" + value);
        return this;
    }

    public Map<String, String> asMap() {
        return Collections.unmodifiableMap(map);
    }
}
