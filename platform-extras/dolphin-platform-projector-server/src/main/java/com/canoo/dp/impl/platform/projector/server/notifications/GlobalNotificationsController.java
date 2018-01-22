package com.canoo.dp.impl.platform.projector.server.notifications;

import com.canoo.dp.impl.platform.projector.notifications.NotificationBean;
import com.canoo.dp.impl.platform.projector.notifications.NotificationWrapperBean;
import com.canoo.platform.remoting.BeanManager;
import com.canoo.platform.remoting.server.RemotingController;
import com.canoo.platform.remoting.server.RemotingModel;
import com.canoo.platform.remoting.server.event.RemotingEventBus;

import javax.annotation.PostConstruct;

import static com.canoo.dp.impl.platform.projector.notifications.NotificationConstants.GLOBAL_NOTIFICATIONS_CONTROLLER;
import static com.canoo.dp.impl.platform.projector.server.notifications.NotificationsTopics.GLOBAL_NOTIFICATION;

@RemotingController(GLOBAL_NOTIFICATIONS_CONTROLLER)
public class GlobalNotificationsController {

    @RemotingModel
    private NotificationWrapperBean model;

    @Inject
    private RemotingEventBus eventBus;

    @Inject
    private BeanManager beanManager;

    @PostConstruct
    public void init() {
        eventBus.subscribe(GLOBAL_NOTIFICATION, e -> {
            NotificationBean bean = beanManager.create(NotificationBean.class);
            bean.setTitle(e.getData().getTitle());
            bean.setDescription(e.getData().getText());
            bean.setMessageType(e.getData().getMessageType());
            model.setNotification(bean);
        });
    }

}
