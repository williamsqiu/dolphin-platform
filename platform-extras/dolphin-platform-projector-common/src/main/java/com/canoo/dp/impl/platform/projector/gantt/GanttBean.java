package com.canoo.dp.impl.platform.projector.gantt;

import com.canoo.dp.impl.platform.projector.base.Projectable;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class GanttBean implements Projectable {

    private Property<RowBean> root;

    public Property<RowBean> rootProperty() {
        return root;
    }

    public RowBean getRoot() {
        return rootProperty().get();
    }

    public void setRoot(RowBean root) {
        rootProperty().set(root);
    }
}
