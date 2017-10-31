package com.canoo.dp.impl.server.event;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "1.0.0.RC1", status = INTERNAL)
public interface DistributedEventConstants {

    String SPEC_VERSION_PARAM = "shared-event-spec-version";

    String SPEC_1_0 = "1.0";

    int TYPE_ID = 4711;

    String DATA_PARAM = "data";

    String CONTEXT_PARAM = "context";

    String TOPIC_PARAM = "topic";

    String TIMESTAMP_PARAM = "timestamp";

    String METADATA_PARAM = "metadata";

    String METADATA_KEY_PARAM = "key";

    String METADATA_VALUE_PARAM = "value";

}
