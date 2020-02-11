package com.countries.countriesAPI.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BasicCountry {
    private Integer id;
    @Size(min = 1, max = 150, message = "Country name cannot be more than 150 characters: ")
    @NotNull
    private String name;
    @NotNull
    @Size(min = 0, max = 150, message = "Capital cannot be more than 150 characters: ")
    private String capital;
    @NotNull
    private long population;
    @NotNull
    @Size(max = 200)
    private String flag;
    @NotNull
    @Size(min = 1, max = 150, message = "Region cannot be more than 150 characters: ")
    private String region;

    public BasicCountry(){

    }

    
    /**
     * @param capital the capital to set
     */
    public void setCapital(String capital) {
        this.capital = capital;
    }

    /**
     * @return the capital
     */
    public String getCapital() {
        return capital;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param population the population to set
     */
    public void setPopulation(long population) {
        this.population = population;
    }

    /**
     * @return the population
     */
    public long getPopulation() {
        return population;
    }


    /**
     * @return String return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return String return the Region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param Region the Region to set
     */
    public void setRegion(String Region) {
        this.region = Region;
    }

}