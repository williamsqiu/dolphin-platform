package com.canoo.dp.impl.platform.projector.server.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleTranslator implements Translator {

    private final String baseName;

    private final String encoding;

    public ResourceBundleTranslator(String baseName) {
        this(baseName, "utf-8");
    }

    public ResourceBundleTranslator(String baseName, String encoding) {
        this.baseName = baseName;
        this.encoding = encoding;
    }


    @Override
    public String translate(Locale locale, String key, Object... args) {
        return ResourceBundle.getBundle(baseName, locale, new EncodingResourceBundleControl(encoding)).getString(key);
    }
}

