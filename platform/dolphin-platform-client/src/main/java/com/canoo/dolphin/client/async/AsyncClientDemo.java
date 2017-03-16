package com.canoo.dolphin.client.async;

import com.canoo.dolphin.client.async.athomsphere.AthmosClient;

import java.net.URI;

public class AsyncClientDemo {

    public static void main(String[] args) throws Exception {
        AsyncClient client = new AthmosClient();
        AsyncConnection connection = client.connect(new URI("http://localhost:8080/todo-app/bla"), new AsyncHandler() {
            @Override
            public void onConnect(AsyncConnection connection) {
                System.out.println("Connection opened");
            }

            @Override
            public void onClose(AsyncConnection connection) {
                System.out.println("Connection closed");
            }

            @Override
            public void onMessage(AsyncConnection connection, String message) {
                System.out.println("Message: " + message);
                connection.disconnect();
            }

            @Override
            public void onError(AsyncConnection connection, Throwable error) {
                System.out.println("Error: " + error);
            }
        });
        connection.send("huhu");
    }

}
