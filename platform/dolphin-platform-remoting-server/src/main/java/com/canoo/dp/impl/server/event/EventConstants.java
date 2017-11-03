package com.canoo.dp.impl.server.event;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public interface EventConstants {

    String TYPE_PARAM = "sender.type";

    String TYPE_PLATFORM = "dolphinPlatform";

    String CLIENT_SESSION_PARAM = "sender.dolphinPlatform.clientSessionId";

    String HTTP_SESSION_PARAM = "sender.httpSessionId";
}
