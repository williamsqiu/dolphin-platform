package com.canoo.dolphin.integration.action;

import com.canoo.dolphin.mapping.DolphinBean;
import com.canoo.dolphin.mapping.Property;

@DolphinBean
public class ActionTestBean {

    private Property<String> stringValue;

    private Property<Boolean> booleanValue;

    public Property<String> stringValueProperty() {
        return stringValue;
    }

    public Property<Boolean> booleanValueProperty() {
        return booleanValue;
    }

    public String getStringValue() {
        return stringValue.get();
    }

    public Boolean getBooleanValue() {
        return booleanValue.get();
    }

    public void setStringValue(String stringValue) {
        this.stringValue.set(stringValue);
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue.set(booleanValue);
    }

}
