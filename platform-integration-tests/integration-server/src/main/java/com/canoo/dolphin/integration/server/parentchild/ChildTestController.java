package com.canoo.dolphin.integration.server.parentchild;

import com.canoo.dolphin.integration.parentchild.ChildTestBean;
import com.canoo.platform.remoting.server.ParentController;
import com.canoo.platform.remoting.server.RemotingController;
import com.canoo.platform.remoting.server.RemotingModel;

import static com.canoo.dolphin.integration.parentchild.ParentChildTestConstants.CHILD_CONTROLLER_NAME;

@RemotingController(CHILD_CONTROLLER_NAME)
public class ChildTestController {

    @RemotingModel
    private ChildTestBean model;

    @ParentController
    private ParentTestController parentController;
}
