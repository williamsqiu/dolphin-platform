package com.canoo.platform.samples.microservices.user;

import com.canoo.platform.remoting.server.RemotingAction;
import com.canoo.platform.remoting.server.RemotingController;
import com.canoo.platform.remoting.server.RemotingModel;

import static com.canoo.platform.samples.microservices.user.UserConstants.USER_CONTROLLER_NAME;
import static com.canoo.platform.samples.microservices.user.UserConstants.USER_REFRESH_ACTION;

@RemotingController(USER_CONTROLLER_NAME)
public class UserController {
    private static int counter;

    @RemotingModel
    private UserBean model;

    @RemotingAction(USER_REFRESH_ACTION)
    public void refresh() {
        model.setName("john0815 " + ++counter);
    }
}
