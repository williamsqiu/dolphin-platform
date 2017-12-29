package com.canoo.platform.logger.model;

import com.canoo.platform.remoting.DolphinBean;
import com.canoo.platform.remoting.ObservableList;

@DolphinBean
public class LogListBean {

    private ObservableList<LogEntryBean> entries;

    public ObservableList<LogEntryBean> getEntries() {
        return entries;
    }
}
