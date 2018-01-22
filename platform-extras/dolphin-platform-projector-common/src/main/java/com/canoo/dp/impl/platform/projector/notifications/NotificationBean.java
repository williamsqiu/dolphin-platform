package com.canoo.dp.impl.platform.projector.notifications;

import com.canoo.dp.impl.platform.projector.message.Message;
import com.canoo.dp.impl.platform.projector.message.MessageType;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class NotificationBean implements Message {

    private Property<MessageType> messageType;

    private Property<String> description;

    private Property<String> title;

    @Override
    public Property<MessageType> messageTypeProperty() {
        return messageType;
    }

    @Override
    public Property<String> descriptionProperty() {
        return description;
    }

    @Override
    public Property<String> titleProperty() {
        return title;
    }
}
