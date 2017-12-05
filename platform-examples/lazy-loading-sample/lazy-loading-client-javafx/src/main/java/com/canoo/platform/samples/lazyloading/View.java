package com.canoo.platform.samples.lazyloading;

import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.javafx.FXBinder;
import com.canoo.platform.remoting.client.javafx.view.AbstractViewController;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;

import static com.canoo.platform.samples.lazyloading.Constants.CONTROLLER_NAME;
import static com.canoo.platform.samples.lazyloading.Constants.REFRESH_ACTION;

public class View extends AbstractViewController<LazyLoadingBean> {

    private final VBox root;

    private final ListView<LazyLoadingItem> list;

    private final Button refreshButton;

    private final ProgressIndicator progressIndicator;

    private final Spinner<Integer> sizeSpinner;

    public View(ClientContext clientContext) {
        super(clientContext, CONTROLLER_NAME);

        sizeSpinner = new Spinner<>(1, 100, 10);
        progressIndicator = new ProgressIndicator();
        refreshButton = new Button("refresh");
        refreshButton.setGraphic(progressIndicator);
        progressIndicator.setVisible(false);
        list = new ListView<>();
        root = new VBox(sizeSpinner, list, refreshButton);
        root.setSpacing(12);
        root.setPadding(new Insets(24));
    }

    @Override
    protected void init() {
        refreshButton.setOnAction(e -> invoke(REFRESH_ACTION));
        FXBinder.bind(list.getItems()).to(getModel().getItems());
        FXBinder.bind(progressIndicator.visibleProperty()).to(getModel().loadingProperty());
        FXBinder.bind(getModel().requestedSizeProperty()).to(sizeSpinner.valueProperty());
    }

    @Override
    public Node getRootNode() {
        return root;
    }
}
