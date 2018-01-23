package com.canoo.dp.impl.platform.projector.graph;

import com.canoo.dp.impl.platform.projector.metadata.MetadataUtilities;
import com.canoo.platform.remoting.BeanManager;

import java.util.Optional;

public interface GraphMetadata {

    String JAVAFX_LAYOUT_GRAPH_TYPE = "type";

    String JAVAFX_LAYOUT_GRAPH_TYPE_VALUE_PIE = GraphType.PIE.name();

    String JAVAFX_LAYOUT_GRAPH_TYPE_VALUE_BAR = GraphType.BARCHART.name();

    static void setGraphType(GraphDataBean content, BeanManager beanManager, GraphType type) {
        MetadataUtilities.getOrCreateStringBasedMetadata(JAVAFX_LAYOUT_GRAPH_TYPE, content, beanManager).setValue(type.name());
    }

    static GraphType getGraphType(GraphDataBean content) {
        return MetadataUtilities.getMetadata(JAVAFX_LAYOUT_GRAPH_TYPE, content).map(key -> GraphType.valueOf(Optional.ofNullable(key.getValue()).orElse("").toString())).orElse(GraphType.PIE);
    }
}
