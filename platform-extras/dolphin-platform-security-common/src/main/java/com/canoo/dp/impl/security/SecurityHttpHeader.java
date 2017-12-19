package com.canoo.dp.impl.security;

import org.apiguardian.api.API;

@API(since = "0.19.0", status = API.Status.EXPERIMENTAL)
public interface SecurityHttpHeader {

    String REALM_NAME_HEADER = "X-platform-security-realm";

    String APPLICATION_NAME_HEADER = "X-platform-security-application";

    String BEARER_ONLY_HEADER = "X-platform-security-bearer-only";

}
