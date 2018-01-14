package com.canoo.platform.samples.lazyloading;

import com.canoo.platform.remoting.RemotingBean;
import com.canoo.platform.remoting.Property;

import java.time.LocalDateTime;

@RemotingBean
public class LazyLoadingItem {

    private Property<String> name;

    private Property<LocalDateTime> creationDate;

    public String getName() {
        return name.get();
    }

    public Property<String> nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public LocalDateTime getCreationDate() {
        return creationDate.get();
    }

    public Property<LocalDateTime> creationDateProperty() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate.set(creationDate);
    }

    @Override
    public String toString() {
        return "Item " + getName() + " (loaded: " + getCreationDate() + ")";
    }
}
