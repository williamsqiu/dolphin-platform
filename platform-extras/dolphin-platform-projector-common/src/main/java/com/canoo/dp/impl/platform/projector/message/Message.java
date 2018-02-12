package com.canoo.dp.impl.platform.projector.message;

import com.canoo.dp.impl.platform.projector.base.WithDescription;
import com.canoo.dp.impl.platform.projector.base.WithTitle;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface Message extends WithTitle, WithDescription {

    Property<MessageType> messageTypeProperty();

    default MessageType getMessageType() {
        return messageTypeProperty().get();
    }

    default void setMessageType(MessageType type) {
        messageTypeProperty().set(type);
    }
}
