package com.canoo.dp.impl.validation;

import org.apiguardian.api.API;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Size;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.19.0", status = INTERNAL)
public class SizePropertyValidator extends AbstractPropertyValidator<Size, String> {

    private int maxSizeValue;
    private int minSizeValue;

    public SizePropertyValidator() {
        super(String.class);
    }

    @Override
    public void initialize(Size constraintAnnotation) {
        this.minSizeValue = constraintAnnotation.min();
        this.maxSizeValue = constraintAnnotation.max();
    }

    @Override
    protected boolean checkValid(String value, ConstraintValidatorContext context) {
        return (value != null && value.length() >= minSizeValue && value.length() <= maxSizeValue);
    }
}
