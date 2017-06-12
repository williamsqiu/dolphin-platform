package com.canoo.dolphin.server.async;

import com.canoo.dolphin.async.CommandCodec;
import com.canoo.dolphin.async.RemoteMessage;
import org.atmosphere.config.managed.Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemotingDecoder implements Decoder<String, RemoteMessage> {

    Logger LOG = LoggerFactory.getLogger(RemotingDecoder.class);

    final CommandCodec codec = new CommandCodec();

    @Override
    public RemoteMessage decode(String s) {
        LOG.info("DECODING {}", s);
        return codec.decode(s);
    }
}
