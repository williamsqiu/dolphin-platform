package com.canoo.platform.logging.spi;

import org.slf4j.event.Level;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class LogMessage {

    private String loggerName;

    private Level level;

    private ZonedDateTime timestamp;

    private String message;

    private List<String> marker;

    private Map<String, String> context;

    private String exceptionMessage;

    private String exceptionDetail;

    private String exceptionClass;

    private String threadName;

    private transient Throwable throwable;

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(final String loggerName) {
        this.loggerName = loggerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public List<String> getMarker() {
        return marker;
    }

    public void setMarker(final List<String> marker) {
        this.marker = marker;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(final Map<String, String> context) {
        this.context = context;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(final String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(final String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(final String threadName) {
        this.threadName = threadName;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setThrowable(final Throwable throwable) {
        this.throwable = throwable;
        if(throwable != null) {
            setExceptionClass(throwable.getClass().toString());
            setExceptionMessage(throwable.getMessage());
            setExceptionDetail(toStackTrace(throwable));
        } else {
            setExceptionClass(null);
            setExceptionMessage(null);
            setExceptionDetail(null);
        }
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }

    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(final String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    private String toStackTrace(final Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter stream = new PrintWriter(stringWriter);
        if (throwable != null) {
            throwable.printStackTrace(stream);
        }
        return stringWriter.toString().substring(0, stringWriter.toString().length() - 1);
    }
}
