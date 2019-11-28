package com.countries.countriesAPI.models;

public class BasicCountry {
    private Integer id;
    private String name;
    private String capital;
    private long population;

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
    public void setId(int id) {
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
}