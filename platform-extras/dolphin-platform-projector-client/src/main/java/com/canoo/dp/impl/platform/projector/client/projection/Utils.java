package com.canoo.dp.impl.platform.projector.client.projection;

import com.canoo.dp.impl.platform.projector.base.WithDescription;
import com.canoo.platform.remoting.client.javafx.FXWrapper;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;

public class Utils {

    public static void registerTooltip(Control node, WithDescription bean) {
        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(FXWrapper.wrapStringProperty(bean.descriptionProperty()));

        FXWrapper.wrapStringProperty(bean.descriptionProperty()).addListener(e -> {
            if (bean.getDescription() == null || bean.getDescription().isEmpty()) {
                node.setTooltip(null);
            } else {
                node.setTooltip(tooltip);
            }
        });

        if (bean.getDescription() == null || bean.getDescription().isEmpty()) {
            node.setTooltip(null);
        } else {
            node.setTooltip(tooltip);
        }
    }
}
