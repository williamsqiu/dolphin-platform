package com.canoo.dp.impl.platform.projector.client.table;

import com.canoo.dp.impl.platform.projector.table.Column;
import com.canoo.platform.remoting.client.javafx.FXBinder;
import javafx.scene.control.TableColumn;

public abstract class AbstractTableColumn<S, T> extends TableColumn<S, T> {

    public AbstractTableColumn(Column column) {
        FXBinder.bind(textProperty()).to(column.titleProperty());
        FXBinder.bind(editableProperty()).to(column.editableProperty());
        FXBinder.bind(sortableProperty()).to(column.sortableProperty());
    }
}
