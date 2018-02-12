package com.canoo.dp.impl.platform.projector.lazy.concrete;

import com.canoo.dp.impl.platform.projector.lazy.AbstractLazyListBean;
import com.canoo.dp.impl.platform.projector.lazy.LazyMediaBean;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class MediaLazyListBean extends AbstractLazyListBean<LazyMediaBean> {

    private ObservableList<LazyMediaBean> loadedContent;

    private ObservableList<LazyMediaBean> selectedValues;

    private Property<LazyMediaBean> selectedValue;

    @Override
    public ObservableList<LazyMediaBean> getLoadedContent() {
        return loadedContent;
    }

    @Override
    public ObservableList<LazyMediaBean> getSelectedValues() {
        return selectedValues;
    }

    @Override
    public Property<LazyMediaBean> selectedValueProperty() {
        return selectedValue;
    }
}
