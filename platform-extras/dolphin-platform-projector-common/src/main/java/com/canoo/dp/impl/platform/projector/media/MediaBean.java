package com.canoo.dp.impl.platform.projector.media;

import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class MediaBean implements Media {

    private Property<String> imageUrl;

    private Property<String> description;

    private ObservableList<KeyValue> layoutMetadata;

    private Property<String> title;

    @Override
    public Property<String> imageUrlProperty() {
        return imageUrl;
    }

    @Override
    public Property<String> descriptionProperty() {
        return description;
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
