package com.canoo.dp.impl.server.security;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.http.ConnectionUtils;
import com.canoo.dp.impl.platform.core.http.DefaultHttpURLConnectionFactory;
import com.canoo.platform.core.DolphinRuntimeException;
import com.canoo.platform.core.http.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Optional;

import static com.canoo.dp.impl.security.SecurityHttpHeader.REALM_NAME_HEADER;

public class LoginServlet extends HttpServlet {

    private final KeycloakConfiguration configuration;

    public LoginServlet(final KeycloakConfiguration configuration) {
        this.configuration = Assert.requireNonNull(configuration, "configuration");
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        try {
            final String realmName = Optional.ofNullable(req.getHeader(REALM_NAME_HEADER)).orElse(configuration.getRealmName());
            final String authEndPoint = configuration.getAuthEndpoint();
            final byte[] content = ConnectionUtils.readContent(req.getInputStream());

            final URI url = new URI(authEndPoint + "/auth/realms/" + realmName + "/protocol/openid-connect/token");
            final HttpURLConnection connection = new DefaultHttpURLConnectionFactory().create(url);
            connection.setRequestMethod(RequestMethod.POST.getRawName());
            connection.setRequestProperty("charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", content.length + "");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            ConnectionUtils.writeContent(connection, content);

            final int responseCode = connection.getResponseCode();
            if (responseCode == 401) {
                throw new DolphinRuntimeException("Invalid login!");
            }

            final byte[] responseContent = ConnectionUtils.readContent(connection);

            ConnectionUtils.writeContent(resp.getOutputStream(), responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
