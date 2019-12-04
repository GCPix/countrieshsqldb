package com.countries.countriesAPI.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.countries.countriesAPI.dataAccess.CountryDataAccess;
import com.countries.countriesAPI.models.Country;

@Path("country")
public class CountryController {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCountry(Country country) throws ClassNotFoundException, SQLException, IOException {
        CountryDataAccess cda = new CountryDataAccess();
        Response response;
        int id = -1;

        id = cda.addCountry(country);
        if(id >=0){
            response = Response.status(Status.CREATED).entity(id).build();
        } else {
            response = Response.status(Status.BAD_REQUEST).build();
        }
        return response;
    }
    @Path("{id}")
    @PUT
    public Response updateCountry(@PathParam("id") int countryId, Country country, @QueryParam("deletedCurrencies") List<Integer> deletedCurrencies)
            throws ClassNotFoundException, SQLException {
        Response response;
        CountryDataAccess cda = new CountryDataAccess();
        cda.updateCountry(country, deletedCurrencies);
        response = Response.ok().build();
        return response;
    }
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountry(@PathParam("id") int countryId)
            throws ClassNotFoundException, SQLException, IOException {

        CountryDataAccess cda = new CountryDataAccess();
        Country c = null;
        Response response;

        c = cda.getCountry(countryId);
        
        if (c == null) {
            response =  Response.status(404).entity("no country returned for that id").build();
        } else {
            response = Response.ok(c).build();
        }

        return response;

    }

    @Path("/{id}")
    @DELETE
    public Response deleteCountry(@PathParam("id") int countryId) throws ClassNotFoundException, SQLException {
        CountryDataAccess cda = new CountryDataAccess();
        Response response;
        int noRowsReturned;

        noRowsReturned = cda.deleteCountry(countryId);

        if (noRowsReturned!=0){
            response = Response.status(204).build();
        } else {
            response = Response.status(404).build();
        }
        
        return response;
    }
    
}