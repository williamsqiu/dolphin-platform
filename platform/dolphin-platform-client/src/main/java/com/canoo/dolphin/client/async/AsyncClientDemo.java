package com.canoo.dolphin.client.async;

import com.canoo.dolphin.client.async.athomsphere.AthmosClient;
import org.opendolphin.core.client.comm.ExceptionHandler;

import java.io.IOException;
import java.net.URI;

public class AsyncClientDemo {

    public static void main(String[] args) throws Exception {
        AsyncClient client = new AthmosClient();
        AsyncConnection connection = client.connect(new URI("http://localhost:8086/todo-app/dolphin"), new AsyncHandler() {
            @Override
            public void onConnect(AsyncConnection connection) {
                System.out.println("Connection opened");
                try {
                    connection.send("huhu");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(AsyncConnection connection) {
                System.out.println("Connection closed");
            }

            @Override
            public void onMessage(AsyncConnection connection, String message) {
                System.out.println("Message: " + message);

                try {
                    Thread.sleep(1_000);
                    connection.send("huhu");
                } catch (Exception e) {
                    e.printStackTrace();
                }


               // connection.disconnect();
            }

            @Override
            public void onError(AsyncConnection connection, Throwable error) {
                System.out.println("Error: " + error);
            }
        });
    }

}
