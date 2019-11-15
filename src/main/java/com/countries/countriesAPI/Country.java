package com.countries.countriesAPI;

import java.util.List;

public class Country {
    private int id;
    private String  name;
    private String capital;
    private List<Language> languages;
    private List<String> borders;
    private List<Currency>  currencies;
    private long population;
    private String alpha3Code;


    public Country(){

    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the borders
     */
    public List<String> getBorders() {
        return borders;
    }
    /**
     * @param borders the borders to set
     */
    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    /**
     * @return the capital
     */
    public String getCapital() {
        return capital;
    }
    /**
     * @param capital the capital to set
     */
    public void setCapital(String capital) {
        this.capital = capital;
    }
    /**
     * @return the population
     */
    public long getPopulation() {
        return population;
    }
    /**
     * @param population the population to set
     */
    public void setPopulation(long population) {
        this.population = population;
    }
    /**
     * @return the languages
     */
    public List<Language> getLanguages() {
        return languages;
    }
    // /**
    //  * @param languages the languages to set
    //  */
    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    /**
     * @return the currencies
     */
    public List<Currency> getCurrencies() {
        return currencies;
    }

    /**
     * @param currencies the currencies to set
     */
    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
    /**
     * @param alpha3Code the alpha3Code to set
     */
    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }
    /**
     * @return the alpha3Code
     */
    public String getAlpha3Code() {
        return alpha3Code;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}