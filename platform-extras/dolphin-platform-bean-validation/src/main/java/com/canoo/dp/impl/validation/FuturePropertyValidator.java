package com.canoo.dp.impl.validation;

import org.apiguardian.api.API;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Future;
import java.util.Calendar;
import java.util.Date;

import static org.apiguardian.api.API.Status.INTERNAL;

/**
 * Validator that adds Dolphin Platform property support for the {@link Future} annotation.
 */
@API(since = "0.19.0", status = INTERNAL)
public final class FuturePropertyValidator extends AbstractDateAndCalendarValidator<Future> {

    @Override
    protected boolean checkValidDate(final Date date, final ConstraintValidatorContext context) {
        return date.after(new Date() );
    }

    @Override
    protected boolean checkValidCalendar(final Calendar calendar, final ConstraintValidatorContext context) {
        return calendar.after(Calendar.getInstance() );
    }
}
