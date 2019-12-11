package com.countries.countriesAPI.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.countries.countriesAPI.dataAccess.CurrencyDataAccess;
import com.countries.countriesAPI.models.Currency;

@Path("currencies")
public class CurrenciesController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrencies() {
        CurrencyDataAccess cda = new CurrencyDataAccess();
        List<Currency> currencies = null;
        Response response;
        try {
            currencies = cda.getCurrencies();
        } catch (ClassNotFoundException | SQLException | IOException e) {
           
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        }
        
        if(currencies != null){
            response = Response.ok(currencies).build();
        } else {
            response = Response.status(Status.NOT_FOUND).entity("No data was returned for your request").build();
        }
        
        return response;
    }
}