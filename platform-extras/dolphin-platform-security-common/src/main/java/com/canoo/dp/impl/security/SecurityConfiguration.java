package com.canoo.dp.impl.security;

import org.apiguardian.api.API;

@API(since = "0.19.0", status = API.Status.EXPERIMENTAL)
public interface SecurityConfiguration {

    String AUTH_ENDPOINT_PROPERTY_NAME = "security.keycloak.endpoint";

    String AUTH_ENDPOINT_PROPERTY_DEFAULT_VALUE = "http://localhost:8080";

    String REALM_PROPERTY_NAME = "security.keycloak.realm";

    String REALM_PROPERTY_DEFAULT_VALUE = "myRealm";

    String APPLICATION_PROPERTY_NAME = "security.keycloak.app";

    String DIRECT_CONNECTION_PROPERTY_NAME = "security.useDirectConnection";

    boolean DIRECT_CONNECTION_PROPERTY_DEFAULT_VALUE = false;

    String APPLICATION_PROPERTY_DEFAULT_VALUE = "myApp";
}
