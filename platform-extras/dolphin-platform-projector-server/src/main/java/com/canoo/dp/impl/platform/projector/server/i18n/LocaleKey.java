package com.canoo.dp.impl.platform.projector.server.i18n;

public class LocaleKey {

    private final String key;

    private final Object[] args;

    public LocaleKey(String key, Object... args) {
        this.key = key;
        this.args = args;
    }

    public String getKey() {
        return key;
    }

    public Object[] getArgs() {
        return args;
    }
}
