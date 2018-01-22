package com.canoo.dp.impl.platform.projector.client.projection;

import com.canoo.dp.impl.platform.projector.client.form.SimpleForm;
import com.canoo.dp.impl.platform.projector.form.Form;
import com.canoo.dp.impl.platform.projector.metadata.MetadataUtilities;
import com.canoo.dp.impl.platform.projector.view.ViewMetadata;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.ControllerProxy;
import javafx.scene.Parent;

public class FormFactory implements ProjectionFactory<Form> {

    @Override
    public Parent createProjection(Projector projector, ClientContext clientContext, ControllerProxy controllerProxy, Form projectable) {
        SimpleForm simpleForm = new SimpleForm(controllerProxy, projectable, projector);
        MetadataUtilities.addListenerToMetadata(projectable, () -> {
            updateByMetadata(simpleForm, projectable);
        });
        updateByMetadata(simpleForm, projectable);
        return simpleForm;
    }

    private void updateByMetadata(SimpleForm simpleForm, Form projectable) {
        simpleForm.setStyle("-fx-background-color: " + ViewMetadata.getBackgroundColor(projectable));
    }
}
