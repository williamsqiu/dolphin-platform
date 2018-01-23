package com.canoo.dp.impl.platform.projector.form;

import com.canoo.dp.impl.platform.projector.action.Action;
import com.canoo.dp.impl.platform.projector.base.Icon;
import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class FormBean implements Form {

    private ObservableList<FormSection> sections;

    private ObservableList<Action> actions;

    private Property<String> description;

    private Property<Icon> icon;

    private ObservableList<KeyValue> layoutMetadata;

    private Property<String> title;

    @Override
    public ObservableList<FormSection> getSections() {
        return sections;
    }

    @Override
    public ObservableList<Action> getActions() {
        return actions;
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
