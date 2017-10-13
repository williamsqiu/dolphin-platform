package com.canoo.dolphin.samples.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("city")
public class CityEndpoint {

    @POST
    public CityDetails getDetails(final City city) {
        final CityDetails cityDetails = new CityDetails(city);
        cityDetails.setDescription("No description");
        cityDetails.setPopulation((long) (Math.random() * 1_000_000));
        return cityDetails;
    }
}
