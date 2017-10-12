package com.canoo.dolphin.samples.rest;

public class CityDetails extends City {

    private String description;

    private long population;

    public CityDetails() {
    }

    public CityDetails(City city) {
        super(city.getName(), city.getCountry());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }
}
