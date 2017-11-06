package com.canoo.dolphin.logger;

import com.canoo.dolphin.core.DefaultHttpURLConnectionFactory;
import com.canoo.dolphin.logger.impl.DolphinLoggerThreadFactory;
import com.canoo.platform.core.http.HttpURLConnectionFactory;
import org.slf4j.event.Level;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DolphinLoggerConfiguration {

    private URL remoteUrl;

    private int parallelRequests = Runtime.getRuntime().availableProcessors() / 2 - 1;

    private int maxMessagesPerRequest = 100;

    private int remotingErrorWaitTime = 5_000;

    private int maxRemotingQueueSize = 1_000;

    private int remotingQueueCheckSleepTime = 100;

    private Level globalLevel = Level.INFO;

    private Executor remoteLoggingExecutor = new ThreadPoolExecutor(parallelRequests + 1, parallelRequests + 1,
            Long.MAX_VALUE, TimeUnit.DAYS,
            new LinkedBlockingDeque<>(),
            new DolphinLoggerThreadFactory());

    private DateFormat dateFormat = new SimpleDateFormat();

    private HttpURLConnectionFactory connectionFactory = new DefaultHttpURLConnectionFactory();

    public URL getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(URL remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public Level getGlobalLevel() {
        return globalLevel;
    }

    public void setGlobalLevel(Level globalLevel) {
        this.globalLevel = globalLevel;
    }

    public Executor getRemoteLoggingExecutor() {
        return remoteLoggingExecutor;
    }

    public void setRemoteLoggingExecutor(Executor remoteLoggingExecutor) {
        this.remoteLoggingExecutor = remoteLoggingExecutor;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public HttpURLConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(HttpURLConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public int getParallelRequests() {
        return parallelRequests;
    }

    public void setParallelRequests(int parallelRequests) {
        this.parallelRequests = parallelRequests;
    }

    public int getMaxMessagesPerRequest() {
        return maxMessagesPerRequest;
    }

    public void setMaxMessagesPerRequest(int maxMessagesPerRequest) {
        this.maxMessagesPerRequest = maxMessagesPerRequest;
    }

    public int getRemotingErrorWaitTime() {
        return remotingErrorWaitTime;
    }

    public void setRemotingErrorWaitTime(int remotingErrorWaitTime) {
        this.remotingErrorWaitTime = remotingErrorWaitTime;
    }

    public int getMaxRemotingQueueSize() {
        return maxRemotingQueueSize;
    }

    public void setMaxRemotingQueueSize(int maxRemotingQueueSize) {
        this.maxRemotingQueueSize = maxRemotingQueueSize;
    }

    public int getRemotingQueueCheckSleepTime() {
        return remotingQueueCheckSleepTime;
    }

    public void setRemotingQueueCheckSleepTime(int remotingQueueCheckSleepTime) {
        this.remotingQueueCheckSleepTime = remotingQueueCheckSleepTime;
    }
}
