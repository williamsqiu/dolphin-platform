package com.canoo.dp.impl.platform.projector.lazy;

import com.canoo.dp.impl.platform.projector.media.MediaBean;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class LazyMediaBean extends MediaBean implements LazyListElement {

    private Property<Integer> index;

    @Override
    public Property<Integer> indexProperty() {
        return index;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
