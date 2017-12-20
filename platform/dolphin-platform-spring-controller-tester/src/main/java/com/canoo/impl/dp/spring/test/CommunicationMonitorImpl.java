package com.canoo.impl.dp.spring.test;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.spring.test.CommunicationMonitor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommunicationMonitorImpl implements CommunicationMonitor {

    private final static long SLEEP_TIME = 100;

    private final TestClientContext clientContext;

    private final AtomicBoolean ping = new AtomicBoolean();

    public CommunicationMonitorImpl(final TestClientContext clientContext) {
        this.clientContext = Assert.requireNonNull(clientContext, "clientContext");
    }

    private void sendPing() {
        clientContext.sendPing();
    }

    private void sendPing(final long time, final TimeUnit unit) {
        clientContext.sendPing(time, unit);
    }

    @Override
    public void await(final long time, final TimeUnit unit) throws InterruptedException, TimeoutException {
        Assert.requireNonNull(unit, "unit");
        final long endTime = System.currentTimeMillis() + unit.toMillis(time);
        ping.set(true);
        while (ping.get() && System.currentTimeMillis() < endTime) {
            final long leftTime = endTime - System.currentTimeMillis();
            sendPing(Math.max(0, leftTime - SLEEP_TIME), TimeUnit.MILLISECONDS);
            Thread.sleep(SLEEP_TIME);
        }
        if(ping.get()) {
            throw new TimeoutException("Timeout!");
        }
    }

    @Override
    public void signal() {
        ping.set(false);
    }
}
