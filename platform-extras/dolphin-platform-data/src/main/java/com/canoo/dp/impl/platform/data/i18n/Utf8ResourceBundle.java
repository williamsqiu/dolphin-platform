package com.canoo.dp.impl.platform.data.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


/**
 * UTF-8 friendly ResourceBundle support
 */
public class Utf8ResourceBundle {

    private final static String ISO_8859_1 = "ISO-8859-1";

    private final static String UTF_8 = "UTF-8";

    /**
     * Gets the unicode friendly resource bundle
     *
     * @param baseName
     * @return Unicode friendly resource bundle
     * @see java.util.ResourceBundle#getBundle(String)
     */
    public static final ResourceBundle getBundle(final String baseName, final Locale locale) {
        return createUtf8PropertyResourceBundle(ResourceBundle.getBundle(baseName, locale));
    }

    /**
     * Creates unicode friendly {@link java.util.PropertyResourceBundle} if possible.
     *
     * @param bundle
     * @return Unicode friendly property resource bundle
     */
    private static ResourceBundle createUtf8PropertyResourceBundle(final ResourceBundle bundle) {
        if (!(bundle instanceof PropertyResourceBundle)) {
            return bundle;
        }
        return new Utf8PropertyResourceBundle((PropertyResourceBundle) bundle);
    }

    /**
     * Resource Bundle that does the hard work
     */
    private static class Utf8PropertyResourceBundle extends ResourceBundle {

        /**
         * Bundle with unicode data
         */
        private final PropertyResourceBundle bundle;

        /**
         * Initializing constructor
         *
         * @param bundle
         */
        private Utf8PropertyResourceBundle(final PropertyResourceBundle bundle) {
            this.bundle = bundle;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Enumeration getKeys() {
            return bundle.getKeys();
        }

        @Override
        protected Object handleGetObject(final String key) {
            final String value = bundle.getString(key);
            if (value == null)
                return null;
            try {
                return new String(value.getBytes(ISO_8859_1), UTF_8);
            } catch (final UnsupportedEncodingException e) {
                throw new RuntimeException("Encoding not supported", e);
            }
        }
    }
}
