package com.canoo.platform.samples.microservices.product;

import com.canoo.platform.remoting.server.RemotingAction;
import com.canoo.platform.remoting.server.RemotingController;
import com.canoo.platform.remoting.server.RemotingModel;

import static com.canoo.platform.samples.microservices.product.ProductConstants.PRODUCT_CONTROLLER_NAME;
import static com.canoo.platform.samples.microservices.product.ProductConstants.PRODUCT_REFRESH_ACTION;

@RemotingController(PRODUCT_CONTROLLER_NAME)
public class ProductController {

    private static int counter;

    @RemotingModel
    private ProductBean model;

    @RemotingAction(PRODUCT_REFRESH_ACTION)
    public void refresh() {
        model.setName("Banana " + ++counter);
        model.setPrice(12.50);
    }
}
