package com.canoo.dp.impl.platform.projector.graph;

import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class GraphDataValueBean {

    private Property<String> name;

    private Property<Double> value;

    private ObservableList<KeyValue> layoutMetadata;

    public Property<String> nameProperty() {
        return name;
    }

    public Property<Double> valueProperty() {
        return value;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Double getValue() {
        return value.get();
    }

    public void setValue(Double value) {
        this.value.set(value);
    }

    public ObservableList<KeyValue> getLayoutMetadata() {
        return layoutMetadata;
    }
}
