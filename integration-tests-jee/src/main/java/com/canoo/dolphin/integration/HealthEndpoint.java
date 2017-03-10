package com.canoo.dolphin.integration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/health")
public class HealthEndpoint {

    @GET
    public void check() {

    }

}
