package com.countries.countriesAPI.models;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Currency {
	@JsonIgnore
    private Integer id;
    @NotNull
    private String code;
    @NotNull
    private String name;
    @NotNull
    private String symbol;

    public Currency(){};
    

    /**
     * @return the id
     */
//    @JsonIgnore
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
//    @JsonIgnore
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}