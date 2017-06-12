package com.canoo.dolphin.server.async;

import com.canoo.dolphin.async.CommandCodec;
import com.canoo.dolphin.async.RemoteMessage;
import org.atmosphere.config.managed.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemotingEncoder implements Encoder<RemoteMessage, String> {

    Logger LOG = LoggerFactory.getLogger(RemotingEncoder.class);


    final CommandCodec codec = new CommandCodec();

    @Override
    public String encode(RemoteMessage s) {
        LOG.info("ENCODING");
        return codec.encode(s);
    }
}
