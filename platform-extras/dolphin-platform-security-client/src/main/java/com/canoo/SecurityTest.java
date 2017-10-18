package com.canoo;

import com.canoo.dp.impl.security.KeycloakSecurity;

public class SecurityTest {

    public static void main(String[] args) throws Exception {
        KeycloakSecurity security = new KeycloakSecurity("http://localhost:8080", "canoo", "java-app");
        security.login("admin", "abc123").get();
    }
}
