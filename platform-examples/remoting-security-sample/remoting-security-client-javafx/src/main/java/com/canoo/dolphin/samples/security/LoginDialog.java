package com.canoo.dolphin.samples.security;

import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.security.Security;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.concurrent.ExecutorService;

public class LoginDialog extends Dialog<Boolean> {

    private final ButtonType LOGIN = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);

    private final TextField userField;

    private final PasswordField passwordField;

    public LoginDialog() {
        setTitle("Login");
        DialogPane dialogPane = new LoginDialogPane();
        dialogPane.getButtonTypes().add(LOGIN);
        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        userField = new TextField();
        passwordField = new PasswordField();
        grid.add(new Label("Username:"), 0, 0);
        grid.add(userField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        dialogPane.setContent(grid);
        setDialogPane(dialogPane);
        setResult(false);
    }

    private class LoginDialogPane extends DialogPane {

        @Override
        protected Node createButton(final ButtonType buttonType) {
            if(buttonType.equals(LOGIN)) {
                final Button button = new Button(buttonType.getText());
                final ButtonBar.ButtonData buttonData = buttonType.getButtonData();
                ButtonBar.setButtonData(button, buttonData);
                button.setDefaultButton(buttonType != null && buttonData.isDefaultButton());
                button.addEventHandler(ActionEvent.ACTION, e -> {
                    if (e.isConsumed()) return;
                    login(button);
                });
                return button;
            } else {
                return super.createButton(buttonType);
            }
        }
    }

    private void login(final Button button) {
        button.setDisable(true);
        final Security security = PlatformClient.getService(Security.class);
        final String user = userField.getText();
        final String password = passwordField.getText();
        final ExecutorService executor = PlatformClient.getClientConfiguration().getBackgroundExecutor();
        executor.execute(() -> {
            try {
                security.login(user, password).get();
                Platform.runLater(() -> {
                    setResult(true);
                    hide();
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    setResult(false);
                });
            } finally {
                Platform.runLater(() -> {
                    button.setDisable(false);
                });
            }
        });
    }

}
