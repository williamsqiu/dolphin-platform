package com.canoo.dp.impl.platform.projector.client.action;

import com.canoo.dp.impl.platform.projector.action.Action;
import com.canoo.platform.remoting.client.javafx.FXBinder;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import java.util.concurrent.CompletableFuture;

public abstract class AbstractActionButton<T extends Action> extends Button {

    private final T action;

    private final BooleanProperty blockUi = new SimpleBooleanProperty(false);

    private final BooleanProperty blockOnAction = new SimpleBooleanProperty(false);

    private final BooleanProperty actionRunning = new SimpleBooleanProperty(false);

    private final BooleanProperty disabled = new SimpleBooleanProperty(false);

    public AbstractActionButton(T action) {
        this.action = action;

        FXBinder.bind(textProperty()).to(action.titleProperty());

        FXBinder.bind(blockUi).to(action.blockUiProperty());
        FXBinder.bind(blockOnAction).to(action.blockOnActionProperty());
        FXBinder.bind(disabled).to(action.disabledProperty());
        disableProperty().bind(disabled.or(blockOnAction.and(actionRunning)));

        action.descriptionProperty().onChanged(event -> {
            if(action.getDescription() != null) {
                setTooltip(new Tooltip(action.getDescription()));
            } else {
                setTooltip(null);
            }
        });
        if(action.getDescription() != null) {
            setTooltip(new Tooltip(action.getDescription()));
        }

        setOnAction(e -> {
            actionRunning.setValue(true);
            callAction().whenComplete((v, ex) -> {
                Platform.runLater(() -> {
                    actionRunning.setValue(false);
                });
            });
        });
    }

    protected abstract CompletableFuture<Void> callAction();

    protected T getAction() {
        return action;
    }

}
