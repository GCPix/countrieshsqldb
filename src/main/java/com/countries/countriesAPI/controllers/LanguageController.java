package com.countries.countriesAPI.controllers;

import java.io.IOException;
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
import javax.ws.rs.core.Response.Status;

import com.countries.countriesAPI.dataAccess.LanguageDataAccess;
import com.countries.countriesAPI.models.Language;

@Path("language")
public class LanguageController {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addLanguage(Language language) throws SQLException, IOException, ClassNotFoundException {
        Response response;
        LanguageDataAccess lda = new LanguageDataAccess();
        int id = -1;
        id = lda.addLanguage(language);

        // below used to create a return value that would include the path to the new addition sticking to poc example so changing to id
        // URI uri = UriBuilder.fromResource(LanguageController.class).path("language/{id}").build(Integer.toString(id));
        // return Response.created(uri).entity(id).build();
        if (id >= 0){
            response = Response.status(Status.CREATED).entity(id).build();
        } else {
            response = Response.status(Status.BAD_REQUEST).build();
        }
        return response;
    }

    @GET
    @Path("/{languageid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLanguage(@PathParam("languageid") int languageId) throws SQLException, IOException, ClassNotFoundException {

        LanguageDataAccess lda = new LanguageDataAccess();
        Language lan = null;
        Response response = null;
        
        lan = lda.getLanguage(languageId);
        
        if (lan == null) {

            response = Response.status(404).entity("Language not returned").build();
        }
        
        response = Response.ok(lan).build();
        return response;
    }

    @DELETE
    @Path("/{languageid}")
    public Response deleteLanguage(@PathParam("languageid") int languageId) throws SQLException, ClassNotFoundException {
        LanguageDataAccess lda = new LanguageDataAccess();
        Response response;
        int noRowsReturned;
        
        noRowsReturned = lda.deleteLanguage(languageId);

        if (noRowsReturned != 0) {
            response = Response.noContent().build();
        } else {
            response = Response.status(Status.NOT_FOUND).entity("it would appear the id is wrong").build();
        }
        return response;
    }

    @PUT
    @Path("/{languageid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLanguage(@PathParam("languageid") int languageId, Language language) throws SQLException, ClassNotFoundException {
        LanguageDataAccess lda = new LanguageDataAccess();
        Response response = null;
        //not sure if I need bit below front end would need to ensure the types are valid
        if (languageId >= 0 && language != null) {
  
            lda.updateLanguage(languageId, language);
            response = Response.noContent().build();
             
        } else {
            response = Response.status(Status.BAD_REQUEST).entity("please check your details for your update, you appear to have a formatting issue").build();
            
        }
        return response;
    }


    
}