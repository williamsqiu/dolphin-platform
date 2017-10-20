package com.canoo.dp.impl.validation;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Validator that adds Dolphin Platform property support for the {@link Min} annotation.
 * <p>
 * Note that {@link BigDecimal} and {@link BigInteger} require special treatments, whereas byte, short, int and long
 * can be cast to long and compared to {@link Min} annotations value.
 */
public final class MinPropertyValidator extends AbstractPropertyValidator<Min, Number> {

    private long minValue;

    @Override
    public void initialize(Min minValue) {
        this.minValue = minValue.value();
    }

    /**
     * ctor
     */
    public MinPropertyValidator() {
        super(Number.class);
    }

    @Override
    protected boolean checkValid(Number value, ConstraintValidatorContext context) {
        if (value instanceof BigDecimal) {
            return checkValidBigDecimal((BigDecimal) value);
        } else if (value instanceof BigInteger) {
            return checkValidBigInteger((BigInteger) value);
        } else if (value instanceof Byte ||
                value instanceof Short ||
                value instanceof Integer ||
                value instanceof Long
                ) {
            return value.longValue() >= minValue;
        } else {
            throw new ValidationException("Property contains value of type " +
                    value.getClass() +
                    " whereas only BigDecimal, BigInteger, long, integer, " +
                    "short, byte and their wrappers are supported."
            );
        }
    }

    private boolean checkValidBigInteger(BigInteger value) {
        return value.compareTo(BigInteger.valueOf(minValue) ) != -1;
    }

    private boolean checkValidBigDecimal(BigDecimal value) {
        return value.compareTo(BigDecimal.valueOf(minValue) ) != -1;
    }
}
