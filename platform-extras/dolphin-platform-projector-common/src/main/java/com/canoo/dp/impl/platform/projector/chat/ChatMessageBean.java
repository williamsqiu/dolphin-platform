package com.canoo.dp.impl.platform.projector.chat;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

import java.util.Date;

@RemotingBean
public class ChatMessageBean<T> implements ChatMessage {

    private Property<Boolean> sendByMe;

    private Property<String> senderName;

    private Property<String> senderAvatarUrl;

    private Property<Date> sendTime;

    private Property<String> message;

    @Override
    public Property<Boolean> sendByMeProperty() {
        return sendByMe;
    }

    @Override
    public Property<String> messageProperty() {
        return message;
    }

    @Override
    public Property<String> senderNameProperty() {
        return senderName;
    }

    @Override
    public Property<String> senderAvatarUrlProperty() {
        return senderAvatarUrl;
    }

    @Override
    public Property<Date> sendTimeProperty() {
        return sendTime;
    }
}
