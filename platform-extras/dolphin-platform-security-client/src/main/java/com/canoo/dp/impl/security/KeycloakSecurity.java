package com.canoo.dp.impl.security;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.security.Security;
import com.canoo.platform.core.http.HttpClient;
import com.canoo.platform.core.http.RequestMethod;

import java.util.concurrent.Future;

public class KeycloakSecurity implements Security {

    //Keycloak docker-compose https://github.com/aerogear/aerogear-unifiedpush-server/blob/master/docker-compose/development/development-env.yaml

    //https://github.com/feedhenry/keycloak-apb/blob/master/roles/provision-keycloak-apb/tasks/main.yml

    //This is the ADMIN login
    //It would be better to just do a REST request.

    //Sample: https://github.com/aerogear/aerogear-unifiedpush-server/blob/master/node.js/directgranttest.js

    //Result contains refresh token and Access token.
    //Access Token contains exprire date
    //if this reached do call with refresh token first.

    //https://github.com/keycloak/keycloak/tree/master/adapters/oidc/js/src/main/resources

    //Setup for the realm / client -> "Direct Grand" must be enabled in keycloak admin


    private final String authEndpoint;

    private final String realmName;

    private final String appName;


    private KeycloakOpenidConnectResult connectResult;

    public KeycloakSecurity(final String authEndpoint, final String realmName, final String appName) {
        this.appName = Assert.requireNonBlank(appName, "appName");
        this.authEndpoint = Assert.requireNonBlank(authEndpoint, "authEndpoint");
        this.realmName = Assert.requireNonBlank(realmName, "realmName");
    }

    @Override
    public Future<Void> login(final String user, final String password) {

        HttpClient httpClient = PlatformClient.getService(HttpClient.class);
        return httpClient.
                request(authEndpoint + "/auth/realms/" + realmName + "/protocol/openid-connect/token", RequestMethod.POST).
                withContent("client_id=" + appName + "&username=" + user + "&password=" + password + "&grant_type=password", "application/x-www-form-urlencoded").
                readObject(KeycloakOpenidConnectResult.class).
                execute().
                handle((c, e) -> {
                    connectResult = c;
                    return null;
                });
    }

    @Override
    public Future<Void> logout() {
        //REST Endpoint at Keycloak site -> Call must be done manually
        //http://www.keycloak.org/docs-api/3.3/rest-api/index.html
        //POST /admin/realms/{realm}/users/{id}/logout

        throw new RuntimeException("Not yet implemented");
    }

    public boolean isAuthorized() {
        //REST Endpoint at Keycloak site -> Call must be done manually
        //http://www.keycloak.org/docs-api/3.3/rest-api/index.html
        //See /admin/realms/{realm}/users/{id}/sessions

        return connectResult != null;
    }

    public String getAccessToken() {
        return connectResult.getAccess_token();
    }
}
