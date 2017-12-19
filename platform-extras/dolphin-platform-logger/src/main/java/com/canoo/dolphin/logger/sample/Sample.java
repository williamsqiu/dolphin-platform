package com.canoo.dolphin.logger.sample;

import com.canoo.dolphin.logger.DolphinLogger;
import com.canoo.dolphin.logger.DolphinLoggerConfiguration;
import com.canoo.dolphin.logger.DolphinLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.slf4j.event.Level;

import java.net.URI;

public class Sample {

    private static final Logger LOG = LoggerFactory.getLogger(Sample.class);

    public static void main(String[] args) throws Exception {
        DolphinLogger.addMarker(LOG, "Marker1");
        LOG.info("huhu");


        DolphinLoggerConfiguration configuration = new DolphinLoggerConfiguration();
        configuration.setRemoteUrl(new URI("http://localhost:12201/gelf"));
        configuration.setMaxMessagesPerRequest(1);
        configuration.setRemotingQueueCheckSleepTime(1_000);
        configuration.setGlobalLevel(Level.TRACE);
        DolphinLoggerFactory.applyConfiguration(configuration);

        LOG.info(MarkerFactory.getMarker("Marker2"), "huhu2");

        while (true) {
            Thread.sleep(100);
            DolphinLogger.putInThreadContext("Stimmen", "" + Math.random() * 100.0);
            DolphinLogger.putInThreadContext("Werte", "" + Math.random() * 100.0);
            LOG.info(MarkerFactory.getMarker("MyMarker"), "Zeit: " + System.currentTimeMillis());
            LOG.error("HILFE", new RuntimeException("Ich bin der Fehler"));
            DolphinLogger.clearThreadContext();
        }
    }

}
