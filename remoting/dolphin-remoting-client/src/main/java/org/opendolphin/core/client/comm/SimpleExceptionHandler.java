package org.opendolphin.core.client.comm;

import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleExceptionHandler implements ExceptionHandler {

    private static final Logger LOG = Logger.getLogger(SimpleExceptionHandler.class.getName());

    private final Executor uiExecutor;

    public SimpleExceptionHandler(Executor uiExecutor) {
        this.uiExecutor = uiExecutor;
    }

    @Override
    public void handle(final Throwable e) {
        LOG.log(Level.SEVERE, "onException reached, rethrowing in UI Thread, consider setting AbstractClientConnector.onException", e);
        uiExecutor.execute(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException(e);
            }
        });
    }
}
