package com.canoo.dolphin.integration.enterprise;

import com.canoo.dolphin.mapping.DolphinBean;
import com.canoo.dolphin.mapping.Property;

@DolphinBean
public class EnterpriseTestBean {

    private Property<Boolean> postConstructCalled;

    private Property<Boolean> preDestroyCalled;

    public Property<Boolean> postConstructCalledProperty() {
        return postConstructCalled;
    }

    public Property<Boolean> preDestroyCalledProperty() {
        return preDestroyCalled;
    }

    public void setPostConstructCalled(Boolean postConstructCalled) {
        this.postConstructCalled.set(postConstructCalled);
    }

    public void setPreDestroyCalled(Boolean preDestroyCalled) {
        this.preDestroyCalled.set(preDestroyCalled);
    }

    public Boolean getPostConstructCalled() {
        return postConstructCalled.get();
    }

    public Boolean getPreDestroyCalled() {
        return preDestroyCalled.get();
    }
}
