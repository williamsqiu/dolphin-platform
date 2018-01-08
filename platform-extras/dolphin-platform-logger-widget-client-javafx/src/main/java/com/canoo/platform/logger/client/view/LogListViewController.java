package com.canoo.platform.logger.client.view;

import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.logger.client.widgets.LogListCell;
import com.canoo.platform.logger.model.LogEntryBean;
import com.canoo.platform.logger.model.LogListBean;
import com.canoo.platform.logging.spi.LogMessage;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.javafx.FXBinder;
import com.canoo.platform.remoting.client.javafx.view.AbstractViewController;
import javafx.scene.Node;
import javafx.scene.control.ListView;

import static com.canoo.platform.logger.LoggerRemotingConstants.LOG_LIST_CONTROLLER_NAME;
import static com.canoo.platform.logger.LoggerRemotingConstants.UPDATE_ACTION;

public class LogListViewController extends AbstractViewController<LogListBean> {

    private final ListView<LogMessage> listView;

    public LogListViewController(ClientContext clientContext) {
        super(clientContext, LOG_LIST_CONTROLLER_NAME);
        listView = new ListView<>();
        listView.setCellFactory(v -> new LogListCell());
    }

    @Override
    protected void init() {
        FXBinder.bind(listView.getItems()).to(getModel().getEntries(), b -> convertBean(b));
        invoke(UPDATE_ACTION);

        final ClientConfiguration clientConfiguration = PlatformClient.getClientConfiguration();
        clientConfiguration.getBackgroundExecutor().execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) { e.printStackTrace();}
                clientConfiguration.getUiExecutor().execute(() -> invoke(UPDATE_ACTION));
            }
        });
    }

    private LogMessage convertBean(final LogEntryBean bean) {
        final LogMessage message = new LogMessage();
        message.setMessage(bean.getMessage());
        message.setLevel(bean.getLogLevel());
        message.setTimestamp(bean.getLogTimestamp());
        return message;
    }

    @Override
    public Node getRootNode() {
        return listView;
    }
}
