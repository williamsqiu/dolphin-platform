/*
 * Copyright 2015-2017 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opendolphin.core.client.comm;

import org.opendolphin.core.Attribute;
import org.opendolphin.core.client.ClientAttribute;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientModelStore;
import org.opendolphin.core.client.ClientPresentationModel;
import org.opendolphin.core.comm.AttributeMetadataChangedCommand;
import org.opendolphin.core.comm.CallNamedActionCommand;
import org.opendolphin.core.comm.Command;
import org.opendolphin.core.comm.CreatePresentationModelCommand;
import org.opendolphin.core.comm.DeletePresentationModelCommand;
import org.opendolphin.core.comm.InitializeAttributeCommand;
import org.opendolphin.core.comm.ValueChangedCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class ClientResponseHandler {

    private static final Logger LOG = Logger.getLogger(ClientResponseHandler.class.getName());

    private final ClientDolphin clientDolphin;

    private boolean strictMode = true;

    public ClientResponseHandler(ClientDolphin clientDolphin) {
        this.clientDolphin = Objects.requireNonNull(clientDolphin);
    }

    protected ClientModelStore getClientModelStore() {
        return clientDolphin.getClientModelStore();
    }

    public void dispatchHandle(Command command) {
        if (command instanceof DeletePresentationModelCommand) {
            handleDeletePresentationModelCommand((DeletePresentationModelCommand) command);
        } else if (command instanceof CreatePresentationModelCommand) {
            handleCreatePresentationModelCommand((CreatePresentationModelCommand) command);
        } else if (command instanceof ValueChangedCommand) {
            handleValueChangedCommand((ValueChangedCommand) command);
        } else if (command instanceof InitializeAttributeCommand) {
            handleInitializeAttributeCommand((InitializeAttributeCommand) command);
        } else if (command instanceof AttributeMetadataChangedCommand) {
            handleAttributeMetadataChangedCommand((AttributeMetadataChangedCommand) command);
        } else if (command instanceof CallNamedActionCommand) {
            handleCallNamedActionCommand((CallNamedActionCommand) command);
        } else {
            LOG.severe("C: cannot handle unknown command '" + command + "'");
        }

    }

    private void handleDeletePresentationModelCommand(DeletePresentationModelCommand serverCommand) {
        ClientPresentationModel model = clientDolphin.getPresentationModel(serverCommand.getPmId());
        if (model == null) {
            return;
        }

        getClientModelStore().delete(model);
    }

    private void handleCreatePresentationModelCommand(CreatePresentationModelCommand serverCommand) {
        if (getClientModelStore().containsPresentationModel(serverCommand.getPmId())) {
            throw new IllegalStateException("There already is a presentation model with id '" + serverCommand.getPmId() + "' known to the client.");
        }

        List<ClientAttribute> attributes = new ArrayList<ClientAttribute>();
        for (Map<String, Object> attr : serverCommand.getAttributes()) {

            Object propertyName = attr.get("propertyName");
            Object value = attr.get("value");
            Object qualifier = attr.get("qualifier");
            Object id = attr.get("id");

            ClientAttribute attribute = new ClientAttribute(propertyName != null ? propertyName.toString() : null, value, qualifier != null ? qualifier.toString() : null);
            if (id != null && id.toString().endsWith("S")) {
                attribute.setId(id.toString());
            }

           attributes.add(attribute);
        }

        ClientPresentationModel model = new ClientPresentationModel(serverCommand.getPmId(), attributes);
        model.setPresentationModelType(serverCommand.getPmType());
        if (serverCommand.isClientSideOnly()) {
            model.setClientSideOnly(true);
        }

        getClientModelStore().add(model);
        clientDolphin.updateQualifiers(model);
    }

    private void handleValueChangedCommand(ValueChangedCommand serverCommand) {
        Attribute attribute = getClientModelStore().findAttributeById(serverCommand.getAttributeId());
        if (attribute == null) {
            LOG.warning("C: attribute with id '" + serverCommand.getAttributeId() + "' not found, cannot update old value '" + serverCommand.getOldValue() + "' to new value '" + serverCommand.getNewValue() + "'");
            return;
        }

        if (attribute.getValue() == null && serverCommand.getNewValue() == null || (attribute.getValue() != null && serverCommand.getNewValue() != null && attribute.getValue().equals(serverCommand.getNewValue()))) {
            return;
        }


        if (strictMode && ((attribute.getValue() == null && serverCommand.getOldValue() != null) || (attribute.getValue() != null && serverCommand.getOldValue() == null) || (attribute.getValue() != null && !attribute.getValue().equals(serverCommand.getOldValue())))) {
            // todo dk: think about sending a RejectCommand here to tell the server about a possible lost update
            LOG.warning("C: attribute with id '" + serverCommand.getAttributeId() + "' and value '" + attribute.getValue() + "' cannot be set to new value '" + serverCommand.getNewValue() + "' because the change was based on an outdated old value of '" + serverCommand.getOldValue() + "'.");
            return;
        }

        LOG.info("C: updating '" + attribute.getPropertyName() + "' id '" + serverCommand.getAttributeId() + "' from '" + attribute.getValue() + "' to '" + serverCommand.getNewValue() + "'");
        attribute.setValue(serverCommand.getNewValue());
        return;
    }

    private void handleInitializeAttributeCommand(InitializeAttributeCommand serverCommand) {
        ClientAttribute attribute = new ClientAttribute(serverCommand.getPropertyName(), serverCommand.getNewValue(), serverCommand.getQualifier());

        // todo: add check for no-value; null is a valid value
        if (serverCommand.getQualifier() != null) {
            List<ClientAttribute> copies = getClientModelStore().findAllAttributesByQualifier(serverCommand.getQualifier());
            if (copies != null && !copies.isEmpty()) {
                if (serverCommand.getNewValue() == null) {
                    attribute.setValue(copies.get(0).getValue());
                } else {
                    for (ClientAttribute attr : copies) {
                        attr.setValue(attribute.getValue());
                    }

                }

            }

        }

        ClientPresentationModel presentationModel = null;
        if (serverCommand.getPmId() != null) {
            presentationModel = getClientModelStore().findPresentationModelById(serverCommand.getPmId());
        }

        // here we could have a pmType conflict and we may want to throw an Exception...
        // if there is no pmId, it is most likely an error and CreatePresentationModelCommand should have been used
        if (presentationModel == null) {
            presentationModel = new ClientPresentationModel(serverCommand.getPmId(), Collections.<ClientAttribute>emptyList());
            presentationModel.setPresentationModelType(serverCommand.getPmType());
            getClientModelStore().add(presentationModel);
        }

        // if we already have the attribute, just update the value
        Attribute existingAtt = presentationModel.getAttribute(serverCommand.getPropertyName());
        if (existingAtt != null) {
            existingAtt.setValue(attribute.getValue());
        } else {
            clientDolphin.addAttributeToModel(presentationModel, attribute);
        }

        clientDolphin.updateQualifiers(presentationModel);
    }

    private void handleAttributeMetadataChangedCommand(AttributeMetadataChangedCommand serverCommand) {
        ClientAttribute attribute = getClientModelStore().findAttributeById(serverCommand.getAttributeId());
        if (attribute == null) {
            return;
        }

        if (serverCommand.getMetadataName() != null && serverCommand.getMetadataName().equals(Attribute.VALUE_NAME)) {
            attribute.setValue(serverCommand.getValue());
        }

        if (serverCommand.getMetadataName() != null && serverCommand.getMetadataName().equals(Attribute.QUALIFIER_NAME)) {
            if (serverCommand.getValue() == null) {
                attribute.setQualifier(null);
            } else {
                attribute.setQualifier(serverCommand.getValue().toString());
            }
        }
    }

    private void handleCallNamedActionCommand(CallNamedActionCommand serverCommand) {
        clientDolphin.send(serverCommand.getActionName());
    }

    public void setStrictMode(boolean strictMode) {
        this.strictMode = strictMode;
    }

}
