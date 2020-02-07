package com.countries.countriesAPI.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Language {
    @JsonIgnore
    private Integer id;
    @NotNull
    @Size(min = 2, max = 2, message = "iso639_1 will always be a two character string: ${validatedValue} ")
    private String iso639_1;
    @NotNull
    @Size(min = 3, max = 3, message = "iso639_2 this will always be a three character string:")
    private String iso639_2;
    @NotNull
    @Size(min = 1, max = 150, message = "name this must be between 1 and 150 characters (inclusive):")
    private String name;
    @NotNull
    @Size(min = 1, max = 150, message = "nativeName this must be between 1 and 150 characters (inclusive):")
    private String nativeName;

    public Language() {

    }

    /**
     * @return the id
     */

    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the iso639_1
     */
    public String getIso639_1() {
        return iso639_1;
    }

    /**
     * @param iso639_1 the iso639_1 to set
     */
    public void setIso639_1(String iso639_1) {
        this.iso639_1 = iso639_1;
    }

    /**
     * @return the iso639_2
     */
    public String getIso639_2() {
        return iso639_2;
    }

    /**
     * @param iso639_2 the iso639_2 to set
     */
    public void setIso639_2(String iso639_2) {
        this.iso639_2 = iso639_2;
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
     * @return the nativeName
     */
    public String getNativeName() {
        return nativeName;
    }

    /**
     * @param nativeName the nativeName to set
     */
    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }
   
}