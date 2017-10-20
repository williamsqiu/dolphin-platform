package com.canoo.dp.impl.validation;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import javax.validation.constraints.Future;
import java.util.Calendar;
import java.util.Date;

/**
 * Validator that adds Dolphin Platform property support for the {@link Future} annotation.
 */
public final class FuturePropertyValidator extends AbstractDateAndCalendarValidator<Future> {

    @Override
    protected boolean checkValidDate(Date date, ConstraintValidatorContext context) {
        return date.after(new Date() );
    }

    @Override
    protected boolean checkValidCalendar(Calendar calendar, ConstraintValidatorContext context) {
        return calendar.after(Calendar.getInstance() );
    }
}
