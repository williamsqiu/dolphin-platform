package com.canoo.dp.impl.platform.projector.server.projection;

import com.canoo.dp.impl.platform.projector.action.DefaultServerActionBean;
import com.canoo.dp.impl.platform.projector.action.ServerAction;
import com.canoo.dp.impl.platform.projector.server.i18n.BeanLocalization;
import com.canoo.platform.remoting.BeanManager;

public class ServerActionBuilder<T extends ServerAction> extends ActionBuilder<T> {

    private String actionName;

    public ServerActionBuilder(Class<T> actionClass, BeanManager beanManager, String actionName, BeanLocalization localization) {
        super(actionClass, beanManager, localization);
        this.actionName = actionName;
    }

    public ServerActionBuilder(Class<T> actionClass, BeanManager beanManager, String actionName) {
        this(actionClass, beanManager, actionName, null);
    }

    public ServerActionBuilder(Class<T> actionClass, BeanManager beanManager) {
        this(actionClass, beanManager, null, null);
    }

    public ServerActionBuilder(Class<T> actionClass, BeanManager beanManager, BeanLocalization localization) {
        this(actionClass, beanManager, null, localization);
    }

    ServerActionBuilder<T> withActionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    @Override
    public T build() {
        T action = super.build();
        action.setActionName(actionName);
        return action;
    }

    public static ServerActionBuilder<DefaultServerActionBean> create(BeanManager beanManager) {
        return new ServerActionBuilder<>(DefaultServerActionBean.class, beanManager);
    }

    public static ServerActionBuilder<DefaultServerActionBean> create(BeanManager beanManager, BeanLocalization localization) {
        return new ServerActionBuilder<>(DefaultServerActionBean.class, beanManager, localization);
    }

    public static ServerActionBuilder<DefaultServerActionBean> create(String actionName, BeanManager beanManager) {
        return new ServerActionBuilder<>(DefaultServerActionBean.class, beanManager).withActionName(actionName);
    }

    public static ServerActionBuilder<DefaultServerActionBean> create(String actionName, BeanManager beanManager, BeanLocalization localization) {
        return new ServerActionBuilder<>(DefaultServerActionBean.class, beanManager, localization).withActionName(actionName);
    }
}
