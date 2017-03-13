package com.canoo.dolphin.integration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/health")
public class HealthEndpoint {

    @GET
    public Response check() {
        return Response.status(200).build();
    }

}
