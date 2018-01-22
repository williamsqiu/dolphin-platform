package com.canoo.dp.impl.platform.projector.base;

import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface WithMultiResult<T> extends WithResult<T> {

    ObservableList<T> getResults();

}
