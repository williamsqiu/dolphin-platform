package com.canoo.platform.logger.model;

import com.canoo.platform.remoting.DolphinBean;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import org.slf4j.event.Level;

import java.time.ZonedDateTime;

@DolphinBean
public class LogEntryBean {

    private Property<String> message;

    private Property<String> loggerName;

    private Property<Level> logLevel;

    private Property<ZonedDateTime> logTimestamp;

    private Property<String> threadName;

    private Property<String> exceptionMessage;

    private Property<String> exceptionClass;

    private ObservableList<String> marker;

    public String getMessage() {
        return message.get();
    }

    public Property<String> messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public String getLoggerName() {
        return loggerName.get();
    }

    public Property<String> loggerNameProperty() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName.set(loggerName);
    }

    public ZonedDateTime getLogTimestamp() {
        return logTimestamp.get();
    }

    public Property<ZonedDateTime> logTimestampProperty() {
        return logTimestamp;
    }

    public void setLogTimestamp(ZonedDateTime logTimestamp) {
        this.logTimestamp.set(logTimestamp);
    }

    public String getThreadName() {
        return threadName.get();
    }

    public Property<String> threadNameProperty() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName.set(threadName);
    }

    public String getExceptionMessage() {
        return exceptionMessage.get();
    }

    public Property<String> exceptionMessageProperty() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage.set(exceptionMessage);
    }

    public String getExceptionClass() {
        return exceptionClass.get();
    }

    public Property<String> exceptionClassProperty() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass.set(exceptionClass);
    }

    public ObservableList<String> getMarker() {
        return marker;
    }

    public Level getLogLevel() {
        return logLevel.get();
    }

    public Property<Level> logLevelProperty() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel.set(logLevel);
    }
}
