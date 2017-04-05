package com.canoo.dolphin.todo.client;

import com.canoo.dolphin.client.ClientConfiguration;
import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.ClientContextFactory;
import com.canoo.dolphin.todo.TodoAppConstants;

import java.net.URL;
import java.util.concurrent.Executor;

public class TodoClient2 {

    public static void main(String[] args) throws Exception{

        ClientConfiguration clientConfiguration = new ClientConfiguration(new URL("http://localhost:8080/todo-app/dolphin"), new Executor() {
            @Override
            public void execute(Runnable command) {
                command.run();
            }
        });
        clientConfiguration.setRemotingExceptionHandler(e -> e.printStackTrace());

        System.out.println("Connect!");
        ClientContext clientContext = ClientContextFactory.connect(clientConfiguration).get();
        System.out.println("Create Controller!");
        clientContext.createController(TodoAppConstants.TODO_CONTROLLER_NAME);
        Thread.sleep(1000);
        System.out.println("Reconnect!");
        clientContext.reconnect().get();
        System.out.println("Create Controller!");
        clientContext.createController(TodoAppConstants.TODO_CONTROLLER_NAME);
        Thread.sleep(1000);
        System.out.println("Disconnect!");
        clientContext.disconnect().get();
        Thread.sleep(10000);
    }
}
