package com.canoo.dp.impl.platform.projector.client.projection;

import com.canoo.dp.impl.platform.projector.client.lazy.LazyLoadingBehavior;
import com.canoo.dp.impl.platform.projector.client.media.SimpleMediaListCell;
import com.canoo.dp.impl.platform.projector.lazy.LazyMediaBean;
import com.canoo.dp.impl.platform.projector.lazy.concrete.MediaLazyListBean;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.ControllerProxy;
import javafx.scene.Parent;
import javafx.scene.control.ListView;

public class LazyListFactory implements ProjectionFactory<MediaLazyListBean> {

    @Override
    public Parent createProjection(Projector projector, ClientContext clientContext, ControllerProxy controllerProxy, MediaLazyListBean projectable) {
        ListView<LazyMediaBean> listView = new ListView<>();
        listView.setCellFactory(SimpleMediaListCell.createDefaultCallback());
        LazyLoadingBehavior<LazyMediaBean> lazyLoadingBehavior = new LazyLoadingBehavior<>(listView);
        lazyLoadingBehavior.setModel(projectable);
        return listView;
    }
}
