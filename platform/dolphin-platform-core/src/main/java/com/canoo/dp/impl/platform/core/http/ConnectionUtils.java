/*
 * Copyright 2015-2018 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dp.impl.platform.core.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.http.ByteArrayProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import static com.canoo.dp.impl.platform.core.http.HttpHeaderConstants.CHARSET;

public class ConnectionUtils {

    private ConnectionUtils() {}

    public static byte[] readContent(final InputStream inputStream) throws IOException {
        Assert.requireNonNull(inputStream, "inputStream");
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int read = inputStream.read();
            while (read != -1) {
                byteArrayOutputStream.write(read);
                read = inputStream.read();
            }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] readContent(final HttpURLConnection connection) throws IOException {
        Assert.requireNonNull(connection, "connection");
        final InputStream errorstream = connection.getErrorStream();
        if(errorstream == null) {
            try (final InputStream inputStream = connection.getInputStream()) {
                return readContent(inputStream);
            }
        } else {
            try {
                return readContent(errorstream);
            } finally {
                errorstream.close();
            }
        }
    }

    public static String readUTF8Content(final HttpURLConnection connection) throws IOException {
        return new String(readContent(connection), CHARSET);
    }

    public static String readUTF8Content(final InputStream inputStream) throws IOException {
        return new String(readContent(inputStream), CHARSET);
    }

    public static void writeContent(final OutputStream outputStream, final byte[] rawData) throws IOException {
        Assert.requireNonNull(outputStream, "outputStream");
        Assert.requireNonNull(rawData, "rawData");
        outputStream.write(rawData);
    }

    public static void writeContent(final HttpURLConnection connection, final byte[] rawData) throws IOException {
        Assert.requireNonNull(connection, "connection");
        Assert.requireNonNull(rawData, "rawData");
        try(final OutputStream outputStream = connection.getOutputStream()) {
            writeContent(outputStream, rawData);
        }
    }

    public static void writeContent(final HttpURLConnection connection, final ByteArrayProvider provider) throws IOException {
        Assert.requireNonNull(provider, "provider");
        writeContent(connection, provider.get());
    }

    public static void writeContent(final OutputStream outputStream, final ByteArrayProvider provider) throws IOException {
        Assert.requireNonNull(provider, "provider");
        writeContent(outputStream, provider.get());
    }

    public static void writeUTF8Content(final HttpURLConnection connection, final String content) throws IOException {
        Assert.requireNonNull(content, "content");
        writeContent(connection, content.getBytes(CHARSET));
    }

    public static void writeUTF8Content(final OutputStream outputStream, final String content) throws IOException {
        Assert.requireNonNull(content, "content");
        writeContent(outputStream, content.getBytes(CHARSET));
    }
}
