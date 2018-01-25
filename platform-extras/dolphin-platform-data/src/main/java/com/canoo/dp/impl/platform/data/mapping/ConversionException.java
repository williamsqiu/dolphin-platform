package com.canoo.dp.impl.platform.data.mapping;

public class ConversionException extends RuntimeException {

    public ConversionException(final String message) {
        super(message);
    }

    public ConversionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
