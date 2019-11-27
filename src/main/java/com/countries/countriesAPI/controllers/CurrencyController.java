package com.countries.countriesAPI.controllers;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;

import com.countries.countriesAPI.dataAccess.CurrencyDataAccess;
import com.countries.countriesAPI.models.Currency;

@Path("currency")
public class CurrencyController {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrency(@PathParam("id") int currencyId) throws SQLException, IOException, ClassNotFoundException {

        CurrencyDataAccess cda = new CurrencyDataAccess();
        Currency c = cda.getCurrency(currencyId);
        final ResponseBuilder response;
        if (c != null) {
            response = Response.ok().entity(c);
            return response.build();
        } else {
            // want a better way to handle different error responses
            String errorString = "The request could not be understood by the server due to malformed syntax.";
            response = Response.status(404).entity(errorString);
            return response.build();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // , @Context UriInfo uriInfo this would be needed to use the
    // getAbsolutePathBuilder option
    public Response addCurrency(Currency currency) throws SQLException, IOException, ClassNotFoundException {
        final ResponseBuilder response;
        CurrencyDataAccess cda = new CurrencyDataAccess();
        int currencyId = cda.addCurrency(currency);
        // URI createdUri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(currencyId)).build();
        // response includes address created here as a header Location
        URI createdUri = UriBuilder.fromResource(CurrencyController.class).path("/{id}").build(Integer.toString(currencyId));
        response = Response.created(createdUri).entity(currencyId);
        return response.build();
       
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCurrency(@PathParam("id") int currencyId, Currency currency) throws SQLException, IOException, ClassNotFoundException {
        final ResponseBuilder response;
        CurrencyDataAccess cda = new CurrencyDataAccess();
        cda.updateCurrency(currencyId, currency);
        response = Response.ok();
        return response.build();

        }
    

    // @DELETE
    // @Path("id")
}