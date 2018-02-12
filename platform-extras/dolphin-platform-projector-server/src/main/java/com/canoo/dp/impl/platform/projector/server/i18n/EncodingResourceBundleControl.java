package com.canoo.dp.impl.platform.projector.server.i18n;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class EncodingResourceBundleControl extends ResourceBundle.Control
{
    private final String encoding;

    public EncodingResourceBundleControl(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale,
                                    String format, ClassLoader loader,
                                    boolean reload)
            throws IllegalAccessException, InstantiationException, IOException
    {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        URL resourceURL = loader.getResource(resourceName);
        if (resourceURL != null)
        {
            try
            {
                return new PropertyResourceBundle(new InputStreamReader(resourceURL.openStream(), encoding));
            }
            catch (Exception z)
            {
                //TODO
            }
        }

        return super.newBundle(baseName, locale, format, loader, reload);
    }
}