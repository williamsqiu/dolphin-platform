package com.canoo.dolphin.integration.server.enterprise;

import com.canoo.dolphin.integration.enterprise.EnterpriseTestBean;
import com.canoo.platform.server.DolphinController;
import com.canoo.platform.server.DolphinModel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static com.canoo.dolphin.integration.enterprise.EnterpriseTestConstants.ENTERPRISE_CONTROLLER_NAME;

@DolphinController(ENTERPRISE_CONTROLLER_NAME)
public class EnterpriseTestController {

    @DolphinModel
    private EnterpriseTestBean model;

    @PostConstruct
    private void init() {
        model.setPostConstructCalled(true);
    }

    @PreDestroy
    private void destroy() {
        model.setPreDestroyCalled(true);
    }
}
