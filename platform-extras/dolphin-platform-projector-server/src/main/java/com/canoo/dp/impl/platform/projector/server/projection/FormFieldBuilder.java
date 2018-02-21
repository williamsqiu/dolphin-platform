/*
 * Copyright 2015-2018 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dp.impl.platform.projector.server.projection;

import com.canoo.dp.impl.platform.projector.base.Icon;
import com.canoo.dp.impl.platform.projector.base.IconBean;
import com.canoo.dp.impl.platform.projector.form.FormField;
import com.canoo.dp.impl.platform.projector.form.concrete.BooleanFormFieldBean;
import com.canoo.dp.impl.platform.projector.form.concrete.StringFormFieldBean;
import com.canoo.dp.impl.platform.projector.metadata.AbstractKeyValueBean;
import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.platform.remoting.BeanManager;
import com.canoo.platform.remoting.ValueChangeListener;

import java.util.HashMap;
import java.util.Map;

public class FormFieldBuilder<T, S extends FormField<T>> {

    private final BeanManager beanManager;

    private String description;

    private String title;

    private String iconFamily;

    private String iconCode;

    private Class<T> contentType;

    private boolean mandatory;

    private boolean disabled;

    private boolean editable;

    private T value;

    private Class<S> beanType;

    private ValueChangeListener<? super T> listener;

    private Map<String, Object> metadata = new HashMap<>();

    public FormFieldBuilder(Class<T> contentType, Class<S> beanType, BeanManager beanManager) {
        this.contentType = contentType;
        this.beanType = beanType;
        this.beanManager = beanManager;
    }

    public static FormFieldBuilder<String, StringFormFieldBean> createStringField(BeanManager beanManager) {
        return new FormFieldBuilder(String.class, StringFormFieldBean.class, beanManager);
    }

    public static FormFieldBuilder<Boolean, BooleanFormFieldBean> createBooleanField(BeanManager beanManager) {
        return new FormFieldBuilder(Boolean.class, BooleanFormFieldBean.class, beanManager);
    }

    public FormFieldBuilder<T, S> withValueChangeListener(ValueChangeListener<? super T> listener) {
        this.listener = listener;
        return this;
    }

    public FormFieldBuilder<T, S> withMandatoryFlag(boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public FormFieldBuilder<T, S> withDisabledFlag(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public FormFieldBuilder<T, S> withEditableFlag(boolean editable) {
        this.editable = editable;
        return this;
    }

    public FormFieldBuilder<T, S> withValue(T value) {
        this.value = value;
        return this;
    }

    public FormFieldBuilder<T, S> withIcon(String iconFamily, String iconCode) {
        this.iconCode = iconCode;
        this.iconFamily = iconFamily;
        return this;
    }

    public FormFieldBuilder<T, S> withDescription(String description) {
        this.description = description;
        return this;
    }

    public FormFieldBuilder<T, S> withTitle(String title) {
        this.title = title;
        return this;
    }

    public FormFieldBuilder<T, S> withLayoutMetadata(String key, String value) {
        metadata.put(key, value);
        return this;
    }

    public FormFieldBuilder<T, S> withLayoutMetadata(String key, Integer value) {
        metadata.put(key, value);
        return this;
    }

    public FormFieldBuilder<T, S> withLayoutMetadata(String key, Double value) {
        metadata.put(key, value);
        return this;
    }

    public FormFieldBuilder<T, S> withLayoutMetadata(String key, Long value) {
        metadata.put(key, value);
        return this;
    }

    public FormFieldBuilder<T, S> withLayoutMetadata(String key, Float value) {
        metadata.put(key, value);
        return this;
    }

    public FormFieldBuilder<T, S> withLayoutMetadata(String key, Boolean value) {
        metadata.put(key, value);
        return this;
    }

    public FormFieldBuilder<T, S> removeLayoutMetadata(String key) {
        metadata.remove(key);
        return this;
    }

    public FormField<T> build() {
        FormField<T> bean = beanManager.create(beanType);
        bean.setContentType(contentType);

        bean.setMandatory(mandatory);
        bean.setDisabled(disabled);
        bean.setEditable(editable);

        bean.setTitle(title);
        bean.setDescription(description);

        bean.setValue(value);
        if (listener != null) {
            bean.valueProperty().onChanged(listener);
        }

        metadata.forEach((k, v) -> {
            if (v instanceof String) {
                KeyValue<String> keyValue = beanManager.create(AbstractKeyValueBean.class);
                keyValue.setKey(k);
                keyValue.setValue((String) v);
                bean.getLayoutMetadata().add(keyValue);
            } else if (v instanceof Boolean) {
                KeyValue<Boolean> keyValue = beanManager.create(AbstractKeyValueBean.class);
                keyValue.setKey(k);
                keyValue.setValue((Boolean) v);
                bean.getLayoutMetadata().add(keyValue);
            }
        });

        if (iconCode != null) {
            Icon icon = beanManager.create(IconBean.class);
            icon.setIconFamily(iconFamily);
            icon.setIconCode(iconCode);
            bean.setIcon(icon);
        }


        return bean;
    }

}
