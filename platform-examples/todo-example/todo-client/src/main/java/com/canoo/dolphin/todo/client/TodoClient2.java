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
        ClientContext clientContext = ClientContextFactory.connect(clientConfiguration).get();


        clientContext.createController(TodoAppConstants.TODO_CONTROLLER_NAME);
        Thread.sleep(2000);
        System.out.println("Disconnect!");
        clientContext.disconnect();

        //while (true) {
        //    Thread.sleep(2000);
          //  clientContext.createController(TodoAppConstants.TODO_CONTROLLER_NAME);
      //  }

    }
}
