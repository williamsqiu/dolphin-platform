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
import com.canoo.dolphin.integration.server.TestConfiguration;
import com.canoo.platform.spring.test.ControllerUnderTest;
import com.canoo.platform.spring.test.SpringTestNGControllerTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.canoo.dolphin.integration.qualifier.QualifierTestConstants.BIND_ACTION;
import static com.canoo.dolphin.integration.qualifier.QualifierTestConstants.DUMMY_ACTION;
import static com.canoo.dolphin.integration.qualifier.QualifierTestConstants.QUALIFIER_CONTROLLER_NAME;
import static com.canoo.dolphin.integration.qualifier.QualifierTestConstants.UNBIND_ACTION;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@SpringBootTest(classes = TestConfiguration.class)
public class QualifierControllerTest extends SpringTestNGControllerTest {

    private ControllerUnderTest<QualifierTestBean> controller;

    @BeforeMethod
    public void init() {
        controller = createController(QUALIFIER_CONTROLLER_NAME);
    }

    @AfterMethod
    public void destroy() {
        controller.destroy();
    }

    @Test
    public void testCreateController() {
        assertNotNull(controller.getModel());
        assertEquals(controller.getModel().getClass(), QualifierTestBean.class);

        assertNotNull(controller.getModel().subBeanlOneProperty().get());
        assertEquals(controller.getModel().subBeanlOneProperty().get().getClass(), QualifierTestSubBeanOne.class);

        assertNotNull(controller.getModel().subBeanTwoProperty().get());
        assertEquals(controller.getModel().subBeanTwoProperty().get().getClass(), QualifierTestSubBeanTwo.class);
    }

    @Test
    public void testQualifier1() {

        //given:
        final QualifierTestSubBeanOne subBeanOne = controller.getModel().subBeanlOneProperty().get();
        final QualifierTestSubBeanTwo subBeanTwo = controller.getModel().subBeanTwoProperty().get();

        //when:
        setSubBeanOneValue(subBeanOne, 42, true, "Test1");
        controller.invoke(DUMMY_ACTION);

        //then:
        assertSubBeanOneValue(subBeanOne, 42, true, "Test1");
        assertSubBeanTwoValue(subBeanTwo, 42, true, "Test1");
    }

    @Test
    public void testQualifier2() {
        //given:
        final QualifierTestSubBeanOne subBeanOne = controller.getModel().subBeanlOneProperty().get();
        final QualifierTestSubBeanTwo subBeanTwo = controller.getModel().subBeanTwoProperty().get();

        //when:
        setSubBeanTwoValue(subBeanTwo, 42, true, "Test1");
        controller.invoke(DUMMY_ACTION);

        //then:
        assertSubBeanOneValue(subBeanOne, 42, true, "Test1");
        assertSubBeanTwoValue(subBeanTwo, 42, true, "Test1");
    }

    @Test
    public void testQualifierUnbind() {
        //given:
        final QualifierTestSubBeanOne subBeanOne = controller.getModel().subBeanlOneProperty().get();
        final QualifierTestSubBeanTwo subBeanTwo = controller.getModel().subBeanTwoProperty().get();

        //when:
        setSubBeanOneValue(subBeanOne, 42, true, "Test1");
        controller.invoke(UNBIND_ACTION);
        setSubBeanOneValue(subBeanOne, 44, false, "Test2");

        //then:
        assertSubBeanOneValue(subBeanOne, 44, false, "Test2");
        assertSubBeanTwoValue(subBeanTwo, 42, true, "Test1");


    }

    @Test
    public void testQualifierNotBound() {
        //given:
        final QualifierTestSubBeanOne subBeanOne = controller.getModel().subBeanlOneProperty().get();
        final QualifierTestSubBeanTwo subBeanTwo = controller.getModel().subBeanTwoProperty().get();

        //when:
        controller.invoke(UNBIND_ACTION);
        setSubBeanOneValue(subBeanOne, 42, true, "Test1");
        controller.invoke(DUMMY_ACTION);

        //then:
        assertSubBeanOneValue(subBeanOne, 42, true, "Test1");
        assertSubBeanTwoValue(subBeanTwo, null, null, null);

    }

    @Test
    public void testQualifierRebind() {
        //given:
        final QualifierTestSubBeanOne subBeanOne = controller.getModel().subBeanlOneProperty().get();
        final QualifierTestSubBeanTwo subBeanTwo = controller.getModel().subBeanTwoProperty().get();

        //when:
        controller.invoke(UNBIND_ACTION);
        setSubBeanTwoValue(subBeanTwo, 42, true, "Test1");
        controller.invoke(BIND_ACTION);

        //then:
        assertSubBeanOneValue(subBeanOne, null, null, null);
        assertSubBeanTwoValue(subBeanTwo, 42, true, "Test1");
    }

    @Test
    public void testQualifierChangeAfterRebind() {
        //given:
        final QualifierTestSubBeanOne subBeanOne = controller.getModel().subBeanlOneProperty().get();
        final QualifierTestSubBeanTwo subBeanTwo = controller.getModel().subBeanTwoProperty().get();

        //when:
        controller.invoke(UNBIND_ACTION);
        setSubBeanOneValue(subBeanOne, 42, true, "Test1");
        controller.invoke(BIND_ACTION);
        setSubBeanTwoValue(subBeanTwo, 44, false, "Test2");

        //then:
        assertSubBeanOneValue(subBeanOne, 44, false, "Test2");
        assertSubBeanTwoValue(subBeanTwo, 44, false, "Test2");
    }

    private void setSubBeanOneValue(final QualifierTestSubBeanOne subBeanOne, final int intValue, final boolean booleanValue, final String stringValue) {
        subBeanOne.booleanProperty().set(booleanValue);
        subBeanOne.stringProperty().set(stringValue);
        subBeanOne.integerProperty().set(intValue);
    }

    private void setSubBeanTwoValue(final QualifierTestSubBeanTwo subBeanTwo, final int intValue, final boolean booleanValue, final String stringValue) {
        subBeanTwo.booleanProperty().set(booleanValue);
        subBeanTwo.stringProperty().set(stringValue);
        subBeanTwo.integerProperty().set(intValue);
    }

    private void assertSubBeanOneValue(final QualifierTestSubBeanOne subBeanOne, final Integer intValue, final Boolean booleanValue, final String stringValue) {
        assertEquals(subBeanOne.booleanProperty().get(), booleanValue);
        assertEquals(subBeanOne.stringProperty().get(), stringValue);
        assertEquals(subBeanOne.integerProperty().get(), intValue);
    }

    private void assertSubBeanTwoValue(final QualifierTestSubBeanTwo subBeanTwo, final Integer intValue, final Boolean booleanValue, final String stringValue) {
        assertEquals(subBeanTwo.booleanProperty().get(), booleanValue);
        assertEquals(subBeanTwo.stringProperty().get(), stringValue);
        assertEquals(subBeanTwo.integerProperty().get(), intValue);
    }
}
