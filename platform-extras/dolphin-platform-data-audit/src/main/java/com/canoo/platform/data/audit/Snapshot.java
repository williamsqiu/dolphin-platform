package com.canoo.platform.data.audit;

import java.time.ZonedDateTime;
import java.util.List;

public interface Snapshot {

    long getVersion();

    String getAuthor();

    ZonedDateTime getCommitDate();

    ChangeType getChangeType();

    <V> V getValue(String name);

    List<String> getChangedProperties();
}
