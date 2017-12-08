package com.canoo.platform.client.session;

import org.apiguardian.api.API;

import java.net.URL;
import java.util.Optional;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.19.0", status = EXPERIMENTAL)
public interface ClientSessionStore {

    void resetSession(final URL url);

    String getClientIdentifierForUrl(URL endpoint);

    default Optional<String> clientIdentifierForUrl(final URL endpoint) {
        return Optional.ofNullable(getClientIdentifierForUrl(endpoint));
    }

    void setClientIdentifierForUrl(URL url, String clientIdInHeader);
}
