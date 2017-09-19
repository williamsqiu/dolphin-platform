package com.canoo.dp.impl.platform.client.session;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.session.UrlToAppDomainConverter;

import java.net.URL;

public class SimpleUrlToAppDomainConverter implements UrlToAppDomainConverter {

    @Override
    public String getApplicationDomain(URL url) {
        return Assert.requireNonNull(url, "url").getHost() + ":" + url.getPort();
    }
}
