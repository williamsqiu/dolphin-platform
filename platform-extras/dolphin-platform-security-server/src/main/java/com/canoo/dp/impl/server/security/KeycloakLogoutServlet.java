package com.canoo.dp.impl.server.security;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.http.HttpClientConnection;
import com.canoo.platform.core.DolphinRuntimeException;
import com.canoo.platform.core.http.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.canoo.dp.impl.platform.core.http.HttpStatus.HTTP_OK;
import static com.canoo.dp.impl.platform.core.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static com.canoo.dp.impl.security.SecurityHttpHeader.AUTHORIZATION_HEADER;
import static com.canoo.dp.impl.security.SecurityHttpHeader.REALM_NAME_HEADER;

public class KeycloakLogoutServlet extends HttpServlet {

    private final static Logger LOG = LoggerFactory.getLogger(KeycloakTokenServlet.class);

    private final KeycloakConfiguration configuration;

    public KeycloakLogoutServlet(final KeycloakConfiguration configuration) {
        this.configuration = Assert.requireNonNull(configuration, "configuration");
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        try {
            LOG.debug("Logout endpoint called");
            final String realmName = Optional.ofNullable(req.getHeader(REALM_NAME_HEADER)).orElse(configuration.getRealmName());
            final String authEndPoint = configuration.getAuthEndpoint();
            final URI url = new URI(authEndPoint + "/realms/" + realmName + "/protocol/openid-connect/logout");
            LOG.debug("Calling Keycloak");
            final HttpClientConnection clientConnection = new HttpClientConnection(url, RequestMethod.GET);
            clientConnection.addRequestHeader(AUTHORIZATION_HEADER, req.getHeader(AUTHORIZATION_HEADER));
            final int responseCode = clientConnection.readResponseCode();
            if(responseCode != HTTP_OK) {
                LOG.debug("Error in logout!");
                throw new DolphinRuntimeException("Error in logout!");
            }
            LOG.debug("Logout done");
        } catch (final Exception e) {
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Error in logout!");
        }
    }
}

