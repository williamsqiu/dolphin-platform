package com.canoo.dp.impl.platform.projector.media;

import com.canoo.dp.impl.platform.projector.base.WithDescription;
import com.canoo.dp.impl.platform.projector.base.WithLayoutMetadata;
import com.canoo.dp.impl.platform.projector.base.WithTitle;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface Media extends WithTitle, WithDescription, WithLayoutMetadata {

    Property<String> imageUrlProperty();

    default String getImageUrl() {
        return imageUrlProperty().get();
    }

    default void setImageUrl(String imageUrl) {
        imageUrlProperty().set(imageUrl);
    }
}
