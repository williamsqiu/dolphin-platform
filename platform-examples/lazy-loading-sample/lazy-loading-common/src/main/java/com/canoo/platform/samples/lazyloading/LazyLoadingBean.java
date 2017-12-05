package com.canoo.platform.samples.lazyloading;

import com.canoo.platform.remoting.DolphinBean;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;

@DolphinBean
public class LazyLoadingBean {

    private Property<Integer> requestedSize;

    private Property<Boolean> loading;

    private ObservableList<LazyLoadingItem> items;

    public Integer getRequestedSize() {
        return requestedSize.get();
    }

    public Property<Integer> requestedSizeProperty() {
        return requestedSize;
    }

    public void setRequestedSize(Integer requestedSize) {
        this.requestedSize.set(requestedSize);
    }

    public Boolean getLoading() {
        return loading.get();
    }

    public Property<Boolean> loadingProperty() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading.set(loading);
    }

    public ObservableList<LazyLoadingItem> getItems() {
        return items;
    }
}
