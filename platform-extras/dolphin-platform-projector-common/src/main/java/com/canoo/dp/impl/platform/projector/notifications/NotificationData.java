package com.canoo.dp.impl.platform.projector.notifications;

import com.canoo.dp.impl.platform.projector.message.MessageType;

import java.io.Serializable;

public class NotificationData implements Serializable{

    private String title;

    private String text;

    private MessageType messageType;

    public NotificationData(String title, String text, MessageType messageType) {
        this.title = title;
        this.text = text;
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
