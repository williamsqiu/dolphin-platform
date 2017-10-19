package com.canoo;

import com.canoo.dp.impl.security.KeycloakSecurity;

import java.util.concurrent.Executors;

public class SecurityTest {

    public static void main(String[] args) throws Exception {
        KeycloakSecurity security = new KeycloakSecurity("http://localhost:8180", "canoo", "java-app", Executors.newCachedThreadPool());
        security.login("admin", "abc123").get();
    }
}
