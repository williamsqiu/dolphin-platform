package com.canoo.dolphin.client.async;

import java.util.List;

public class SockJsServerInfo {

    private boolean websocket;

    private List<String> origins;

    private boolean cookieNeeded;

    private long entropy;

    public boolean isWebsocket() {
        return websocket;
    }

    public void setWebsocket(boolean websocket) {
        this.websocket = websocket;
    }

    public List<String> getOrigins() {
        return origins;
    }

    public void setOrigins(List<String> origins) {
        this.origins = origins;
    }

    public boolean isCookieNeeded() {
        return cookieNeeded;
    }

    public void setCookieNeeded(boolean cookieNeeded) {
        this.cookieNeeded = cookieNeeded;
    }

    public long getEntropy() {
        return entropy;
    }

    public void setEntropy(long entropy) {
        this.entropy = entropy;
    }
}
