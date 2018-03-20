package com.canoo.dp.impl.server.security;

import org.apiguardian.api.API;

@API(since = "0.19.0", status = API.Status.EXPERIMENTAL)
public interface SecurityServerConfiguration {

    String CORS_PROPERTY_NAME = "security.keycloak.cors";

    boolean CORS_PROPERTY_DEFAULT_VALUE = true;

    String REALMS_PROPERTY_NAME = "security.keycloak.realms";
}
