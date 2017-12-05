package com.canoo.platform.samples.microservices.user;

import com.canoo.platform.remoting.server.DolphinAction;
import com.canoo.platform.remoting.server.DolphinController;
import com.canoo.platform.remoting.server.DolphinModel;

import static com.canoo.platform.samples.microservices.user.UserConstants.USER_CONTROLLER_NAME;
import static com.canoo.platform.samples.microservices.user.UserConstants.USER_REFRESH_ACTION;

@DolphinController(USER_CONTROLLER_NAME)
public class UserController {

    @DolphinModel
    private UserBean model;

    @DolphinAction(USER_REFRESH_ACTION)
    public void refresh() {
        model.setName("john0815");
    }
}
