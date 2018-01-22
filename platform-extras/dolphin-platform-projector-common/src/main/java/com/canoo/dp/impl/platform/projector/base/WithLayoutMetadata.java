package com.canoo.dp.impl.platform.projector.base;

import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface WithLayoutMetadata {

    ObservableList<KeyValue> getLayoutMetadata();

}
