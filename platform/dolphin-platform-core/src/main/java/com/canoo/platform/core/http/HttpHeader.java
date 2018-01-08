package com.canoo.platform.core.http;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

/**
 * Defines a HTTP header
 */
@API(since = "1.0.0.RC3", status = EXPERIMENTAL)
public interface HttpHeader {

    /**
     * Returns the name of the header
     * @return the name
     */
    String getName();

    /**
     * Returns the content of the header
     * @return the content
     */
    String getContent();
}
