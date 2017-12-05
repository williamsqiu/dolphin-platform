package com.canoo.dolphin.integration.server.runLater;

import com.canoo.dolphin.integration.runlater.RunLaterTestBean;
import com.canoo.dolphin.integration.server.TestConfiguration;
import com.canoo.platform.spring.test.AsyncCondition;
import com.canoo.platform.spring.test.ControllerUnderTest;
import com.canoo.platform.spring.test.SpringTestNGControllerTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.canoo.dolphin.integration.runlater.RunLaterTestConstants.RUN_LATER_ACTION_NAME;
import static com.canoo.dolphin.integration.runlater.RunLaterTestConstants.RUN_LATER_ASYNC_ACTION_NAME;
import static com.canoo.dolphin.integration.runlater.RunLaterTestConstants.RUN_LATER_CONTROLLER_NAME;

@SpringBootTest(classes = TestConfiguration.class)
public class RunLaterControllerTest extends SpringTestNGControllerTest {

    @Test
    public void testRunLaterInPostConstruct() {
        //given:
        final ControllerUnderTest<RunLaterTestBean> controller = createController(RUN_LATER_CONTROLLER_NAME);
        final RunLaterTestBean model = controller.getModel();

        //then
        Assert.assertNotNull(model.getPostConstructPreRunLaterCallIndex());
        Assert.assertNotNull(model.getPostConstructRunLaterCallIndex());
        Assert.assertNotNull(model.getPostConstructPostRunLaterCallIndex());

        Assert.assertTrue(model.getPostConstructPreRunLaterCallIndex() > 0);
        Assert.assertTrue(model.getPostConstructRunLaterCallIndex() > 0);
        Assert.assertTrue(model.getPostConstructPostRunLaterCallIndex() > 0);

        Assert.assertTrue(model.getPostConstructPreRunLaterCallIndex() < model.getPostConstructPostRunLaterCallIndex());
        Assert.assertTrue(model.getPostConstructPostRunLaterCallIndex() < model.getPostConstructRunLaterCallIndex());

    }

    @Test
    public void testRunLaterInAction() {
        //given:
        final ControllerUnderTest<RunLaterTestBean> controller = createController(RUN_LATER_CONTROLLER_NAME);
        final RunLaterTestBean model = controller.getModel();

        //when:
        controller.invoke(RUN_LATER_ACTION_NAME);

        //then:
        Assert.assertNotNull(model.getActionPreRunLaterCallIndex());
        Assert.assertNotNull(model.getActionRunLaterCallIndex());
        Assert.assertNotNull(model.getActionPostRunLaterCallIndex());

        Assert.assertTrue(model.getActionPreRunLaterCallIndex() > 0);
        Assert.assertTrue(model.getActionRunLaterCallIndex() > 0);
        Assert.assertTrue(model.getActionPostRunLaterCallIndex() > 0);

        Assert.assertTrue(model.getActionPreRunLaterCallIndex() < model.getActionPostRunLaterCallIndex());
        Assert.assertTrue(model.getActionPostRunLaterCallIndex() < model.getActionRunLaterCallIndex());
    }

    @Test
    public void testRunLaterAsyncInAction() throws Exception {
        //given:
        final ControllerUnderTest<RunLaterTestBean> controller = createController(RUN_LATER_CONTROLLER_NAME);
        final RunLaterTestBean model = controller.getModel();
        final AsyncCondition condition = controller.createAsyncCondition();
        model.actionRunLaterAsyncCallIndexProperty().onChanged(e -> {
            condition.signal();
        });

        //when:
        controller.invoke(RUN_LATER_ASYNC_ACTION_NAME);
        condition.await(10, TimeUnit.SECONDS);

        //then:
        Assert.assertNotNull(model.getActionPreRunLaterAsyncCallIndex());
        Assert.assertNotNull(model.getActionRunLaterAsyncCallIndex());
        Assert.assertNotNull(model.getActionPostRunLaterAsyncCallIndex());

        Assert.assertTrue(model.getActionPreRunLaterAsyncCallIndex() > 0);
        Assert.assertTrue(model.getActionRunLaterAsyncCallIndex() > 0);
        Assert.assertTrue(model.getActionPostRunLaterAsyncCallIndex() > 0);

        Assert.assertTrue(model.getActionPreRunLaterAsyncCallIndex() < model.getActionPostRunLaterAsyncCallIndex());
        Assert.assertTrue(model.getActionPostRunLaterAsyncCallIndex() < model.getActionRunLaterAsyncCallIndex());
    }
}
