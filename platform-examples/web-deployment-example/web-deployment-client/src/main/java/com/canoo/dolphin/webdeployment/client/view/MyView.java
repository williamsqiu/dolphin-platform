package com.canoo.dolphin.webdeployment.client.view;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.javafx.binding.FXBinder;

import com.canoo.dolphin.client.javafx.view.AbstractFXMLViewBinder;
import com.canoo.dolphin.webdeployment.Constants;
import com.canoo.dolphin.webdeployment.model.MyModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class MyView extends AbstractFXMLViewBinder<MyModel> {

    @FXML
    private TextField valueField;

    @FXML
    private Button resetButton;

    public MyView(ClientContext clientContext) {
        super(clientContext, Constants.CONTROLLER_NAME, MyView.class.getResource("view.fxml"));
    }


    @Override
    protected void init() {
        FXBinder.bind(valueField.textProperty()).bidirectionalTo(getModel().valueProperty());

        resetButton.setOnAction(e -> invoke(Constants.RESET_ACTION));
    }
}
