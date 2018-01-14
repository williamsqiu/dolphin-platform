package com.canoo.platform.logger.server;

import com.canoo.platform.logging.spi.LogMessage;
import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.logger.model.LogEntryBean;
import com.canoo.platform.logger.model.LogListBean;
import com.canoo.platform.logger.server.service.LoggerRepository;
import com.canoo.platform.logger.model.LoggerSearchRequest;
import com.canoo.platform.remoting.BeanManager;
import com.canoo.platform.remoting.server.RemotingController;
import com.canoo.platform.remoting.server.RemotingModel;

import javax.inject.Inject;

@RemotingController
public class LogListController {

    private final BeanManager beanManager;

    private final LoggerRepository repository;

    @RemotingModel
    private LogListBean model;

    @Inject
    public LogListController(final BeanManager beanManager, final LoggerRepository repository) {
        this.beanManager = Assert.requireNonNull(beanManager, "beanManager");
        this.repository = Assert.requireNonNull(repository, "repository");
    }

    protected final void update(final LoggerSearchRequest request) {
        Assert.requireNonNull(request, "request");

        model.getEntries().clear();
        repository.search(request).
                map(m -> convert(m)).
                forEach(b -> model.getEntries().add(b));
    }

    private LogEntryBean convert(final LogMessage logMessage) {
        Assert.requireNonNull(logMessage, "logMessage");

        final LogEntryBean bean = beanManager.create(LogEntryBean.class);
        bean.setLoggerName(logMessage.getLoggerName());
        bean.setLogLevel(logMessage.getLevel());
        bean.setMessage(logMessage.getMessage());
        bean.setLogTimestamp(logMessage.getTimestamp());
        bean.setExceptionClass(logMessage.getExceptionClass());
        bean.setExceptionMessage(logMessage.getExceptionMessage());
        bean.setThreadName(logMessage.getThreadName());
        bean.getMarker().addAll(logMessage.getMarker());

        return bean;
    }
}
