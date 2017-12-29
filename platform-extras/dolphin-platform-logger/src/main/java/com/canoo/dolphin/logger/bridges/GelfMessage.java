package com.canoo.dolphin.logger.bridges;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GelfMessage implements Serializable {

    private String message;

    private String loggerName;

    private String logLevel;

    private long logTimestamp;

    private String timeZone;

    private String threadName;

    private String exceptionMessage;

    private String exceptionClass;

    private List<String> marker;

    private Map<String, String> context;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public long getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(long logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
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
}
