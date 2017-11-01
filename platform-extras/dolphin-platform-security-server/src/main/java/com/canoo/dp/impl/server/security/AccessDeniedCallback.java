package com.canoo.dp.impl.server.security;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.19.0", status = INTERNAL)
public interface AccessDeniedCallback {

    void onAccessDenied();
}
