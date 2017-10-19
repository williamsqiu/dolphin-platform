package com.canoo.platform.client.security;

import java.util.concurrent.Future;

public interface Security {

    Future<Void> login(String user, String password);

    Future<Void> logout();

    boolean isAuthorized();

    @Deprecated
    String getAccessToken();

    @Deprecated
    long remainingTokenLifetime();

    @Deprecated
    Future<Void> refreshToken();
}
