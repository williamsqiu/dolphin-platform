package com.canoo.dp.impl.platform.projector.form;

import com.canoo.dp.impl.platform.projector.action.Action;
import com.canoo.dp.impl.platform.projector.base.Projectable;
import com.canoo.dp.impl.platform.projector.base.WithDescription;
import com.canoo.dp.impl.platform.projector.base.WithIcon;
import com.canoo.dp.impl.platform.projector.base.WithLayoutMetadata;
import com.canoo.dp.impl.platform.projector.base.WithTitle;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface Form extends WithDescription, WithTitle, WithIcon, WithLayoutMetadata, Projectable {

    ObservableList<FormSection> getSections();

    ObservableList<Action> getActions();
}
