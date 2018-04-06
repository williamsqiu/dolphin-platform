package com.canoo.projection.sample.server.controller;

import com.canoo.dp.impl.platform.projector.action.DefaultServerActionBean;
import com.canoo.dp.impl.platform.projector.base.ViewBean;
import com.canoo.dp.impl.platform.projector.form.Form;
import com.canoo.dp.impl.platform.projector.form.FormField;
import com.canoo.dp.impl.platform.projector.form.FormSection;
import com.canoo.dp.impl.platform.projector.server.projection.FormBuilder;
import com.canoo.dp.impl.platform.projector.server.projection.FormFieldBuilder;
import com.canoo.dp.impl.platform.projector.server.projection.FormSectionBuilder;
import com.canoo.dp.impl.platform.projector.server.update.UpdateHandler;
import com.canoo.platform.remoting.BeanManager;
import com.canoo.platform.remoting.server.RemotingAction;
import com.canoo.platform.remoting.server.RemotingController;
import com.canoo.platform.remoting.server.RemotingModel;
import com.canoo.projection.sample.server.data.User;
import com.canoo.projection.sample.server.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@RemotingController("SimpleFormController")
public class SimpleFormController {

    @RemotingModel
    private ViewBean model;

    @Autowired
    private BeanManager beanManager;

    @Autowired
    private UserService service;

    private User user;

    private final UpdateHandler<User> updateHandler = new UpdateHandler<>();

    @PostConstruct
    public void init() {
        user = service.createNewInstance();

        final DefaultServerActionBean saveAction = beanManager.create(DefaultServerActionBean.class);
        saveAction.setTitle("save");
        saveAction.setDescription("saves the user");
        saveAction.setBlockUi(true);
        saveAction.setActionName("save");

        final DefaultServerActionBean resetAction = beanManager.create(DefaultServerActionBean.class);
        resetAction.setTitle("reset");
        resetAction.setDescription("resets the user");
        resetAction.setBlockUi(true);
        resetAction.setActionName("reset");

        final FormField IdField = FormFieldBuilder.createStringField(beanManager)
                .withTitle("Id")
                .withDescription("The id of the user")
                .withEditableFlag(false)
                .withDisabledFlag(true)
                .withUpdateHandler(updateHandler, u -> u.getId(), v -> user.setId(v))
                .build();

        final FormField firstNameField = FormFieldBuilder.createStringField(beanManager)
                .withTitle("first name")
                .withDescription("The first name of the user")
                .withUpdateHandler(updateHandler, u -> u.getFirstName(), v -> user.setFirstName(v))
                .build();

        final FormField lastNameField = FormFieldBuilder.createStringField(beanManager)
                .withTitle("last name")
                .withDescription("The last name of the user")
                .withUpdateHandler(updateHandler, u -> u.getLastName(), v -> user.setLastName(v))
                .build();

        final FormField activeField = FormFieldBuilder.createBooleanField(beanManager)
                .withTitle("active")
                .withDescription("Defines is the user is active")
                .withUpdateHandler(updateHandler, u -> u.isActive(), v -> user.setActive(v))
                .build();

        final FormSection section = FormSectionBuilder.create(beanManager)
                .withField(IdField)
                .withField(firstNameField)
                .withField(lastNameField)
                .withField(activeField)
                .build();

        final Form formBean = FormBuilder.create(beanManager)
                .withTitle("User Detail View")
                .withDescription("The detail information of a user")
                .withAction(saveAction)
                .withAction(resetAction)
                .withSection(section)
                .build();

        model.getContent().addAll(formBean);
    }

    @RemotingAction
    public void save() {
        user = service.save(user);
        updateHandler.update(user);
    }

    @RemotingAction
    public void reset() {
        user = service.reset(user);
        updateHandler.update(user);
    }
}
