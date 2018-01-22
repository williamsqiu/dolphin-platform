package com.canoo.dp.impl.platform.projector.server.i18n;

import java.util.Locale;

public interface Translator {

    String translate(Locale locale, String key, Object... args);
}
