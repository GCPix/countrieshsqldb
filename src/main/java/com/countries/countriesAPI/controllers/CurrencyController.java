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

import com.countries.countriesAPI.dataAccess.CurrencyDataAccess;
import com.countries.countriesAPI.models.Currency;

@Path("currency")
public class CurrencyController {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrency(@PathParam("id") int currencyId) {
        Response response;
        CurrencyDataAccess cda = new CurrencyDataAccess();
        Currency c = null;
        try {
            c = cda.getCurrency(currencyId);
        } catch (ClassNotFoundException | SQLException | IOException e) {

            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong, contact someone who can sort it").build();
        }
        
        if (c != null) {
            response = Response.ok().entity(c).build();

        } else {
            
            String errorString = "Something went wrong check your data";
            response = Response.status(404).entity(errorString).build();

        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response addCurrency(Currency currency) {
        Response response;
        CurrencyDataAccess cda = new CurrencyDataAccess();
        int currencyId = -1;
        try {
            currencyId = cda.addCurrency(currency);
        } catch (ClassNotFoundException | SQLException | IOException e) {

            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong, contact someone who can sort it").build();
        }
        // response includes address created here as a header Location - didn't use in
        // POC but leaving as example
        // URI createdUri =
        // UriBuilder.fromResource(CurrencyController.class).path("/{id}").build(Integer.toString(currencyId));
        if (currencyId >= 0) {
            response = Response.status(Status.CREATED).entity(currencyId).build();
        } else {
            response = Response.status(Status.BAD_REQUEST).entity("Something went wrong, check your data").build();
        }

        return response;

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCurrency(@PathParam("id") int currencyId, Currency currency) {
        Response response;
        CurrencyDataAccess cda = new CurrencyDataAccess();

        int noRowsReturned = 0;
        try {
            noRowsReturned = cda.updateCurrency(currencyId, currency);
        } catch (ClassNotFoundException | SQLException | IOException e) {

            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong, contact someone who can sort it").build();
        }
        if (noRowsReturned != 0) {
            response = Response.noContent().build();
        } else {
            response = Response.status(Status.BAD_REQUEST).entity("something went wrong check your data").build();
        }

        return response;

    }

    @DELETE
    @Path("{id}")
    public Response deleteCurrency(@PathParam("id") int currencyId) {
        Response response;
        CurrencyDataAccess cda = new CurrencyDataAccess();
        int noRowsReturned =0;
        try {
            noRowsReturned = cda.deleteCurrency(currencyId);
        } catch (ClassNotFoundException | SQLException e) {
            
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        }
        if(noRowsReturned != 0){
            response = Response.noContent().build();
        } else {
            response = Response.status(Status.NOT_FOUND).entity("Couldn't delete  the currency from the database, check your ID").build();
        }
        
        return response;
    }
}