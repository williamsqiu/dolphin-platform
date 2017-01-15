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
package com.canoo.dolphin.server.event.impl;

import com.canoo.dolphin.event.Subscription;
import com.canoo.dolphin.server.context.DolphinSessionLifecycleHandler;
import com.canoo.dolphin.server.context.DolphinSessionProvider;
import com.canoo.dolphin.server.event.DolphinEventBus;
import com.canoo.dolphin.server.event.MessageListener;
import com.canoo.dolphin.server.event.Topic;

import java.io.Serializable;

public class DefaultDolphinEventBus extends AbstractEventBus implements DolphinEventBus {

    public DefaultDolphinEventBus(final DolphinSessionProvider sessionProvider, final DolphinSessionLifecycleHandler lifecycleHandler) {
        super(sessionProvider, lifecycleHandler);
    }

    @Override
    protected <T extends Serializable> void publishForOtherSessions(final DolphinEvent<T> event) {
        triggerEventHandling(event);
    }

    @Override
    public <T extends Serializable> Subscription subscribe(final Topic<T> topic, final MessageListener<? super T> handler) {
        return subscribeListenerToTopic(topic, handler);
    }
}
