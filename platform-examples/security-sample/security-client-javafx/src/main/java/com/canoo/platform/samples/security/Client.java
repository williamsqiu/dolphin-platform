package com.canoo.platform.samples.security;

import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.security.Security;
import com.canoo.platform.core.http.HttpClient;

public class Client {

    public static void main(String[] args) throws Exception{
        final HttpClient client = PlatformClient.getService(HttpClient.class);

        final String message = client.get("http://localhost:8080/api/message").
                withoutContent().
                readString().execute().get().getContent();
        System.out.println(message);

        final Security security = PlatformClient.getService(Security.class);
        security.login("admin", "admin").get();

        final String message2 = client.get("http://localhost:8080/api/secure/message").
                withoutContent().
                readString().execute().get().getContent();
        System.out.println(message2);
    }
}
