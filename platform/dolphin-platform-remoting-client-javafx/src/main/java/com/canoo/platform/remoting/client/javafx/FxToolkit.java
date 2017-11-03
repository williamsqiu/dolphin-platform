package com.canoo.platform.remoting.client.javafx;

import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.Toolkit;
import javafx.application.Platform;
import org.apiguardian.api.API;

import java.util.concurrent.Executor;

import static org.apiguardian.api.API.Status.MAINTAINED;

@API(since = "0.x", status = MAINTAINED)
public class FxToolkit implements Toolkit {

    private final static String NAME = "JavaFX toolkit";


    @Override
    public Executor getUiExecutor() {
        return new Executor() {
            @Override
            public void execute(Runnable command) {
                Platform.runLater(command);
            }
        };
    }

    @Override
    public String getName() {
        return NAME;
    }

    public static void init() {
        PlatformClient.init(new FxToolkit());
    }
}
