package com.canoo.dp.impl.platform.projector.gantt;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

import java.util.Date;

@RemotingBean
public class ActivityBean {

    private Property<String> layerName;

    private Property<String> name;

    private Property<Date> start;

    private Property<Date> end;

    private Property<BarType> type;

    public Property<String> layerNameProperty() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName.set(layerName);
    }

    public Property<String> nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Property<Date> startProperty() {
        return start;
    }

    public Date getStart() {
        return start.get();
    }

    public void setStart(Date start) {
        this.start.set(start);
    }

    public Property<Date> endProperty() {
        return end;
    }

    public Date getEnd() {
        return end.get();
    }

    public void setEnd(Date end) {
        this.end.set(end);
    }

    public Property<BarType> typeProperty() {
        return type;
    }

    public BarType getType() {
        return type.get();
    }

    public void setType(final BarType barType) {
        type.set(barType);
    }
}
