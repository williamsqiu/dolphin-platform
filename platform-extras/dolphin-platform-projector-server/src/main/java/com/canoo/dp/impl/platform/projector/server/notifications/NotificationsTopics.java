package com.canoo.dp.impl.platform.projector.server.notifications;

import com.canoo.dp.impl.platform.projector.notifications.NotificationData;
import com.canoo.platform.remoting.server.event.Topic;

/**
 * Created by hendrikebbers on 31.05.16.
 */
public interface NotificationsTopics {

    Topic<NotificationData> GLOBAL_NOTIFICATION = Topic.create();
}
