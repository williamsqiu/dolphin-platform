package com.canoo.dp.impl.platform.client.session;

import com.canoo.dp.impl.platform.core.Assert;
import org.apiguardian.api.API;

import java.net.URI;
import java.util.function.Function;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class SimpleUrlToAppDomainConverter implements Function<URI, String> {

    @Override
    public String apply(URI uri) {
        return Assert.requireNonNull(uri, "uri").getHost() + ":" + uri.getPort();
    }
}
