package com.canoo.dp.impl.platform.projector.server.i18n;

import java.util.Locale;

public class EchoTranslator implements Translator {

    @Override
    public String translate(Locale locale, String key, Object... args) {
        return key;
    }
}
