package com.canoo.dp.impl.platform.projector.form;

import com.canoo.dp.impl.platform.projector.base.WithDescription;
import com.canoo.dp.impl.platform.projector.base.WithIcon;
import com.canoo.dp.impl.platform.projector.base.WithLayoutMetadata;
import com.canoo.dp.impl.platform.projector.base.WithTitle;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface FormSection extends WithTitle, WithDescription, WithIcon, WithLayoutMetadata {

    ObservableList<FormField> getFields();

}
