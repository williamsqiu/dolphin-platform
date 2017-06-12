package com.canoo.dolphin.async;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandCodec {

    Logger LOG = LoggerFactory.getLogger(CommandCodec.class);


    private final Gson gson;


    public CommandCodec() {
        this.gson = new Gson();
    }

    public RemoteMessage decode(String string) {
        LOG.info("DECODE: {}", string);
        return gson.fromJson(string, RemoteMessage.class);
    }

    public String encode(RemoteMessage message) {
        LOG.info("ENCODE: {}", message);
        return gson.toJson(message);
    }
}
