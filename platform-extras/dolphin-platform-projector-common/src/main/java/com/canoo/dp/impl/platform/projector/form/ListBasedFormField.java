package com.canoo.dp.impl.platform.projector.form;

import com.canoo.dp.impl.platform.projector.base.WithMultiSelection;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface ListBasedFormField<T> extends FormField<T>, WithMultiSelection<T> {

    ObservableList<T> getItems();

}
