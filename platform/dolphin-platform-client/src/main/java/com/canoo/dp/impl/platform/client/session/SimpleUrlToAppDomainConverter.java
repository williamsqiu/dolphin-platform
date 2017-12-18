package com.canoo.dp.impl.platform.client.session;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.domain.UrlToAppDomainConverter;
import org.apiguardian.api.API;

import java.net.URI;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class SimpleUrlToAppDomainConverter implements UrlToAppDomainConverter {

    @Override
    public String getApplicationDomain(URI url) {
        return Assert.requireNonNull(url, "url").getHost() + ":" + url.getPort();
    }
}
