package com.canoo.dolphin.samples.security;

import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.ClientContextFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URI;


public class Client extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
        final LoginDialog loginDialog = new LoginDialog();
        boolean login = loginDialog.showAndWait().orElse(false);
        if(login) {
            showApp(primaryStage);
        } else {
            System.exit(0);
        }
    }

    private void showApp(final Stage primaryStage) throws Exception {
        final ClientConfiguration clientConfiguration = PlatformClient.getClientConfiguration();
        final ClientContextFactory contextFactory = PlatformClient.getService(ClientContextFactory.class);
        final URI endpoint = new URI("http://localhost:8080/dolphin");
        final ClientContext clientContext = contextFactory.create(clientConfiguration, endpoint);
        clientContext.connect().handle((v, e) -> {
            final UserView view = new UserView(clientContext);
            Platform.runLater(() -> {
                primaryStage.setScene(new Scene(view.getParent()));
                primaryStage.show();
            });
            return null;
        });
    }

    public static void main(String[] args) {
        Client.launch(args);
    }
}
