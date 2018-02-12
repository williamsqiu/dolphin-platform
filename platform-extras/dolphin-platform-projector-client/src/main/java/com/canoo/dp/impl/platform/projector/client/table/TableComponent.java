package com.canoo.dp.impl.platform.projector.client.table;

import com.canoo.dp.impl.platform.projector.table.PropertyColumn;
import com.canoo.dp.impl.platform.projector.table.Table;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.client.javafx.FXBinder;
import javafx.scene.control.TableView;

public class TableComponent<S> extends TableView<S> {

    public TableComponent(Property<Table<S>> tableProperty) {
        tableProperty.onChanged(e -> {
            if(e.getNewValue() != null) {
                update(e.getNewValue());
            }
        });
        if(tableProperty.get() != null) {
            update(tableProperty.get());
        }
    }

    public TableComponent(Table<S> table) {
        update(table);
    }

    private void update(Table<S> table) {
        FXBinder.bind(getItems()).to(table.getItems());
        FXBinder.bind(getColumns()).to(table.getColumns(), column -> {
            if(column instanceof PropertyColumn) {
                return new PropertyTableColumn<S, Object>((PropertyColumn) column);
            } else {
                //TODO: Action Column, Error
                return null;
            }
        });
        FXBinder.bind(table.selectedValueProperty()).to(getSelectionModel().selectedItemProperty());
    }
}
