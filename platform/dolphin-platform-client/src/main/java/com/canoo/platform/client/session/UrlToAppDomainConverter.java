package com.canoo.platform.client.session;

import java.net.URL;

public interface UrlToAppDomainConverter {

    String getApplicationDomain(URL url);
}
