package com.canoo.platform.logger.model;

import com.canoo.platform.remoting.DolphinBean;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import org.slf4j.event.Level;

import java.time.ZonedDateTime;

@DolphinBean
public class LogSearchFilterBean {

    private Property<ZonedDateTime> startDate;

    private Property<ZonedDateTime> endDateTime;

    private Property<Integer> maxResults;

    private ObservableList<Level> level;

    public ZonedDateTime getStartDate() {
        return startDate.get();
    }

    public Property<ZonedDateTime> startDateProperty() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate.set(startDate);
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime.get();
    }

    public Property<ZonedDateTime> endDateTimeProperty() {
        return endDateTime;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime.set(endDateTime);
    }

    public ObservableList<Level> getLevel() {
        return level;
    }

    public Integer getMaxResults() {
        return maxResults.get();
    }

    public Property<Integer> maxResultsProperty() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults.set(maxResults);
    }
}
