package com.canoo.dp.impl.platform.projector.client.notifications;

import com.canoo.dp.impl.platform.projector.message.MessageType;
import com.canoo.dp.impl.platform.projector.notifications.NotificationBean;
import com.canoo.dp.impl.platform.projector.notifications.NotificationData;
import com.canoo.dp.impl.platform.projector.notifications.NotificationWrapperBean;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.ControllerProxy;
import org.controlsfx.control.Notifications;

import java.util.concurrent.CompletableFuture;

import static com.canoo.dp.impl.platform.projector.notifications.NotificationConstants.GLOBAL_NOTIFICATIONS_CONTROLLER;

public class GlobalNotificationsHandler {

    private final ClientContext clientContext;

    public GlobalNotificationsHandler(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    public CompletableFuture<Void> init() {
        CompletableFuture<ControllerProxy<NotificationWrapperBean>> controllerFuture =  clientContext.createController(GLOBAL_NOTIFICATIONS_CONTROLLER);
        return controllerFuture.handle((c, e) -> {
            if(e != null) {
                //TODO
            }
            NotificationWrapperBean model = c.getModel();
            model.notificationProperty().onChanged(event -> {
                NotificationBean bean = model.getNotification();
                if(bean != null) {
                    showNotification(new NotificationData(bean.getTitle(), bean.getDescription(), bean.getMessageType()));
                }
            });

            NotificationBean bean = model.getNotification();
            if(bean != null) {
                showNotification(new NotificationData(bean.getTitle(), bean.getDescription(), bean.getMessageType()));
            }
            return null;
        });
    }

    protected void showNotification(NotificationData notification) {
        Notifications notificationPopup = Notifications.create().title(notification.getTitle()).text(notification.getText());
        if(MessageType.SUCCESS.equals(notification.getMessageType())) {
            notificationPopup.showConfirm();
        } else if(MessageType.INFO.equals(notification.getMessageType())) {
            notificationPopup.showInformation();
        } else if(MessageType.WARNING.equals(notification.getMessageType())) {
            notificationPopup.showWarning();
        } else if(MessageType.ERROR.equals(notification.getMessageType())) {
            notificationPopup.showError();
        } else {
            notificationPopup.show();
        }
    }

    public static CompletableFuture<Void> create(ClientContext clientContext) {
        return new GlobalNotificationsHandler(clientContext).init();
    }
}
