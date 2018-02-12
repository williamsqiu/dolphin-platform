package com.canoo.dp.impl.platform.projector.graph;


import com.canoo.dp.impl.platform.projector.base.Projectable;
import com.canoo.dp.impl.platform.projector.base.WithLayoutMetadata;
import com.canoo.dp.impl.platform.projector.base.WithTitle;
import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class GraphDataBean implements Projectable, WithTitle, WithLayoutMetadata {

    private ObservableList<KeyValue> layoutMetadata;

    private ObservableList<GraphDataValueBean> values;

    private Property<String> title;

    private Property<String> keyLabel;

    private Property<String> dataLabel;

    public ObservableList<KeyValue> getLayoutMetadata() {
        return layoutMetadata;
    }

    public ObservableList<GraphDataValueBean> getValues() {
        return values;
    }

    @Override
    public Property<String> titleProperty() {
        return title;
    }
}
