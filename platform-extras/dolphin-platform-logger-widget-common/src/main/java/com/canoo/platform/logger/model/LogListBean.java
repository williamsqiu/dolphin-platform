package com.canoo.platform.logger.model;

import com.canoo.platform.remoting.RemotingBean;
import com.canoo.platform.remoting.ObservableList;

@RemotingBean
public class LogListBean {

    private ObservableList<LogEntryBean> entries;

    public ObservableList<LogEntryBean> getEntries() {
        return entries;
    }
}
