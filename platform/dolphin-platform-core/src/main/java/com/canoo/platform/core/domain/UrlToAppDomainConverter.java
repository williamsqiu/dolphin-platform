package com.canoo.platform.core.domain;

import org.apiguardian.api.API;

import java.net.URI;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

/**
 * {@link java.util.function.Function} can be used...
 */
@API(since = "0.x", status = EXPERIMENTAL)
@FunctionalInterface
@Deprecated
public interface UrlToAppDomainConverter {

    String getApplicationDomain(URI url);
}
