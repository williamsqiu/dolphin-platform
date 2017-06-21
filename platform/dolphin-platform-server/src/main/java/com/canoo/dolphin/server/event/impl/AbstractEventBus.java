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
import com.canoo.dolphin.server.DolphinSession;
import com.canoo.dolphin.server.context.DolphinContextUtils;
import com.canoo.dolphin.server.context.DolphinSessionLifecycleHandler;
import com.canoo.dolphin.server.context.DolphinSessionProvider;
import com.canoo.dolphin.server.event.DolphinEventBus;
import com.canoo.dolphin.server.event.EventSessionFilter;
import com.canoo.dolphin.server.event.MessageListener;
import com.canoo.dolphin.server.event.Topic;
import com.canoo.dolphin.util.Assert;
import com.canoo.dolphin.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractEventBus implements DolphinEventBus {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractEventBus.class);

    private final DolphinSessionProvider sessionProvider;

    private final Map<Topic<?>, List<MessageListener<?>>> topicToListenerMap = new ConcurrentHashMap<>();

    private final Map<MessageListener<?>, String> listenerToSessionMap = new ConcurrentHashMap<>();

    private final Map<String, List<Subscription>> sessionStore = new ConcurrentHashMap<>();

    protected AbstractEventBus(final DolphinSessionProvider sessionProvider, final DolphinSessionLifecycleHandler lifecycleHandler) {
        this.sessionProvider = Assert.requireNonNull(sessionProvider, "sessionProvider");
        Assert.requireNonNull(lifecycleHandler, "lifecycleHandler").addSessionDestroyedListener(new Callback<DolphinSession>() {
            @Override
            public void call(final DolphinSession dolphinSession) {
                onSessionEnds(dolphinSession.getId());
            }
        });
    }

    @Override
    public <T extends Serializable> void publish(final Topic<T> topic, final T data) {
        publish(topic, data, DefaultEventSessionFilter.getInstance());
    }

    @Override
    public <T extends Serializable> void publish(final Topic<T> topic, final T data, final EventSessionFilter filter) {
        publishData(topic, data, filter);
    }

    private <T extends Serializable> List<MessageListener<T>> getListenersForSessionAndTopic(final String sessionId, final Topic<T> topic) {
        Assert.requireNonBlank(sessionId, "sessionId");
        Assert.requireNonNull(topic, "topic");

        final List<MessageListener<?>> handlers = topicToListenerMap.get(topic);
        if (handlers == null) {
            return Collections.emptyList();
        }

        final List<MessageListener<T>> ret = new ArrayList<>();
        for (MessageListener<?> listener : handlers) {
            if (sessionId.equals(listenerToSessionMap.get(listener))) {
                ret.add((MessageListener<T>) listener);
            }
        }
        return ret;
    }

    private <T extends Serializable> void publishData(final Topic<T> topic, final T data, final EventSessionFilter filter) {
        final DolphinSession currentSession = getCurrentDolphinSession();
        final DolphinEvent event = new DolphinEvent(currentSession != null ? currentSession.getId() : null, new DefaultMessage(topic, data, System.currentTimeMillis()), filter);
        if (currentSession != null && (filter == null || filter.shouldHandleEvent(currentSession.getId()))) {
            final List<MessageListener<T>> listenersInCurrentSession = getListenersForSessionAndTopic(currentSession.getId(), topic);
            for (MessageListener<T> listener : listenersInCurrentSession) {
                listener.onMessage(event.getMessage());
            }
        }

        publishForOtherSessions(event);
    }

    protected abstract <T extends Serializable> void publishForOtherSessions(DolphinEvent<T> event);

    protected <T extends Serializable> void triggerEventHandling(final DolphinEvent<T> event) {
        Assert.requireNonNull(event, "event");

        final Topic<T> topic = event.getTopic();
        LOG.trace("Handling data for topic {}", topic.getName());
        final List<MessageListener<?>> listeners = topicToListenerMap.get(topic);
        if (listeners != null) {
            for (final MessageListener<?> listener : listeners) {
                final String sessionId = listenerToSessionMap.get(listener);
                if (sessionId == null) {
                    throw new RuntimeException("Internal Error! No session id defined for event bus listener!");
                }
                if (sessionId.equals(event.getSenderSessionId())) {
                    //This listener was already called at the publish call
                    // since the event was called from the same session
                    LOG.trace("Event listener for topic {} was already called in Dolphin Platform context {}", topic.getName(), sessionId);
                } else {
                    LOG.trace("Event listener for topic {} must be called later in Dolphin Platform context {}", topic.getName(), sessionId);
                    DolphinContextUtils.runLaterInClientSession(sessionId, new Runnable() {

                        @Override
                        public void run() {
                            LOG.trace("Calling event listener for topic {} in Dolphin Platform context {}", topic.getName(), sessionId);
                            final EventSessionFilter sessionFilter = event.getSessionFilter();
                            if (sessionFilter == null || sessionFilter.shouldHandleEvent(sessionId)) {
                                ((MessageListener<T>) listener).onMessage(event.getMessage());
                            }
                        }
                    });
                }
            }
        }
    }

    public <T extends Serializable> Subscription subscribe(final Topic<T> topic, final MessageListener<? super T> listener) {
        Assert.requireNonNull(topic, "topic");
        Assert.requireNonNull(listener, "listener");

        final DolphinSession subscriptionSession = getCurrentDolphinSession();
        if (subscriptionSession == null) {
            throw new IllegalStateException("Subscription can only be done from Dolphin Context!");
        }
        final String subscriptionSessionId = subscriptionSession.getId();
        LOG.trace("Adding subscription for topic {} in Dolphin Platform context {}", topic.getName(), subscriptionSessionId);
        List<MessageListener<?>> listeners = topicToListenerMap.get(topic);
        if (listeners == null) {
            listeners = new CopyOnWriteArrayList<>();
            topicToListenerMap.put(topic, listeners);
        }
        listeners.add(listener);
        listenerToSessionMap.put(listener, subscriptionSessionId);
        final Subscription subscription = new Subscription() {
            @Override
            public void unsubscribe() {
                LOG.trace("Removing subscription for topic {} in Dolphin Platform context {}", topic.getName(), subscriptionSessionId);
                final List<MessageListener<?>> listeners = topicToListenerMap.get(topic);
                if (listeners != null) {
                    listeners.remove(listener);
                }
                listenerToSessionMap.remove(listener);
                removeSubscriptionForSession(this, subscriptionSessionId);
            }
        };
        addSubscriptionForSession(subscription, subscriptionSessionId);
        return subscription;
    }

    private void addSubscriptionForSession(final Subscription subscription, final String dolphinSessionId) {
        List<Subscription> subscriptionsForSession = sessionStore.get(dolphinSessionId);
        if (subscriptionsForSession == null) {
            subscriptionsForSession = new CopyOnWriteArrayList<>();
            sessionStore.put(dolphinSessionId, subscriptionsForSession);
        }
        subscriptionsForSession.add(subscription);
    }

    private void removeSubscriptionForSession(final Subscription subscription, final String dolphinSessionId) {
        final List<Subscription> subscriptionsForSession = sessionStore.get(dolphinSessionId);
        if (subscriptionsForSession != null) {
            subscriptionsForSession.remove(subscription);
        }
    }

    private void onSessionEnds(final String dolphinSessionId) {
        Assert.requireNonBlank(dolphinSessionId, "dolphinSessionId");
        final List<Subscription> subscriptions = sessionStore.get(dolphinSessionId);
        if (subscriptions != null) {
            for (Subscription subscription : subscriptions) {
                subscription.unsubscribe();
            }
        }
    }

    private DolphinSession getCurrentDolphinSession() {
        return sessionProvider.getCurrentDolphinSession();
    }
}
