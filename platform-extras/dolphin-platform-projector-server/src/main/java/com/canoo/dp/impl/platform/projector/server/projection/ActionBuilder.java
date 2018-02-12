package com.canoo.dp.impl.platform.projector.server.projection;

import com.canoo.dp.impl.platform.projector.action.Action;
import com.canoo.dp.impl.platform.projector.base.Icon;
import com.canoo.dp.impl.platform.projector.base.IconBean;
import com.canoo.dp.impl.platform.projector.metadata.AbstractKeyValueBean;
import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.dp.impl.platform.projector.server.i18n.BeanLocalization;
import com.canoo.dp.impl.platform.projector.server.i18n.LocaleKey;
import com.canoo.platform.remoting.BeanManager;

import java.util.HashMap;
import java.util.Map;

public class ActionBuilder<T extends Action> {

    private final Class<T> actionClass;

    private final BeanManager beanManager;

    private String description;

    private String title;

    private String iconFamily;

    private String iconCode;

    private final Map<String, Object> metadata = new HashMap<>();

    private final BeanLocalization localization;

    private LocaleKey descriptionLocaleKey;

    private LocaleKey titleLocaleKey;

    public ActionBuilder(Class<T> actionClass, BeanManager beanManager, BeanLocalization localization) {
        this.actionClass = actionClass;
        this.beanManager = beanManager;
        this.localization = localization;
    }

    public ActionBuilder(Class<T> actionClass, BeanManager beanManager) {
        this(actionClass, beanManager, null);
    }

    public ActionBuilder<T> withIcon(String iconFamily, String iconCode) {
        this.iconCode = iconCode;
        this.iconFamily = iconFamily;
        return this;
    }

    public ActionBuilder<T> withDescription(String description) {
        this.description = description;
        this.descriptionLocaleKey = null;
        return this;
    }

    public ActionBuilder<T> withLocalizedDescription(String key, Object... args) {
        if(localization == null) {
            throw new NullPointerException("localization == null");
        }
        this.description = null;
        this.descriptionLocaleKey = new LocaleKey(key, args);
        return this;
    }

    public ActionBuilder<T> withTitle(String title) {
        this.title = title;
        this.titleLocaleKey = null;
        return this;
    }

    public ActionBuilder<T> withLocalizedTitle(String key, Object... args) {
        if(localization == null) {
            throw new NullPointerException("localization == null");
        }
        this.title = null;
        this.titleLocaleKey = new LocaleKey(key, args);
        return this;
    }

    public ActionBuilder<T> withLayoutMetadata(String key, String value) {
        metadata.put(key, value);
        return this;
    }

    public ActionBuilder<T> withLayoutMetadata(String key, Integer value) {
        metadata.put(key, value);
        return this;
    }

    public ActionBuilder<T> withLayoutMetadata(String key, Double value) {
        metadata.put(key, value);
        return this;
    }

    public ActionBuilder<T> withLayoutMetadata(String key, Long value) {
        metadata.put(key, value);
        return this;
    }

    public ActionBuilder<T> withLayoutMetadata(String key, Float value) {
        metadata.put(key, value);
        return this;
    }

    public ActionBuilder<T> withLayoutMetadata(String key, Boolean value) {
        metadata.put(key, value);
        return this;
    }

    public ActionBuilder<T> removeLayoutMetadata(String key) {
        metadata.remove(key);
        return this;
    }

    public T build() {
        T action = beanManager.create(actionClass);

        if(titleLocaleKey == null) {
            action.setTitle(title);
        } else {
            localization.add(action.titleProperty(), titleLocaleKey);
        }

        if(descriptionLocaleKey == null) {
            action.setDescription(description);
        } else {
            localization.add(action.descriptionProperty(), descriptionLocaleKey);
        }

        metadata.forEach((k, v) -> {
            if(v instanceof String) {
                KeyValue<String> keyValue = beanManager.create(AbstractKeyValueBean.class);
                keyValue.setKey(k);
                keyValue.setValue((String) v);
                action.getLayoutMetadata().add(keyValue);
            } else if(v instanceof Boolean) {
                KeyValue<Boolean> keyValue = beanManager.create(AbstractKeyValueBean.class);
                keyValue.setKey(k);
                keyValue.setValue((Boolean) v);
                action.getLayoutMetadata().add(keyValue);
            }
        });

        if(iconCode != null) {
            Icon icon = beanManager.create(IconBean.class);
            icon.setIconFamily(iconFamily);
            icon.setIconCode(iconCode);
            action.setIcon(icon);
        }

        return action;
    }
}
