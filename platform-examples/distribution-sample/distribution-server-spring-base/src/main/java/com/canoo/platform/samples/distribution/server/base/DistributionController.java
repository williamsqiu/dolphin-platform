/*
 * Copyright 2015-2017 Canoo Engineering AG.
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
package com.canoo.platform.samples.distribution.server.base;

import com.canoo.platform.remoting.server.DolphinAction;
import com.canoo.platform.remoting.server.DolphinController;
import com.canoo.platform.remoting.server.DolphinModel;
import com.canoo.platform.remoting.server.Param;
import com.canoo.platform.remoting.server.event.RemotingEventBus;
import com.canoo.platform.remoting.server.event.EventFilter;
import com.canoo.platform.remoting.server.event.EventFilterFactory;
import com.canoo.platform.samples.distribution.common.model.ToDoList;
import com.canoo.platform.server.client.ClientSession;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static com.canoo.platform.samples.distribution.common.DistributionAppConstants.ADD_ACTION;
import static com.canoo.platform.samples.distribution.common.DistributionAppConstants.ITEM_PARAM;
import static com.canoo.platform.samples.distribution.common.DistributionAppConstants.REMOVE_ACTION;
import static com.canoo.platform.samples.distribution.common.DistributionAppConstants.CONTROLLER_NAME;
import static com.canoo.platform.samples.distribution.server.base.DistributionEventTopics.ITEM_ADDED;
import static com.canoo.platform.samples.distribution.server.base.DistributionEventTopics.ITEM_REMOVED;

@DolphinController(CONTROLLER_NAME)
public class DistributionController {

    private final RemotingEventBus eventBus;

    private final ClientSession session;

    @DolphinModel
    private ToDoList toDoList;

    @Inject
    public DistributionController(final RemotingEventBus eventBus, final ClientSession session) {
        this.eventBus = eventBus;
        this.session = session;
    }

    @PostConstruct
    public void onInit() {
        final EventFilter excludeMe = EventFilterFactory.excludeClientSessions(session.getId());
        eventBus.subscribe(ITEM_REMOVED, message -> removeItem(message.getData()), excludeMe);
        eventBus.subscribe(ITEM_ADDED, message -> addItem(message.getData()), excludeMe);
    }

    @DolphinAction(ADD_ACTION)
    public void onItemAddAction() {
        final String itemName = toDoList.getNewItemText();
        toDoList.setNewItemText("");

        addItem(itemName);
        eventBus.publish(ITEM_ADDED, itemName);
    }

    @DolphinAction(REMOVE_ACTION)
    public void onItemRemovedAction(@Param(ITEM_PARAM) final String name) {
        removeItem(name);
        eventBus.publish(ITEM_REMOVED, name);
    }

    private void addItem(final String name) {
        toDoList.getItems().add(name);
    }

    private void removeItem(final String name) {
        toDoList.getItems().remove(name);
    }
}
