package com.canoo.dolphin.samples.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/city")
public class CityEndpoint {

    @RequestMapping(method = RequestMethod.POST)
    public CityDetails getDetails(@RequestBody final City city) {
        final CityDetails cityDetails = new CityDetails(city);
        cityDetails.setDescription("No description");
        cityDetails.setPopulation((long) (Math.random() * 1_000_000));
        return cityDetails;
    }

}
