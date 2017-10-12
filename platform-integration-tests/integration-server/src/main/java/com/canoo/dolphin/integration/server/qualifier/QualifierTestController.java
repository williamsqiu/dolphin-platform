/*
 * Copyright 2015-2017 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dolphin.integration.server.qualifier;

import com.canoo.dolphin.integration.qualifier.QualifierTestBean;
import com.canoo.dolphin.integration.qualifier.QualifierTestSubBeanOne;
import com.canoo.dolphin.integration.qualifier.QualifierTestSubBeanTwo;
import com.canoo.platform.remoting.BeanManager;
import com.canoo.platform.remoting.Binding;
import com.canoo.platform.remoting.server.DolphinAction;
import com.canoo.platform.remoting.server.DolphinController;
import com.canoo.platform.remoting.server.DolphinModel;
import com.canoo.platform.remoting.server.binding.PropertyBinder;
import com.canoo.platform.remoting.server.binding.Qualifier;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.canoo.dolphin.integration.qualifier.QualifierTestConstants.BIND_ACTION;
import static com.canoo.dolphin.integration.qualifier.QualifierTestConstants.DUMMY_ACTION;
import static com.canoo.dolphin.integration.qualifier.QualifierTestConstants.QUALIFIER_CONTROLLER_NAME;
import static com.canoo.dolphin.integration.qualifier.QualifierTestConstants.UNBIND_ACTION;

@DolphinController(QUALIFIER_CONTROLLER_NAME)
public class QualifierTestController {

    private final static Qualifier<String> STRING_QUALIFIER = Qualifier.create();

    private final static Qualifier<Boolean> BOOLEAN_QUALIFIER = Qualifier.create();

    private final static Qualifier<Integer> INTEGER_QUALIFIER = Qualifier.create();

    @DolphinModel
    private QualifierTestBean model;

    @Inject
    private BeanManager beanManager;

    @Inject
    private PropertyBinder binder;

    final private List<Binding> bindings = new ArrayList<>();

    @PostConstruct
    public void init() {
        QualifierTestSubBeanOne qualifierTestSubBeanOne = beanManager.create(QualifierTestSubBeanOne.class);
        model.setQualifierTestSubBeanOneValue(qualifierTestSubBeanOne);

        QualifierTestSubBeanTwo qualifierTestSubBeanTwo = beanManager.create(QualifierTestSubBeanTwo.class);
        model.setQualifierTestSubBeanTwoValue(qualifierTestSubBeanTwo);

        bind();
    }

    private void bind() {
        bindings.add(binder.bind(model.getQualifierTestSubBeanOneValue().booleanValueProperty(), BOOLEAN_QUALIFIER));
        bindings.add(binder.bind(model.getQualifierTestSubBeanOneValue().stringValueProperty(), STRING_QUALIFIER));
        bindings.add(binder.bind(model.getQualifierTestSubBeanOneValue().integerValueProperty(), INTEGER_QUALIFIER));

        bindings.add(binder.bind(model.getQualifierTestSubBeanTwoValue().booleanValueProperty(), BOOLEAN_QUALIFIER));
        bindings.add(binder.bind(model.getQualifierTestSubBeanTwoValue().stringValueProperty(), STRING_QUALIFIER));
        bindings.add(binder.bind(model.getQualifierTestSubBeanTwoValue().integerValueProperty(), INTEGER_QUALIFIER));
    }

    private void unbind() {
        for(Binding binding : bindings) {
            binding.unbind();
        }
        bindings.clear();
    }

    @DolphinAction(DUMMY_ACTION)
    public void dummyAction() {}

    @DolphinAction(BIND_ACTION)
    public void bindAction() {
        bind();
    }

    @DolphinAction(UNBIND_ACTION)
    public void unbindAction() {
        unbind();
    }
}

