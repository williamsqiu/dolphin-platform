package com.canoo.platform.core.http;

import com.canoo.dp.impl.platform.core.PlatformConstants;
import com.canoo.platform.core.DolphinRuntimeException;
import org.apiguardian.api.API;

import java.io.UnsupportedEncodingException;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
public interface HttpCallRequestBuilder {

    HttpCallRequestBuilder withHeader(String name, String content);

    default HttpCallResponseBuilder withContent(byte[] content) {
        return withContent(content, "application/raw");
    }

    HttpCallResponseBuilder withContent(byte[] content, String contentType);

    default HttpCallResponseBuilder withContent(String content) {
        return withContent(content, "application/txt;charset=utf-8");
    }

    default HttpCallResponseBuilder withContent(String content, String contentType) {
        withHeader( "charset", "UTF-8");
        try {
            return withContent(content.getBytes(PlatformConstants.CHARSET), contentType);
        } catch (UnsupportedEncodingException e) {
            throw new DolphinRuntimeException("Encoding error", e);
        }
    }

    <I> HttpCallResponseBuilder withContent(I content);

    HttpCallResponseBuilder withoutContent();
}
