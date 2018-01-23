package com.canoo.dp.impl.platform.projector.chat;

import com.canoo.dp.impl.platform.projector.base.Projectable;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class ChatThreadBean implements ChatThread, Projectable {

    private ObservableList<ChatMessage> messages;

    @Override
    public ObservableList<ChatMessage> getMessages() {
        return messages;
    }
}
