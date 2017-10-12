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
    public void testQualifier1() {

        //given:
        final QualifierTestSubBeanOne qualifierTestSubBeanOne = controller.getModel().getQualifierTestSubBeanOneValue();
        final QualifierTestSubBeanTwo qualifierTestSubBeanTwo = controller.getModel().getQualifierTestSubBeanTwoValue();

        //when:
        setSubBeanOneValue(qualifierTestSubBeanOne, 42, true, "Test1");
        controller.invoke(DUMMY_ACTION);

        //then:
        assertSubBeanOneValue(qualifierTestSubBeanOne, 42, true, "Test1");
        assertSubBeanTwoValue(qualifierTestSubBeanTwo, 42, true, "Test1");
    }

    @Test
    public void testQualifier2() {
        //given:
        final QualifierTestSubBeanOne qualifierTestSubBeanOne = controller.getModel().getQualifierTestSubBeanOneValue();
        final QualifierTestSubBeanTwo qualifierTestSubBeanTwo = controller.getModel().getQualifierTestSubBeanTwoValue();

        //when:
        setSubBeanTwoValue(qualifierTestSubBeanTwo, 42, true, "Test1");
        controller.invoke(DUMMY_ACTION);

        //then:
        assertSubBeanOneValue(qualifierTestSubBeanOne, 42, true, "Test1");
        assertSubBeanTwoValue(qualifierTestSubBeanTwo, 42, true, "Test1");
    }

    @Test
    public void testQualifierUnbind() {
        //given:
        final QualifierTestSubBeanOne qualifierTestSubBeanOne = controller.getModel().getQualifierTestSubBeanOneValue();
        final QualifierTestSubBeanTwo qualifierTestSubBeanTwo = controller.getModel().getQualifierTestSubBeanTwoValue();

        //when:
        setSubBeanOneValue(qualifierTestSubBeanOne, 42, true, "Test1");
        controller.invoke(UNBIND_ACTION);
        setSubBeanOneValue(qualifierTestSubBeanOne, 44, false, "Test2");

        //then:
        assertSubBeanOneValue(qualifierTestSubBeanOne, 44, false, "Test2");
        assertSubBeanTwoValue(qualifierTestSubBeanTwo, 42, true, "Test1");


    }

    @Test
    public void testQualifierNotBound() {
        //given:
        final QualifierTestSubBeanOne qualifierTestSubBeanOne = controller.getModel().getQualifierTestSubBeanOneValue();
        final QualifierTestSubBeanTwo qualifierTestSubBeanTwo = controller.getModel().getQualifierTestSubBeanTwoValue();

        //when:
        controller.invoke(UNBIND_ACTION);
        setSubBeanOneValue(qualifierTestSubBeanOne, 42, true, "Test1");
        controller.invoke(DUMMY_ACTION);

        //then:
        assertSubBeanOneValue(qualifierTestSubBeanOne, 42, true, "Test1");
        assertSubBeanTwoValue(qualifierTestSubBeanTwo, null, null, null);

    }

    @Test
    public void testQualifierRebind() {
        //given:
        final QualifierTestSubBeanOne qualifierTestSubBeanOne = controller.getModel().getQualifierTestSubBeanOneValue();
        final QualifierTestSubBeanTwo qualifierTestSubBeanTwo = controller.getModel().getQualifierTestSubBeanTwoValue();

        //when:
        controller.invoke(UNBIND_ACTION);
        setSubBeanTwoValue(qualifierTestSubBeanTwo, 42, true, "Test1");
        controller.invoke(BIND_ACTION);

        //then:
        assertSubBeanOneValue(qualifierTestSubBeanOne, null, null, null);
        assertSubBeanTwoValue(qualifierTestSubBeanTwo, 42, true, "Test1");
    }

    @Test
    public void testQualifierChangeAfterRebind() {
        //given:
        final QualifierTestSubBeanOne qualifierTestSubBeanOne = controller.getModel().getQualifierTestSubBeanOneValue();
        final QualifierTestSubBeanTwo qualifierTestSubBeanTwo = controller.getModel().getQualifierTestSubBeanTwoValue();

        //when:
        controller.invoke(UNBIND_ACTION);
        setSubBeanOneValue(qualifierTestSubBeanOne, 42, true, "Test1");
        controller.invoke(BIND_ACTION);
        setSubBeanTwoValue(qualifierTestSubBeanTwo, 44, false, "Test2");

        //then:
        assertSubBeanOneValue(qualifierTestSubBeanOne, 44, false, "Test2");
        assertSubBeanTwoValue(qualifierTestSubBeanTwo, 44, false, "Test2");
    }

    private void setSubBeanOneValue(final QualifierTestSubBeanOne qualifierTestSubBeanOne, final int intValue, final boolean booleanValue, final String stringValue) {
        qualifierTestSubBeanOne.setBooleanValue(booleanValue);
        qualifierTestSubBeanOne.setStringValue(stringValue);
        qualifierTestSubBeanOne.setIntegerValue(intValue);
    }

    private void setSubBeanTwoValue(final QualifierTestSubBeanTwo qualifierTestSubBeanTwo, final int intValue, final boolean booleanValue, final String stringValue) {
        qualifierTestSubBeanTwo.setBooleanValue(booleanValue);
        qualifierTestSubBeanTwo.setStringValue(stringValue);
        qualifierTestSubBeanTwo.setIntegerValue(intValue);
    }

    private void assertSubBeanOneValue(final QualifierTestSubBeanOne qualifierTestSubBeanOne, final Integer intValue, final Boolean booleanValue, final String stringValue) {
        assertEquals(qualifierTestSubBeanOne.getBooleanValue(), booleanValue);
        assertEquals(qualifierTestSubBeanOne.getStringValue(), stringValue);
        assertEquals(qualifierTestSubBeanOne.getIntegerValue(), intValue);
    }

    private void assertSubBeanTwoValue(final QualifierTestSubBeanTwo qualifierTestSubBeanTwo, final Integer intValue, final Boolean booleanValue, final String stringValue) {
        assertEquals(qualifierTestSubBeanTwo.getBooleanValue(), booleanValue);
        assertEquals(qualifierTestSubBeanTwo.getStringValue(), stringValue);
        assertEquals(qualifierTestSubBeanTwo.getIntegerValue(), intValue);
    }
}
