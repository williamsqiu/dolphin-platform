package org.slf4j.impl;

import com.canoo.impl.dp.logging.ThreadLocalMDCAdapter;
import org.slf4j.spi.MDCAdapter;

public class StaticMDCBinder {

    public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();

    private StaticMDCBinder() {
    }

    public static final StaticMDCBinder getSingleton() {
        return SINGLETON;
    }

    public MDCAdapter getMDCA() {
        return new ThreadLocalMDCAdapter();
    }

    public String getMDCAdapterClassStr() {
        return ThreadLocalMDCAdapter.class.getName();
    }
}
