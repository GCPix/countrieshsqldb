package com.countries.countriesAPI.controllers;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import com.countries.countriesAPI.dataAccess.LanguageDataAccess;
import com.google.gson.Gson;

@Path("languages")
public class LanguagesController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLanguages() throws SQLException {
        String jsonString = "";
        LanguageDataAccess l = new LanguageDataAccess();

        Gson gson = new Gson();
        jsonString = gson.toJson(l.getLanguageList());
        
        return jsonString; 
    }

}