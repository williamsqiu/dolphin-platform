package com.canoo.dp.impl.platform.projector.client.table;

import com.canoo.dp.impl.platform.core.ReflectionHelper;
import com.canoo.dp.impl.platform.projector.table.PropertyColumn;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.client.javafx.FXBinder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PropertyTableColumn<S, T> extends AbstractTableColumn<S, T> {

    public PropertyTableColumn(PropertyColumn column) {
        super(column);

        setCellValueFactory(e -> {
            try {
                Property<T> property = getPropertyForRowItem(e.getValue(), column.getPropertyName());
                ObjectProperty<T> javaFXProperty = new SimpleObjectProperty<T>();
                FXBinder.bind(javaFXProperty).bidirectionalTo(property);
                return javaFXProperty;
                //TODO: No Unbind here. This will cause memory leak!
            } catch (IllegalAccessException e1) {
                throw new RuntimeException("TODO", e1);
            }
        });

    }

    private Property<T> getPropertyForRowItem(S rowItem, String propertyName) throws IllegalAccessException {
        return (Property<T>) ReflectionHelper.getInheritedDeclaredField(rowItem.getClass(), propertyName).get(rowItem);
    }

}
