package com.canoo.platform.logger.client.view;

import com.canoo.dolphin.logger.impl.LogMessage;
import javafx.scene.control.ListCell;

public class LogListCell extends ListCell<LogMessage> {

    private final LogListView logListView = new LogListView();

    @Override
    protected void updateItem(LogMessage item, boolean empty) {
        super.updateItem(item, empty);
        logListView.setLogMessage(item);
        setGraphic(logListView);
        setText(null);
    }
}
