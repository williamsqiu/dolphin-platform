package com.canoo.dp.impl.platform.projector.server.notifications;

import com.canoo.dp.impl.platform.projector.message.MessageType;
import com.canoo.dp.impl.platform.projector.notifications.NotificationData;
import com.canoo.platform.remoting.server.event.RemotingEventBus;

import static com.canoo.dp.impl.platform.projector.server.notifications.NotificationsTopics.GLOBAL_NOTIFICATION;

public class NotificationsHandler {

    public static void sendGlobalNotification(final RemotingEventBus eventBus, final String title, final String text, final MessageType messageType) {
        eventBus.publish(GLOBAL_NOTIFICATION, new NotificationData(title, text, messageType));
    }

}
