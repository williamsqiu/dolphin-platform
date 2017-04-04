package org.opendolphin.core.client;

import java.beans.PropertyChangeEvent;

public interface ModelSynchronizer {

    void onAdded(ClientPresentationModel model);

    void onDeleted(ClientPresentationModel model);

    void onPropertyChanged(PropertyChangeEvent evt);

    void onMetadataChanged(PropertyChangeEvent evt);
}
