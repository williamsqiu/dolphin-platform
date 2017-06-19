
package com.canoo.dolphin.webdeployment.client;

import com.canoo.platform.client.ClientContext;
import com.canoo.dolphin.client.javafx.SimpleDolphinPlatformApplication;
import com.canoo.dolphin.webdeployment.client.view.MyView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.net.URL;

public class ClientApplication extends SimpleDolphinPlatformApplication {

    @Override
    protected URL getServerEndpoint() throws MalformedURLException {
         return new URL("http://localhost:8080/WebDeployment/dolphin");
    }

    @Override
    protected void start(Stage primaryStage, ClientContext clientContext) throws Exception {
        MyView view = new MyView(clientContext);
        Scene scene = new Scene(view.getParent());
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}