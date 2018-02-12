package com.canoo.dp.impl.platform.projector.server.projection;

import com.canoo.dp.impl.platform.projector.action.ClientAction;
import com.canoo.dp.impl.platform.projector.action.StringClientActionBean;
import com.canoo.dp.impl.platform.projector.server.i18n.BeanLocalization;
import com.canoo.platform.remoting.BeanManager;

public class ClientActionBuilder<T, U extends ClientAction<T>> extends ActionBuilder<U> {


    private String actionName;

    public ClientActionBuilder(Class<U> actionClass, BeanManager beanManager, String actionName, BeanLocalization localization) {
        super(actionClass, beanManager, localization);
        this.actionName = actionName;
    }

    public ClientActionBuilder(Class<U> actionClass, BeanManager beanManager, String actionName) {
        this(actionClass, beanManager, actionName, null);
    }

    public ClientActionBuilder(Class<U> actionClass, BeanManager beanManager) {
        this(actionClass, beanManager, null, null);
    }

    public ClientActionBuilder(Class<U> actionClass, BeanManager beanManager, BeanLocalization localization) {
        this(actionClass, beanManager, null, localization);
    }

    ClientActionBuilder<T, U> withActionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    @Override
    public U build() {
        U action = super.build();
        action.setActionName(actionName);
        return action;
    }

    public static ClientActionBuilder<String, StringClientActionBean> createWithStringResult(BeanManager beanManager) {
        return new ClientActionBuilder<>(StringClientActionBean.class, beanManager);
    }

    public static ClientActionBuilder<String, StringClientActionBean> createWithStringResult(BeanManager beanManager, BeanLocalization localization) {
        return new ClientActionBuilder<>(StringClientActionBean.class, beanManager, localization);
    }

    public static ClientActionBuilder<String, StringClientActionBean> createWithStringResult(String actionName, BeanManager beanManager) {
        return new ClientActionBuilder<>(StringClientActionBean.class, beanManager).withActionName(actionName);
    }

    public static ClientActionBuilder<String, StringClientActionBean> createWithStringResult(String actionName, BeanManager beanManager, BeanLocalization localization) {
        return new ClientActionBuilder<>(StringClientActionBean.class, beanManager, localization).withActionName(actionName);
    }
}
