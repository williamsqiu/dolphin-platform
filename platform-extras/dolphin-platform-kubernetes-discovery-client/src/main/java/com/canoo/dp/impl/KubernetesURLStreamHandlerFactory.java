package com.canoo.dp.impl;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.discovery.DiscoveryService;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class KubernetesURLStreamHandlerFactory implements URLStreamHandlerFactory  {

    private final String KUBERNETES_PROTOCOL = "service";

    private final DiscoveryService discoveryService;

    public KubernetesURLStreamHandlerFactory() {
        discoveryService = PlatformClient.getService(DiscoveryService.class);
        Assert.requireNonNull(discoveryService, "discoveryService");
    }

    @Override
    public URLStreamHandler createURLStreamHandler(final String protocol) {
        if(KUBERNETES_PROTOCOL.equals(protocol)) {
            new URLStreamHandler(){

                @Override
                protected URLConnection openConnection(final URL u) throws IOException {
                    Assert.requireNonNull(u, "u");
                    final String serviceName = u.getHost();
                    if(serviceName.contains(":")) {
                        final String namespace = serviceName.split(":")[0];
                        final String name = serviceName.split(":")[1];
                        try {
                            return discoveryService.getAddress(namespace, name).get().openConnection();
                        } catch (Exception e) {
                            throw new IOException("Can not convert service URL", e);
                        }
                    } else {
                        final String name = serviceName;
                        try {
                            return discoveryService.getAddress(name).get().openConnection();
                        } catch (Exception e) {
                            throw new IOException("Can not convert service URL", e);
                        }
                    }
                }
            };
        }
        return null;
    }


    public static void install() {
        URL.setURLStreamHandlerFactory(new KubernetesURLStreamHandlerFactory());
    }
}
