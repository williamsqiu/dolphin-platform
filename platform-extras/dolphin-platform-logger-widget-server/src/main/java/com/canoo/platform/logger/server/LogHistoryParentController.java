package com.canoo.platform.logger.server;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.logger.model.LogHistoryBean;
import com.canoo.platform.remoting.server.DolphinController;
import com.canoo.platform.remoting.server.DolphinModel;
import com.canoo.platform.remoting.server.PostChildCreated;

@DolphinController
public class LogHistoryParentController {

    private LogFilterController filterController;

    private LogListController listController;

    @DolphinModel
    private LogHistoryBean model;

    @PostChildCreated
    public void onFilterControllerAdded(final LogFilterController controller) {
        Assert.requireNonNull(controller, "controller");
        if(filterController != null) {
            throw new IllegalStateException("Only one filter controller can be added as child!");
        }
        this.filterController = controller;
        if(listController != null) {
            filterController.addSearchListener(r -> listController.update(r));
        }
    }

    @PostChildCreated
    public void onListControllerAdded(final LogListController controller) {
        Assert.requireNonNull(controller, "controller");
        if(listController != null) {
            throw new IllegalStateException("Only one list controller can be added as child!");
        }
        this.listController = controller;
        if(filterController != null) {
            filterController.addSearchListener(r -> listController.update(r));
        }
    }

}
