package com.canoo.dp.impl.platform.projector.client.chat;

import com.canoo.dp.impl.platform.projector.chat.ChatMessage;
import com.canoo.platform.core.functional.Binding;
import com.canoo.platform.remoting.client.javafx.FXBinder;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;

import java.util.Optional;

public class ChatCell<T extends ChatMessage> extends ListCell<T> {

    private ChatBubble bubble;

    private Binding sendByMeBinding;

    private Binding messageBinding;

    public ChatCell() {
        bubble = new ChatBubble();
        setGraphic(bubble);
        setText("");
        getStyleClass().add("chat-cell");

        listViewProperty().addListener(e -> {
            bubble.minWidthProperty().unbind();
            if(listViewProperty().get() != null) {
                bubble.minWidthProperty().bind(listViewProperty().get().widthProperty().subtract(32));
                bubble.prefWidthProperty().bind(listViewProperty().get().widthProperty().subtract(32));
                bubble.maxWidthProperty().bind(listViewProperty().get().widthProperty().subtract(32));
            }
        });

    }

    @Override
    public Orientation getContentBias() {
        return Orientation.HORIZONTAL;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        Optional.ofNullable(messageBinding).ifPresent(b -> b.unbind());
        Optional.ofNullable(sendByMeBinding).ifPresent(b -> b.unbind());

        if(item == null || empty) {
            bubble.setVisible(false);
        } else {
            bubble.setVisible(true);
            messageBinding = FXBinder.bind(bubble.messageProperty()).to(item.messageProperty());
            sendByMeBinding = FXBinder.bind(bubble.sendByMeProperty()).to(item.sendByMeProperty());
        }
    }
}
