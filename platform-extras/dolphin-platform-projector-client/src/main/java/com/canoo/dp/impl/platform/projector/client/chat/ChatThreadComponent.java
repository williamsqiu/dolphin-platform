package com.canoo.dp.impl.platform.projector.client.chat;

import com.canoo.dp.impl.platform.projector.chat.ChatMessage;
import com.canoo.dp.impl.platform.projector.chat.ChatThread;
import com.canoo.platform.remoting.client.javafx.FXBinder;
import javafx.scene.control.ListView;

public class ChatThreadComponent extends ListView<ChatMessage> {

    public ChatThreadComponent(ChatThread thread) {
        setCellFactory(e -> new ChatCell<ChatMessage>());
        FXBinder.bind(getItems()).to(thread.getMessages());
    }
}
