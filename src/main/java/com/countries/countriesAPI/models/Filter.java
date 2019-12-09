package com.countries.countriesAPI.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Filter {
    private String countryFilterField;
    private String countryFilterValue;
    private List<Integer> languageFilterList;
    private List<Integer> currencyFilterList;
    private List<Integer> regionalBlockFilterList;

    
    @JsonCreator
    // public Filter(@JsonProperty("countryFilterField") String countryFilterField, @JsonProperty("countryFilterValue") String countryFilterValue ){
    //     this.countryFilterField = countryFilterField;
    //     this.countryFilterValue = countryFilterValue;
    //     this.languageFilterList = languageFilterList;
    //     this.currencyFilterList = currencyFilterList;
    //     this.regionalBlockFilterList = regionalBlockFilterList;
    // }
//,  @JsonProperty("languageFilterList") List<Language> languageFilterList, @JsonProperty("currencyFilterList") List<Currency> currencyFilterList, 
// @JsonProperty("regionalBlockFilterList") List<RegionalBlock> regionalBlockFilterList
    public Filter(){

    }
    /**
     * @return List<Language> return the languageFilterList
     */
    public List<Integer> getLanguageFilterList() {
        return languageFilterList;
    }

    /**
     * @param languageFilterList the languageFilterList to set
     */
    public void setLanguageFilterList(List<Integer> languageFilterList) {
        this.languageFilterList = languageFilterList;
    }

    /**
     * @return List<Currency> return the currencyFilterList
     */
    public List<Integer> getCurrencyFilterList() {
        return currencyFilterList;
    }

    /**
     * @param currencyFilterList the currencyFilterList to set
     */
    public void setCurrencyFilterList(List<Integer> currencyFilterList) {
        this.currencyFilterList = currencyFilterList;
    }

    /**
     * @return List<RegionalBlock> return the regionalBlockFilterList
     */
    public List<Integer> getRegionalBlockFilterList() {
        return regionalBlockFilterList;
    }

    /**
     * @param regionalBlockFilterList the regionalBlockFilterList to set
     */
    public void setRegionalBlockFilterList(List<Integer> regionalBlockFilterList) {
        this.regionalBlockFilterList = regionalBlockFilterList;
    }

    /**
     * @return String return the countryFilterField
     */
    public String getCountryFilterField() {
        return countryFilterField;
    }

    /**
     * @param countryFilterField the countryFilterField to set
     */
    public void setCountryFilterField(String countryFilterField) {
        this.countryFilterField = countryFilterField;
    }

    /**
     * @return String return the countryFilterValue
     */
    public String getCountryFilterValue() {
        return countryFilterValue;
    }

    /**
     * @param countryFilterValue the countryFilterValue to set
     */
    public void setCountryFilterValue(String countryFilterValue) {
        this.countryFilterValue = countryFilterValue;
    }

  
    @Override
        public String toString() {
            return "{" +
                    "countryFilterField=" + countryFilterField + 
                    ", countryFilterValue=" + countryFilterValue + 
                    '}';
        }

}