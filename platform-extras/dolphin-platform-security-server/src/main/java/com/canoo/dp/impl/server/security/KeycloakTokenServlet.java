package com.canoo.dp.impl.server.security;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.http.ConnectionUtils;
import com.canoo.dp.impl.platform.core.http.HttpClientConnection;
import com.canoo.platform.core.DolphinRuntimeException;
import com.canoo.platform.core.http.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.canoo.dp.impl.platform.core.http.HttpStatus.SC_HTTP_UNAUTHORIZED;
import static com.canoo.dp.impl.security.SecurityHttpHeader.REALM_NAME_HEADER;

public class KeycloakTokenServlet extends HttpServlet {

    private final KeycloakConfiguration configuration;

    public KeycloakTokenServlet(final KeycloakConfiguration configuration) {
        this.configuration = Assert.requireNonNull(configuration, "configuration");
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        try {
            final String realmName = Optional.ofNullable(req.getHeader(REALM_NAME_HEADER)).orElse(configuration.getRealmName());
            final String authEndPoint = configuration.getAuthEndpoint();
            final byte[] content = ConnectionUtils.readContent(req.getInputStream());

            final URI url = new URI(authEndPoint + "/realms/" + realmName + "/protocol/openid-connect/token");
            final HttpClientConnection clientConnection = new HttpClientConnection(url, RequestMethod.POST);
            clientConnection.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            clientConnection.setDoOutput(true);
            clientConnection.writeRequestContent(content);
            final int responseCode = clientConnection.readResponseCode();
            if(responseCode == SC_HTTP_UNAUTHORIZED) {
                throw new DolphinRuntimeException("Invalid login!");
            }
            final byte[] responseContent = clientConnection.readResponseContent();
            ConnectionUtils.writeContent(resp.getOutputStream(), responseContent);
        } catch (Exception e) {
            resp.sendError(SC_HTTP_UNAUTHORIZED, "Can not authorize");
        }
    }
}
