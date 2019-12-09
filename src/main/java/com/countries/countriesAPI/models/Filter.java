package com.countries.countriesAPI.models;

import java.util.List;

import com.countries.countriesAPI.models.Currency;
import com.countries.countriesAPI.models.Language;
import com.countries.countriesAPI.models.RegionalBlock;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Filter {
    private String countryFilterField;
    private String countryFilterValue;
    private List<Language> languageFilterList;
    private List<Currency> currencyFilterList;
    private List<RegionalBlock> regionalBlockFilterList;

    
    @JsonCreator
    public Filter(@JsonProperty("countryFilterField") String countryFilterField, @JsonProperty("countryFilterValue") String countryFilterValue ){
        this.countryFilterField = countryFilterField;
        this.countryFilterValue = countryFilterValue;
        // this.languageFilterList = languageFilterList;
        // this.currencyFilterList = currencyFilterList;
        // this.regionalBlockFilterList = regionalBlockFilterList;
    }
//,  @JsonProperty("languageFilterList") List<Language> languageFilterList, @JsonProperty("currencyFilterList") List<Currency> currencyFilterList, 
// @JsonProperty("regionalBlockFilterList") List<RegionalBlock> regionalBlockFilterList
    public Filter(){

    }
    // /**
    //  * @return List<Language> return the languageFilterList
    //  */
    // public List<Language> getLanguageFilterList() {
    //     return languageFilterList;
    // }

    // /**
    //  * @param languageFilterList the languageFilterList to set
    //  */
    // public void setLanguageFilterList(List<Language> languageFilterList) {
    //     this.languageFilterList = languageFilterList;
    // }

    // /**
    //  * @return List<Currency> return the currencyFilterList
    //  */
    // public List<Currency> getCurrencyFilterList() {
    //     return currencyFilterList;
    // }

    // /**
    //  * @param currencyFilterList the currencyFilterList to set
    //  */
    // public void setCurrencyFilterList(List<Currency> currencyFilterList) {
    //     this.currencyFilterList = currencyFilterList;
    // }

    // /**
    //  * @return List<RegionalBlock> return the regionalBlockFilterList
    //  */
    // public List<RegionalBlock> getRegionalBlockFilterList() {
    //     return regionalBlockFilterList;
    // }

    // /**
    //  * @param regionalBlockFilterList the regionalBlockFilterList to set
    //  */
    // public void setRegionalBlockFilterList(List<RegionalBlock> regionalBlockFilterList) {
    //     this.regionalBlockFilterList = regionalBlockFilterList;
    // }

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

    // public String toJsonString() throws JsonProcessingException {
    //     String jsonFilterString;
        
    //     String s = "{\"countryFilterField\":\"" + this.countryFilterField + "\",\"countryFilterValue\":\"" + this.countryFilterValue + "\"}";
    //     jsonFilterString = new ObjectMapper().writeValueAsString(s);
    //     return jsonFilterString;
    // }

    @Override
        public String toString() {
            return "{" +
                    "countryFilterField=" + countryFilterField + 
                    ", countryFilterValue=" + countryFilterValue + 
                    '}';
        }

}