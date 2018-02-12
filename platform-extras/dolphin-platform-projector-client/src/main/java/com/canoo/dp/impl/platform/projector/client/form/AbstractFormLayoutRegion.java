package com.canoo.dp.impl.platform.projector.client.form;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Region;

public class AbstractFormLayoutRegion extends Region {

    private ReadOnlyDoubleWrapper minTitleWidth;

    private ReadOnlyDoubleWrapper prefTitleWidth;

    private ReadOnlyDoubleWrapper maxTitleWidth;

    private ReadOnlyDoubleWrapper minEditorWidth;

    private ReadOnlyDoubleWrapper prefEditorWidth;

    private ReadOnlyDoubleWrapper maxEditorWidth;

    private DoubleProperty titleWidth;

    public AbstractFormLayoutRegion() {
        minTitleWidth = new ReadOnlyDoubleWrapper();
        prefTitleWidth = new ReadOnlyDoubleWrapper();
        maxTitleWidth = new ReadOnlyDoubleWrapper();
        minEditorWidth = new ReadOnlyDoubleWrapper();
        prefEditorWidth = new ReadOnlyDoubleWrapper();
        maxEditorWidth = new ReadOnlyDoubleWrapper();
        titleWidth = new SimpleDoubleProperty();
    }

    protected DoubleProperty internMinTitleWidthProperty() {
        return minTitleWidth;
    }

    protected DoubleProperty internPrefTitleWidthProperty() {
        return prefTitleWidth;
    }

    protected DoubleProperty internMaxTitleWidthProperty() {
        return maxTitleWidth;
    }

    protected DoubleProperty internMinEditorWidthProperty() {
        return minEditorWidth;
    }

    protected DoubleProperty internPrefEditorWidthProperty() {
        return prefEditorWidth;
    }

    protected DoubleProperty internMaxEditorWidthProperty() {
        return maxEditorWidth;
    }

    public double getMinTitleWidth() {
        return minTitleWidth.get();
    }

    public ReadOnlyDoubleProperty minTitleWidthProperty() {
        return minTitleWidth.getReadOnlyProperty();
    }

    public double getPrefTitleWidth() {
        return prefTitleWidth.get();
    }

    public ReadOnlyDoubleProperty prefTitleWidthProperty() {
        return prefTitleWidth.getReadOnlyProperty();
    }

    public double getMaxTitleWidth() {
        return maxTitleWidth.get();
    }

    public ReadOnlyDoubleProperty maxTitleWidthProperty() {
        return maxTitleWidth.getReadOnlyProperty();
    }

    public double getMinEditorWidth() {
        return minEditorWidth.get();
    }

    public ReadOnlyDoubleProperty minEditorWidthProperty() {
        return minEditorWidth.getReadOnlyProperty();
    }

    public double getPrefEditorWidth() {
        return prefEditorWidth.get();
    }

    public ReadOnlyDoubleProperty prefEditorWidthProperty() {
        return prefEditorWidth.getReadOnlyProperty();
    }

    public double getMaxEditorWidth() {
        return maxEditorWidth.get();
    }

    public ReadOnlyDoubleProperty maxEditorWidthProperty() {
        return maxEditorWidth.getReadOnlyProperty();
    }

    public double getTitleWidth() {
        return titleWidth.get();
    }

    public DoubleProperty titleWidthProperty() {
        return titleWidth;
    }

    public void setTitleWidth(double titleWidth) {
        this.titleWidth.set(titleWidth);
    }
}
