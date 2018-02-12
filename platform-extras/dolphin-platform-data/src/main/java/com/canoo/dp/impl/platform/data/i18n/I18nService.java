package com.canoo.dp.impl.platform.data.i18n;

import java.util.Locale;

public interface I18nService {

    String getText(String textKey, Locale locale);

    String getText(String resourceBaseName, String textKey, Locale locale);
}
