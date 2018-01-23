package com.canoo.dp.impl.platform.projector.view;

import com.canoo.dp.impl.platform.projector.base.Icon;
import com.canoo.dp.impl.platform.projector.base.Projectable;
import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class ViewBean implements View {

    private ObservableList<Projectable> content;

    private Property<String> description;

    private Property<Icon> icon;

    private ObservableList<KeyValue> layoutMetadata;

    private Property<String> title;

    @Override
    public ObservableList<Projectable> getContent() {
        return content;
    }

    @Override
    public Property<String> descriptionProperty() {
        return description;
    }

    @Override
    public Property<Icon> iconProperty() {
        return icon;
    }

    @Override
    public ObservableList<KeyValue> getLayoutMetadata() {
        return layoutMetadata;
    }

    @Override
    public Property<String> titleProperty() {
        return title;
    }
}
