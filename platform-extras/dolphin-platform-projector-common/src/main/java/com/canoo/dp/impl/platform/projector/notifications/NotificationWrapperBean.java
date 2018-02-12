package com.canoo.dp.impl.platform.projector.notifications;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class NotificationWrapperBean {

    private Property<NotificationBean> notification;

    public Property<NotificationBean> notificationProperty() {
        return notification;
    }

    public void setNotification(NotificationBean notification) {
        notificationProperty().set(notification);
    }

    public NotificationBean getNotification() {
        return notificationProperty().get();
    }
}
