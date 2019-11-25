package com.countries.countriesAPI.controllers;

import java.net.URI;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.countries.countriesAPI.dataAccess.LanguageDataAccess;
import com.countries.countriesAPI.models.Language;

@Path("language")
public class LanguageController {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addLanguage(Language language) throws SQLException {
        
        LanguageDataAccess lda = new LanguageDataAccess();
        int id = lda.addLanguage(language);

        URI uri = UriBuilder.fromResource(LanguageController.class).path("language/{id}").build(Integer.toString(id));
        return Response.created(uri).entity(id).build();
        
    }

    @GET
    @Path("/{languageid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLanguage(@PathParam("languageid") int languageId) throws SQLException {
        
        LanguageDataAccess lda = new LanguageDataAccess();
        Language lan = lda.getLanguage(languageId);

        if (lan == null){

            return Response.status(404).entity("Language not returned").build();
        }
        return Response.ok(lan).build();
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


    
}