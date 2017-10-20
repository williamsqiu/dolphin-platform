package com.canoo.dp.impl.validation;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import javax.validation.constraints.Future;
import java.util.Calendar;
import java.util.Date;

/**
 * Validator that adds Dolphin Platform property support for the {@link Future} annotation.
 *
 * Note: using plain {@code Object} as data type because {@link Date} and {@link Calendar} have no common ancestor.
 */
public final class FuturePropertyValidator extends AbstractPropertyValidator<Future, Object> {

    /**
     * ctor
     */
    public FuturePropertyValidator() {
        super(Object.class);
    }

    @Override
    protected boolean checkValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof Date) {
            return checkValidDate((Date) value);
        } else if (value instanceof Calendar) {
            return checkValidCalendar((Calendar) value);
        } else {
            throw new ValidationException("Property contains value of type " + value.getClass() + " whereas only java.util.Date and java.util.Calendar are supported.");
        }
    }

    private boolean checkValidCalendar(Calendar value) {
        return value.after(Calendar.getInstance() );
    }

    private boolean checkValidDate(Date value) {
        return value.after(new Date() );
    }
}
