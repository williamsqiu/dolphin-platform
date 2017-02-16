package com.canoo.dolphin.todo.server;

import com.canoo.dolphin.client.Param;
import com.canoo.dolphin.test.ControllerUnderTest;
import com.canoo.dolphin.test.SpringTestNGControllerTest;
import com.canoo.dolphin.todo.pm.ToDoList;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.canoo.dolphin.todo.TodoAppConstants.ADD_ACTION;
import static com.canoo.dolphin.todo.TodoAppConstants.CHANGE_ACTION;
import static com.canoo.dolphin.todo.TodoAppConstants.ITEM_PARAM;
import static com.canoo.dolphin.todo.TodoAppConstants.REMOVE_ACTION;
import static com.canoo.dolphin.todo.TodoAppConstants.TODO_CONTROLLER_NAME;

@SpringApplicationConfiguration(ToDoServerConfiguration.class)
public class ToDoControllerTests extends SpringTestNGControllerTest {

    @Test
    public void testAddElement() {

        //given:
        ControllerUnderTest<ToDoList> controllerUnderTest = createController(TODO_CONTROLLER_NAME);

        //when:
        controllerUnderTest.getModel().getNewItemText().set("Banana");
        controllerUnderTest.invoke(ADD_ACTION);

        //then:
        Assert.assertEquals(controllerUnderTest.getModel().getItems().size() ,1);
        Assert.assertEquals(controllerUnderTest.getModel().getItems().get(0).getText() ,"Banana");
        Assert.assertEquals(controllerUnderTest.getModel().getItems().get(0).isCompleted() ,false);
    }

    @Test
    public void testChangeElementState() {

        //given:
        ControllerUnderTest<ToDoList> controllerUnderTest = createController(TODO_CONTROLLER_NAME);

        //when:
        controllerUnderTest.getModel().getNewItemText().set("Banana");
        controllerUnderTest.invoke(ADD_ACTION);
        controllerUnderTest.invoke(CHANGE_ACTION, new Param(ITEM_PARAM, "Banana"));

        //then:
        Assert.assertEquals(controllerUnderTest.getModel().getItems().get(0).isCompleted() ,true);
    }

    @Test
    public void testDeleteElement() {

        //given:
        ControllerUnderTest<ToDoList> controllerUnderTest = createController(TODO_CONTROLLER_NAME);

        //when:
        controllerUnderTest.getModel().getNewItemText().set("Banana");
        controllerUnderTest.invoke(ADD_ACTION);
        controllerUnderTest.invoke(REMOVE_ACTION, new Param(ITEM_PARAM, "Banana"));

        //then:
        Assert.assertEquals(controllerUnderTest.getModel().getItems().size() ,0);
    }

    @Test
    public void testInitialElements() {

        //given:
        ControllerUnderTest<ToDoList> controllerUnderTest = createController(TODO_CONTROLLER_NAME);

        //when:
        controllerUnderTest.getModel().getNewItemText().set("Banana");
        controllerUnderTest.invoke(ADD_ACTION);
        ControllerUnderTest<ToDoList> controllerUnderTest2 = createController(TODO_CONTROLLER_NAME);

        //then:
        Assert.assertEquals(controllerUnderTest2.getModel().getItems().size() ,1);
        Assert.assertEquals(controllerUnderTest2.getModel().getItems().get(0).getText() ,"Banana");
    }

    @Test
    public void testElementSync() {

        //given:
        ControllerUnderTest<ToDoList> controllerUnderTest = createController(TODO_CONTROLLER_NAME);
        ControllerUnderTest<ToDoList> controllerUnderTest2 = createController(TODO_CONTROLLER_NAME);


        //when:
        controllerUnderTest.getModel().getNewItemText().set("Banana");
        controllerUnderTest.invoke(ADD_ACTION);

        //then:
        Assert.assertEquals(controllerUnderTest2.getModel().getItems().size() ,1);
        Assert.assertEquals(controllerUnderTest2.getModel().getItems().get(0).getText() ,"Banana");
    }

    @Test
    public void testElementStateSync() {

        //given:
        ControllerUnderTest<ToDoList> controllerUnderTest = createController(TODO_CONTROLLER_NAME);
        ControllerUnderTest<ToDoList> controllerUnderTest2 = createController(TODO_CONTROLLER_NAME);


        //when:
        controllerUnderTest.getModel().getNewItemText().set("Banana");
        controllerUnderTest.invoke(ADD_ACTION);
        controllerUnderTest.invoke(CHANGE_ACTION, new Param(ITEM_PARAM, "Banana"));

        //then:
        Assert.assertEquals(controllerUnderTest2.getModel().getItems().size() ,1);
        Assert.assertEquals(controllerUnderTest2.getModel().getItems().get(0).isCompleted() ,true);
    }

    @Test
    public void testElementDeleteSync() {

        //given:
        ControllerUnderTest<ToDoList> controllerUnderTest = createController(TODO_CONTROLLER_NAME);
        ControllerUnderTest<ToDoList> controllerUnderTest2 = createController(TODO_CONTROLLER_NAME);


        //when:
        controllerUnderTest.getModel().getNewItemText().set("Banana");
        controllerUnderTest.invoke(ADD_ACTION);
        controllerUnderTest.invoke(REMOVE_ACTION, new Param(ITEM_PARAM, "Banana"));

        //then:
        Assert.assertEquals(controllerUnderTest2.getModel().getItems().size() ,0);
    }
}
