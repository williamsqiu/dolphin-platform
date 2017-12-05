package com.canoo.platform.samples.microservices.product;

import com.canoo.platform.remoting.server.DolphinAction;
import com.canoo.platform.remoting.server.DolphinController;
import com.canoo.platform.remoting.server.DolphinModel;

import static com.canoo.platform.samples.microservices.product.ProductConstants.PRODUCT_CONTROLLER_NAME;
import static com.canoo.platform.samples.microservices.product.ProductConstants.PRODUCT_REFRESH_ACTION;

@DolphinController(PRODUCT_CONTROLLER_NAME)
public class ProductController {

    private static int counter;

    @DolphinModel
    private ProductBean model;

    @DolphinAction(PRODUCT_REFRESH_ACTION)
    public void refresh() {
        model.setName("Banana " + ++counter);
        model.setPrice(12.50);
    }
}
