package com.canoo.platform.remoting.client.javafx;

import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.Toolkit;
import javafx.application.Platform;

import java.util.concurrent.Executor;

public class FxToolkit implements Toolkit {

    @Override
    public Executor getUiExecutor() {
        return new Executor() {
            @Override
            public void execute(Runnable command) {
                Platform.runLater(command);
            }
        };
    }

    public static void init() {
        PlatformClient.init(new FxToolkit());
    }
}
