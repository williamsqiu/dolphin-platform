package com.canoo.dp.impl.platform.projector.server.i18n;

import com.canoo.platform.remoting.Property;

import java.util.Locale;
import java.util.WeakHashMap;

public class BeanLocalization {

    private final WeakHashMap<Property<String>, LocaleKey> translateableProperties = new WeakHashMap<>();

    private Locale locale;

    private Translator translator;

    public BeanLocalization(final Translator translator) {
        locale = Locale.getDefault();
        this.translator = translator;
    }

    public void add(final Property<String> property, final LocaleKey key) {
        //TODO: Exception if not in Dolphin Context
        translateableProperties.put(property, key);
        update(property);
    }

    public void add(final Property<String> property, final String key, final Object... args) {
        add(property, new LocaleKey(key, args));
    }

    public void remove(final Property<String> property) {
        translateableProperties.remove(property);
    }

    private void update(final Property<String> property) {
        LocaleKey key = translateableProperties.get(property);
        if(key != null) {
            property.set(translator.translate(getLocale(), key.getKey(), key.getArgs()));
        }
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        //TODO: Exception if not in Dolphin Context
        this.locale = locale;
        translateableProperties.keySet().forEach(p -> update(p));
    }
}
