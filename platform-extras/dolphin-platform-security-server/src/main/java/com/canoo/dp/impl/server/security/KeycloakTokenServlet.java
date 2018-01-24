/*
 * Copyright 2015-2018 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        } catch (final Exception e) {
            resp.sendError(SC_HTTP_UNAUTHORIZED, "Can not authorize");
        }
    }
}
