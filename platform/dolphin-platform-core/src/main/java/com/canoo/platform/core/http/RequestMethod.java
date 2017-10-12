package com.canoo.platform.core.http;

public enum RequestMethod {
    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE"), HEAD("HEAD"), OPTIONS("OPTIONS"), TRACE("TRACE");

    private final String rawName;

    RequestMethod(String rawName) {
        this.rawName = rawName;
    }

    public String getRawName() {
        return rawName;
    }
}
