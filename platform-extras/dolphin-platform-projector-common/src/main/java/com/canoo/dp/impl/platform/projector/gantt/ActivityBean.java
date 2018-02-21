/*
 * Copyright 2015-2018 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
