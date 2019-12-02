package com.countries.countriesAPI.models;

import java.util.ArrayList;
import java.util.List;

public class Country extends BasicCountry{
    private List<Language> languages;
    //borders is the 3 letter string from the borders array, if I change the name GSON doesn't work
    //borders is only used to populate the database
    private List<String> borders;
    //borderCountriesList is for all CRUD and API work
    private List<BasicCountry> borderCountriesList;
    private List<Currency>  currencies;
    private List<RegionalBlock>  regionalBlocs;
    private String alpha3Code;

    public Country(){
        
    }
    
    /**
     * @return the name
     */
    // public String getName() {
    //     return name;
    // }

    // /**
    //  * @param name the name to set
    //  */
    // public void setName(String name) {
    //     this.name = name;
    // }

    // /**
    //  * @return the borders
    //  */
    public List<String> getborders() {
        return borders;
    }
    /**
     * @param borders the borders to set
     */
    public void setborders(List<String> borders) {
        this.borders = borders;
    }

    // /**
    //  * @return the capital
    //  */
    // public String getCapital() {
    //     return capital;
    // }
    // /**
    //  * @param capital the capital to set
    //  */
    // public void setCapital(String capital) {
    //     this.capital = capital;
    // }
    // /**
    //  * @return the population
    //  */
    // public long getPopulation() {
    //     return population;
    // }
    // /**
    //  * @param population the population to set
    //  */
    // public void setPopulation(long population) {
    //     this.population = population;
    // }
    // /**
    //  * @return the languages
    //  */
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
    // /**
    //  * @return the id
    //  */
    // public Integer getId() {
    //     return id;
    // }
    // /**
    //  * @param id the id to set
    //  */
    // public void setId(int id) {
    //     this.id = id;
    // }

    /**
     * @return the borderCountriesList
     */
    public List<BasicCountry> getBorderCountriesList() {
        return borderCountriesList;
    }

    /**
     * @param borderCountriesList the borderCountriesList to set
     */
    public void setBorderCountriesList(List<BasicCountry> borderCountriesList) {
        this.borderCountriesList = borderCountriesList;
    }

    public void addBorderCountry(BasicCountry b){
        if (this.borderCountriesList == null){
            this.borderCountriesList = new ArrayList<BasicCountry>();
        }
        this.borderCountriesList.add(b);
    }
	public void addCurrency(Currency c) {
        if(this.currencies == null){
            this.currencies = new ArrayList<Currency>();
        }
        this.currencies.add(c);
	}

	public void addLanguage(Language l) {
        if(this.languages == null){
            this.languages = new ArrayList<Language>();
        }
        this.languages.add(l);
    }
    
    

    /**
     * @return List<String> return the borders
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
     * @return List<RegionalBlock> return the regionalBlocks
     */
    public List<RegionalBlock> getRegionalBlocks() {
        return regionalBlocs;
    }

    /**
     * @param regionalBlocks the regionalBlocks to set
     */
    public void setRegionalBlocks(List<RegionalBlock> regionalBlocks) {
        this.regionalBlocs = regionalBlocks;
    }

    public void addRegionalBlock(RegionalBlock rb){
        if(this.regionalBlocs == null)
            this.regionalBlocs = new ArrayList<>();
        regionalBlocs.add(rb);
    }

}