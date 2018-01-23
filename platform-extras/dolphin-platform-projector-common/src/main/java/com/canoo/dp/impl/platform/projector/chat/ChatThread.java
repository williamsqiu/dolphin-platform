package com.canoo.dp.impl.platform.projector.chat;

import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface ChatThread {

    ObservableList<ChatMessage> getMessages();

}
