package com.canoo.projection.sample.client;

import com.canoo.dp.impl.platform.projector.client.base.ClientActionSupport;
import com.canoo.dp.impl.platform.projector.client.base.DefaultClientActionSupport;
import com.canoo.dp.impl.platform.projector.client.projection.Projector;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.javafx.DolphinPlatformApplication;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.net.URL;

public class ProjectionClient extends DolphinPlatformApplication {
    @Override
    protected URL getServerEndpoint() throws MalformedURLException {
        return new URL("http://localhost:8080/dolphin");
    }

    @Override
    protected void start(final Stage primaryStage, final ClientContext clientContext) throws Exception {
        ClientActionSupport actionSupport = new DefaultClientActionSupport();
        Projector projector = new Projector(clientContext, actionSupport);
        projector.create("SimpleFormController").handle((p, e) -> {
            if(e != null) {
                e.printStackTrace();
            }
            if(p != null) {
                Platform.runLater(() -> primaryStage.setScene(new Scene(p)));
                primaryStage.show();
            }
            return null;
        });
    }
}
