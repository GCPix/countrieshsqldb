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
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCountry(Country country) {
        CountryDataAccess cda = new CountryDataAccess();
        Response response;
        int id = -1;

        try {
            id = cda.addCountry(country);
        } catch (ClassNotFoundException | SQLException | IOException e) {

            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong, contact someone who can sort it").build();
        }
        if (id >= 0) {
            response = Response.status(Status.CREATED).entity(id).build();
        } else {
            response = Response.status(Status.BAD_REQUEST).entity("something went wrong, check the data you have sent through").build();
        }
        return response;
    }

    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCountry(@PathParam("id") int countryId, Country country,
            @QueryParam("deletedCurrencies") List<Integer> deletedCurrencies,
            @QueryParam("deletedLanguages") List<Integer> deletedLanguages,
            @QueryParam("deletedBorders") List<Integer> deletedBorders,
            @QueryParam("deletedRegionalBlocks") List<Integer> deletedRegionalBlocks) {
        Response response;
        CountryDataAccess cda = new CountryDataAccess();
        try {
            cda.updateCountry(country, deletedCurrencies, deletedRegionalBlocks, deletedRegionalBlocks,
                    deletedRegionalBlocks);
        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong, contact someone who can sort it").build();
        }
        response = Response.ok().build();
        return response;
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountry(@PathParam("id") int countryId) {

        CountryDataAccess cda = new CountryDataAccess();
        Country c = null;
        Response response;

        try {
            c = cda.getCountry(countryId);
        } catch (ClassNotFoundException | SQLException | IOException e) {

            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong, contact someone who can sort it").build();
        }

        if (c.getId() == null) {
            response = Response.status(404).entity("no country returned for that id").build();
        } else {
            response = Response.ok(c).build();
        }

        return response;

    }

    @Path("/{id}")
    @DELETE
    public Response deleteCountry(@PathParam("id") int countryId) {
        CountryDataAccess cda = new CountryDataAccess();
        Response response;
        int noRowsReturned = 0;

        try {
            noRowsReturned = cda.deleteCountry(countryId);
        } catch (ClassNotFoundException | SQLException e) {
            
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        }

        if (noRowsReturned!=0){
            response = Response.status(204).build();
        } else {
            response = Response.status(404).entity("We were unable to delete the record, please check the ID").build();
        }
        
        return response;
    }
    
}