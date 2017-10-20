/*
 * Copyright 2015-2016 Canoo Engineering AG.
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
package com.canoo.dp.impl.validation;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.validation.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class ValidationTest {

    private Validator validator;

    @BeforeSuite
    public void setup() {
        Configuration<?> validationConf = Validation.byDefaultProvider().configure();
        validator = validationConf.buildValidatorFactory().getValidator();
    }

    @Test
    public void testValidators() {
        TestBean bean = new TestBean();

        Set<ConstraintViolation<TestBean>> violations = validator.validate(bean);
        assertEquals(violations.size(), 1);
        ConstraintViolation<TestBean> violation = violations.iterator().next();
        assertEquals(violation.getPropertyPath().iterator().next().getName(), "value1");

        bean.value1Property().set("YEAH!");

        violations = validator.validate(bean);
        assertEquals(violations.size(), 0);

        bean.value2Property().set("TEST");

        violations = validator.validate(bean);
        assertEquals(violations.size(), 1);
        violation = violations.iterator().next();
        assertEquals(violation.getPropertyPath().iterator().next().getName(), "value2");

        bean.value2Property().set(null);

        bean.value3Property().set(false);

        violations = validator.validate(bean);
        assertEquals(violations.size(), 1);
        violation = violations.iterator().next();
        assertEquals(violation.getPropertyPath().iterator().next().getName(), "value3");

        bean.value3Property().set(Boolean.FALSE);

        violations = validator.validate(bean);
        assertEquals(violations.size(), 1);
        violation = violations.iterator().next();
        assertEquals(violation.getPropertyPath().iterator().next().getName(), "value3");

        bean.value3Property().set(true);

        bean.value4Property().set(true);

        violations = validator.validate(bean);
        assertEquals(violations.size(), 1);
        violation = violations.iterator().next();
        assertEquals(violation.getPropertyPath().iterator().next().getName(), "value4");

        bean.value4Property().set(Boolean.TRUE);

        violations = validator.validate(bean);
        assertEquals(violations.size(), 1);
        violation = violations.iterator().next();
        assertEquals(violation.getPropertyPath().iterator().next().getName(), "value4");
    }

    @Test
    public void testPastValidationBasic() {
        TestBeanPast bean = new TestBeanPast();
        ConstraintViolation<TestBeanPast> violation;

        Set<ConstraintViolation<TestBeanPast>> violations = null;

        // Empty bean must be good
        violations = validator.validate(bean);
        assertEquals(violations.size(), 0);

        // Past date must be good
        bean.dateProperty().set(new Date(0L) );
        Calendar past = Calendar.getInstance();
        past.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) - 1); // Gotta be the past
        bean.calendarProperty().set(past);

        violations = validator.validate(bean);
        assertEquals(violations.size(), 0);

        // Future date must be bad
        bean.dateProperty().set(new Date(new Date().getTime() + 1000000) ); // Gotta be the future?

        violations = validator.validate(bean);
        assertEquals(violations.size(), 1);
        violation = violations.iterator().next();
        assertEquals(violation.getPropertyPath().iterator().next().getName(), "date");

        // Reset back to clean
        bean.dateProperty().set(new Date(0L) );

        violations = validator.validate(bean);
        assertEquals(violations.size(), 0);

        // Future calendar must be bad
        Calendar future = Calendar.getInstance();
        future.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) + 1);
        bean.calendarProperty().set(future);

        violations = validator.validate(bean);
        assertEquals(violations.size(), 1);
        violation = violations.iterator().next();
        assertEquals(violation.getPropertyPath().iterator().next().getName(), "calendar");

        // Past calendar must be good
        Calendar pastCalendar = Calendar.getInstance();
        pastCalendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) - 1);
        bean.calendarProperty().set(pastCalendar);

        violations = validator.validate(bean);
        assertEquals(violations.size(), 0);

        // Check when both fields are invalid
        future = Calendar.getInstance();
        future.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) + 1);
        bean.calendarProperty().set(future);
        bean.dateProperty().set(new Date(new Date().getTime() + 1000000) );

        violations = validator.validate(bean);
        assertEquals(violations.size(), 2);

        // Lastly, check validations on invalid type
        TestBeanPastInvalidAnnotation invalid = new TestBeanPastInvalidAnnotation();
        invalid.dateProperty().set((short) 5);
        try {
            validator.validate(invalid);
            fail("Validator must have caught invalid type.");
        } catch (ValidationException ve) {
            // Runtime exception must be thrown when invalid type is validated
        }
    }

    /*
     * Same test as testPastValidationBasic but inverted from past to future
     */
    @Test
    public void testFutureValidationBasic() {
        TestBeanFuture bean = new TestBeanFuture();
        ConstraintViolation<TestBeanFuture> violation;

        Set<ConstraintViolation<TestBeanFuture> > violations = null;

        // Empty bean must be good
        violations = validator.validate(bean);
        assertEquals(violations.size(), 0);

        // Future date must be good
        bean.dateProperty().set(
                new Date(new Date().getTime() + 1000000)
        );

        violations = validator.validate(bean);
        assertEquals(violations.size(), 0);

        // Past date must be bad
        bean.dateProperty().set(new Date(new Date().getTime() - 1000000) );

        violations = validator.validate(bean);
        assertEquals(violations.size(), 1);
        violation = violations.iterator().next();
        assertEquals(violation.getPropertyPath().iterator().next().getName(), "date");

        // Reset back to clean
        bean.dateProperty().set(new Date(new Date().getTime() + 1000000) );

        violations = validator.validate(bean);
        assertEquals(violations.size(), 0);

        // Past calendar must be bad
        Calendar past = Calendar.getInstance();
        past.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) - 1);
        bean.calendarProperty().set(past);

        violations = validator.validate(bean);
        assertEquals(violations.size(), 1);
        violation = violations.iterator().next();
        assertEquals(violation.getPropertyPath().iterator().next().getName(), "calendar");

        // Future calendar must be good
        Calendar future = Calendar.getInstance();
        future.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) + 1);
        bean.calendarProperty().set(future);

        violations = validator.validate(bean);
        assertEquals(violations.size(), 0);

        // Check when both fields are invalid
        past = Calendar.getInstance();
        past.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) - 1);
        bean.calendarProperty().set(past);
        bean.dateProperty().set(new Date(new Date().getTime() - 1000000) );

        violations = validator.validate(bean);
        assertEquals(violations.size(), 2);

        // Lastly, check validations on invalid type
        TestBeanFutureInvalidAnnotation invalid = new TestBeanFutureInvalidAnnotation();
        invalid.dateProperty().set((short) 5);
        try {
            validator.validate(invalid);
            fail("Validator must have caught invalid type.");
        } catch (ValidationException ve) {
            // Runtime exception must be thrown when invalid type is validated
        }
    }

}
