package com.countries.countriesAPI.controllers;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.countries.countriesAPI.dataAccess.LanguageDataAccess;
import com.countries.countriesAPI.models.Language;
import com.google.gson.Gson;

@Path("language")
public class LanguageController {

    @GET
    @Path("/{languageid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLanguage(@PathParam("languageid") int languageId) throws SQLException {
        LanguageDataAccess lda = new LanguageDataAccess();
        Language lan = lda.getLanguage(languageId);
        Gson gson = new Gson();
        String jsonString = gson.toJson(lan);
        return jsonString; 
    }
    @DELETE
    @Path("/{languageid}")
    public void deleteLanguage(@PathParam("languageid") int languageId) throws SQLException {
        LanguageDataAccess lda = new LanguageDataAccess();
        lda.deleteLanguage(languageId);
        
    }

    @PUT
    @Path("/{languageid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateLanguage(@PathParam("languageid") int languageId, Language language) throws SQLException {
        LanguageDataAccess lda = new LanguageDataAccess();
        lda.updateLanguage(languageId, language);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int addLanguage(Language language) throws SQLException {

        LanguageDataAccess lda = new LanguageDataAccess();
        int id = lda.addLanguage(language);

        return id;
        
    }
    
}