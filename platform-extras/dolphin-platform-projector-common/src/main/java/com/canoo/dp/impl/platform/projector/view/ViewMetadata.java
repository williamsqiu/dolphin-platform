package com.canoo.dp.impl.platform.projector.view;

import com.canoo.dp.impl.platform.projector.base.WithLayoutMetadata;
import com.canoo.dp.impl.platform.projector.metadata.MetadataUtilities;
import com.canoo.platform.remoting.BeanManager;

import java.util.Optional;

public interface ViewMetadata {

    String JAVAFX_LAYOUT_ORIENTATION = "orientation";

    String JAVAFX_LAYOUT_ORIENTATION_VALUE_HORIZONTAL = "horizontal";

    String JAVAFX_LAYOUT_ORIENTATION_VALUE_VERTICAL = "vertical";

    String JAVAFX_LAYOUT_CONTENT_GROW = "grow";

    String JAVAFX_LAYOUT_MARGIN = "margin";

    String JAVAFX_LAYOUT_BACKGROUND_COLOR = "backgroundColor";

    String JAVAFX_LAYOUT_CONTENT_GROW_VALUE_ALWAYS = "ALWAYS";

    String JAVAFX_LAYOUT_CONTENT_GROW_VALUE_NEVER = "NEVER";

    String JAVAFX_LAYOUT_CONTENT_GROW_VALUE_SOMETIMES = "SOMETIMES";

    static void setMargin(WithLayoutMetadata content, BeanManager beanManager, double margin) {
        MetadataUtilities.getOrCreateDoubleBasedMetadata(JAVAFX_LAYOUT_MARGIN, content, beanManager).setValue(margin);
    }

    static void setBackgroundColor(WithLayoutMetadata content, BeanManager beanManager, String colorInHex) {
        MetadataUtilities.getOrCreateStringBasedMetadata(JAVAFX_LAYOUT_BACKGROUND_COLOR, content, beanManager).setValue(colorInHex);
    }

    static String getBackgroundColor(WithLayoutMetadata content) {
        return  MetadataUtilities.getMetadata(JAVAFX_LAYOUT_BACKGROUND_COLOR, content).map(key -> Optional.ofNullable(key.getValue()).orElse("").toString()).orElse("");
    }

    static void contentShouldNeverGrow(WithLayoutMetadata content, BeanManager beanManager) {
        MetadataUtilities.getOrCreateStringBasedMetadata(JAVAFX_LAYOUT_CONTENT_GROW, content, beanManager).setValue(JAVAFX_LAYOUT_CONTENT_GROW_VALUE_NEVER);
    }

    static void contentShouldAlwaysGrow(WithLayoutMetadata content, BeanManager beanManager) {
        MetadataUtilities.getOrCreateStringBasedMetadata(JAVAFX_LAYOUT_CONTENT_GROW, content, beanManager).setValue(JAVAFX_LAYOUT_CONTENT_GROW_VALUE_ALWAYS);
    }

    static void setOrientationToHorizontal(View view, BeanManager beanManager) {
        MetadataUtilities.getOrCreateStringBasedMetadata(JAVAFX_LAYOUT_ORIENTATION, view, beanManager).setValue(JAVAFX_LAYOUT_ORIENTATION_VALUE_HORIZONTAL);
    }

    static void setOrientationToVertical(View view, BeanManager beanManager) {
        MetadataUtilities.getOrCreateStringBasedMetadata(JAVAFX_LAYOUT_ORIENTATION, view, beanManager).setValue(JAVAFX_LAYOUT_ORIENTATION_VALUE_VERTICAL);
    }

    static boolean isOrientationVertical(View view) {
        return  MetadataUtilities.getMetadata(JAVAFX_LAYOUT_ORIENTATION, view).map(m -> {
            String value = Optional.ofNullable(m.getValue()).orElse("").toString();
            return value.equals(JAVAFX_LAYOUT_ORIENTATION_VALUE_VERTICAL);
        }).orElse(false);
    }

}
