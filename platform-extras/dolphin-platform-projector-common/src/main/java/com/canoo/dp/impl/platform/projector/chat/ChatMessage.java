package com.canoo.dp.impl.platform.projector.chat;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

import java.util.Date;

@RemotingBean
public interface ChatMessage {

    Property<Boolean> sendByMeProperty();

    Property<String> messageProperty();

    Property<String> senderNameProperty();

    Property<String> senderAvatarUrlProperty();

    Property<Date> sendTimeProperty();

    default boolean isSendByMe() {
        return sendByMeProperty().get();
    }
}
