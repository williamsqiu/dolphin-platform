package com.canoo.platform.samples.distribution.server.external;

public class CustomEventFormat {

    private final String myMessage;

    private final String topic;

    public CustomEventFormat(final String myMessage, final String topic) {
        this.myMessage = myMessage;
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public String getMyMessage() {
        return myMessage;
    }

}
