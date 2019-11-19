package com.countries.countriesAPI;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.json.JsonString;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("/languages")
public class LanguagesResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLanguages() throws SQLException {
        String jsonString = "";
        Language l = new Language();

        Gson gson = new Gson();
        jsonString = gson.toJson(l.getLanguageList());
        
        return jsonString; 
    }

}