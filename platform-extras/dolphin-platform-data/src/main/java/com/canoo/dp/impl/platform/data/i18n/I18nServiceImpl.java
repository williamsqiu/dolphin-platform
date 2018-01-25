package com.canoo.dp.impl.platform.data.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18nServiceImpl implements I18nService {

    private final String bundleBaseName;

    public I18nServiceImpl(final String bundleBaseName) {
        this.bundleBaseName = bundleBaseName;
    }

    @Override
    public String getText(String textKey, Locale locale) {
        return getText(bundleBaseName, textKey, locale);
    }

    @Override
    public String getText(String resourceBaseName, String textKey, Locale locale) {
        ResourceBundle textBundle = Utf8ResourceBundle.getBundle(resourceBaseName, locale);
        return textBundle.getString(textKey);
    }
}
