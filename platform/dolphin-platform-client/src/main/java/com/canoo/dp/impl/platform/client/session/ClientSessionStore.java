package com.canoo.dp.impl.platform.client.session;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.session.UrlToAppDomainConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientSessionStore {

    private static final Logger LOG = LoggerFactory.getLogger(ClientSessionStore.class);

    private final Lock mapLock = new ReentrantLock();

    private final Map<String, String> domainToId = new HashMap<>();

    private UrlToAppDomainConverter converter = new SimpleUrlToAppDomainConverter();

    public String getClientIdentifierForUrl(final URL url) {
        Assert.requireNonNull(url, "url");
        final String applicationDomain = converter.getApplicationDomain(url);
        if(applicationDomain == null) {
            throw new IllegalStateException("Can not define application domain for url " + url);
        }
        LOG.debug("application domain: {}", applicationDomain);

        mapLock.lock();
        try {
            return domainToId.get(applicationDomain);
        } finally {
            mapLock.unlock();
        }
    }

    public void setClientIdentifierForUrl(final URL url, final String clientId) {
        Assert.requireNonNull(url, "url");
        final String applicationDomain = converter.getApplicationDomain(url);
        if(applicationDomain == null) {
            throw new IllegalStateException("Can not define application domain for url " + url);
        }
        LOG.debug("application domain: {}", applicationDomain);

        mapLock.lock();
        try {
            if (domainToId.containsKey(applicationDomain)) {
                final String storedId = domainToId.get(applicationDomain);
                if (clientId != null && !storedId.equals(clientId)) {
                    throw new IllegalStateException("Client Id for application domain " + applicationDomain + " already specified.");
                }
            } else {
                LOG.debug("Defining client id {} for application domain {}", clientId, applicationDomain);
                if(clientId == null) {
                    domainToId.remove(applicationDomain);
                } else {
                    domainToId.put(applicationDomain, clientId);
                }
            }
        } finally {
            mapLock.unlock();
        }
    }

    public void resetSession(final URL url) {
        setClientIdentifierForUrl(url, null);
    }

    public UrlToAppDomainConverter getConverter() {
        return converter;
    }

    public void setConverter(final UrlToAppDomainConverter converter) {
        this.converter = Assert.requireNonNull(converter, "converter");
    }
}
