package com.canoo.dolphin.logger.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class LogMessage implements Serializable {

    private String loggerName;

    private String level;

    private long timestamp;

    private String message;

    private List<String> marker;

    private Map<String, String> context;

    private String exceptionMessage;

    private String exceptionClass;

    private String threadName;

    private TimeZone timeZone;

    private transient Throwable throwable;

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMarker() {
        return marker;
    }

    public void setMarker(List<String> marker) {
        this.marker = marker;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
        if(throwable != null) {
            setExceptionClass(throwable.getClass().toString());
            setExceptionMessage(throwable.getMessage());
        } else {
            setExceptionClass(null);
            setExceptionMessage(null);
        }
    }
}
