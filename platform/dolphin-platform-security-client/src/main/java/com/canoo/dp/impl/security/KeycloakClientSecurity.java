package com.canoo.dp.impl.security;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.HttpURLConnectionFactory;
import org.keycloak.admin.client.Keycloak;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class KeycloakClientSecurity {

    private final String authEndpoint;

    private final String realmName;

    private final String appName;

    private String access_token;

    public KeycloakClientSecurity(final String authEndpoint, final String realmName, final String appName) {
        this.appName = Assert.requireNonBlank(appName, "appName");
        this.authEndpoint = Assert.requireNonBlank(authEndpoint, "authEndpoint");
        this.realmName = Assert.requireNonBlank(realmName, "realmName");
    }

    public KeycloakAuthorizationResult login(String userName, String password) {
        final Keycloak keycloak = Keycloak.getInstance(
                authEndpoint,
                realmName,
                userName,
                password,
                appName);

        //ClientsResource clients = keycloak.realm(realmName).clients();

        access_token = keycloak.tokenManager().getAccessTokenString();
        return new KeycloakAuthorizationResult();
    }

    public KeycloakAuthorizationResult logout() {
        throw new RuntimeException("Not yet implemented");
    }

    public boolean isAuthorized() {
        throw new RuntimeException("Not yet implemented");
    }

    public HttpURLConnectionFactory createConnectionFactory() {
        return new HttpURLConnectionFactory() {
            @Override
            public HttpURLConnection create(URL url) throws IOException {
                Assert.requireNonNull(url, "url");
                final URLConnection connection = url.openConnection();
                Assert.requireNonNull(connection, "connection");

                addSecurityToken(connection);
                if(connection instanceof HttpURLConnection) {
                    return (HttpURLConnection) connection;
                }
                throw new IOException("URL do not provide a HttpURLConnection!");
            }
        };
    }

    public void addSecurityToken(URLConnection connection) {
        Assert.requireNonNull(connection, "connection");
        connection.setRequestProperty("Authorization", "Bearer " + access_token);
    }

}
