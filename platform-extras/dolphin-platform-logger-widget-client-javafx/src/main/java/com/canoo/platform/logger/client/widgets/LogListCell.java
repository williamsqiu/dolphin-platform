package com.canoo.platform.logger.client.widgets;

import com.canoo.platform.logging.spi.LogMessage;
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
