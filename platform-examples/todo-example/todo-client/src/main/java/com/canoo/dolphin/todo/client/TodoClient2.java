package com.canoo.dolphin.todo.client;

import com.canoo.dolphin.client.ClientConfiguration;
import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.ClientContextFactory;
import com.canoo.dolphin.todo.TodoAppConstants;
import com.canoo.dolphin.util.Callback;
import org.opendolphin.util.DolphinRemotingException;

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

        ClientContext clientContext = ClientContextFactory.connect(clientConfiguration).get();
        clientContext.onRemotingError(new Callback<DolphinRemotingException>() {
            @Override
            public void call(DolphinRemotingException e) {
                e.printStackTrace();
            }
        });


        while (true) {
            Thread.sleep(2000);
            clientContext.createController(TodoAppConstants.TODO_CONTROLLER_NAME);
        }

    }


}
