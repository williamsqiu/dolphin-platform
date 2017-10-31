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

import com.canoo.platform.remoting.BeanManager;
import com.canoo.platform.remoting.server.DolphinAction;
import com.canoo.platform.remoting.server.DolphinController;
import com.canoo.platform.remoting.server.DolphinModel;
import com.canoo.platform.remoting.server.Param;
import com.canoo.platform.remoting.server.event.DolphinEventBus;
import com.canoo.platform.samples.distribution.common.model.ToDoItem;
import com.canoo.platform.samples.distribution.common.model.ToDoList;
import com.canoo.platform.remoting.server.event.EventSessionFilterFactory;
import com.canoo.platform.server.client.ClientSession;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Optional;

import static com.canoo.platform.samples.distribution.common.DistributionAppConstants.ADD_ACTION;
import static com.canoo.platform.samples.distribution.common.DistributionAppConstants.CHANGE_ACTION;
import static com.canoo.platform.samples.distribution.common.DistributionAppConstants.ITEM_PARAM;
import static com.canoo.platform.samples.distribution.common.DistributionAppConstants.REMOVE_ACTION;
import static com.canoo.platform.samples.distribution.common.DistributionAppConstants.TODO_CONTROLLER_NAME;

@DolphinController(TODO_CONTROLLER_NAME)
public class DistributionController {

    private final BeanManager beanManager;

    private final DolphinEventBus eventBus;

    private final DistributionItemStore todoItemStore;

    private final ClientSession session;

    @DolphinModel
    private ToDoList toDoList;

    @Inject
    public DistributionController(final BeanManager beanManager, final DolphinEventBus eventBus, final DistributionItemStore todoItemStore, final ClientSession session) {
        this.beanManager = beanManager;
        this.eventBus = eventBus;
        this.todoItemStore = todoItemStore;
        this.session = session;
    }

    @PostConstruct
    public void onInit() {
        eventBus.subscribe(DistributionEventTopics.ITEM_MARK_CHANGED, message -> updateItemState(message.getData()), EventSessionFilterFactory.excludeClientSessions(session.getId()));
        eventBus.subscribe(DistributionEventTopics.ITEM_REMOVED, message -> removeItem(message.getData()), EventSessionFilterFactory.excludeClientSessions(session.getId()));
        eventBus.subscribe(DistributionEventTopics.ITEM_ADDED, message -> addItem(message.getData()), EventSessionFilterFactory.excludeClientSessions(session.getId()));
        todoItemStore.itemNameStream().forEach(name -> addItem(name));
    }

    @DolphinAction(ADD_ACTION)
    public void onItemAddAction() {
        final String itemName = toDoList.getNewItemText();
        toDoList.setNewItemText("");

        addItem(itemName);
        eventBus.publish(DistributionEventTopics.ITEM_ADDED, toDoList.getNewItemText());
    }

    @DolphinAction(CHANGE_ACTION)
    public void onItemStateChangedAction(@Param(ITEM_PARAM) final String name) {
        updateItemState(name);
        eventBus.publish(DistributionEventTopics.ITEM_MARK_CHANGED, name);
    }

    @DolphinAction(REMOVE_ACTION)
    public void onItemRemovedAction(@Param(ITEM_PARAM) final String name) {
        removeItem(name);
        eventBus.publish(DistributionEventTopics.ITEM_REMOVED, name);
    }

    private void addItem(final String name) {
        toDoList.getItems().add(beanManager.create(ToDoItem.class).withText(name).withState(todoItemStore.getState(name)));
    }

    private void removeItem(final String name) {
        getItemByName(name).ifPresent(i -> toDoList.getItems().remove(i));
    }

    private void updateItemState(final String name) {
        getItemByName(name).ifPresent(i -> i.setCompleted(todoItemStore.getState(name)));
    }

    private Optional<ToDoItem> getItemByName(final String name) {
        return toDoList.getItems().stream().filter(i -> i.getText().equals(name)).findAny();
    }
}
