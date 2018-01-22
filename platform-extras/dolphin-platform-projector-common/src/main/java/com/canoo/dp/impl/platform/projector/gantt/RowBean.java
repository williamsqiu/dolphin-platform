package com.canoo.dp.impl.platform.projector.gantt;

import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class RowBean {

    private Property<String> name;

    private ObservableList<RowBean> children;

    private ObservableList<ActivityBean> activityBeen;

    public Property<String> nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableList<RowBean> getChildren() {
        return children;
    }

    public ObservableList<ActivityBean> getActivityBeen() {
        return activityBeen;
    }
}
