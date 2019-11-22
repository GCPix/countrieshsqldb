package com.countries.countriesAPI.controllers;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.countries.countriesAPI.dataAccess.CurrencyDataAccess;
import com.countries.countriesAPI.models.Currency;
import com.google.gson.Gson;

@Path("currency")
public class CurrencyController {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCurrency(@PathParam("id") int currencyId) throws SQLException {
        CurrencyDataAccess cda = new CurrencyDataAccess();
        Currency c = cda.getCurrency(currencyId);
        Gson gson = new Gson();
        String jsonString = gson.toJson(c);
        return jsonString;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addCurrency(Currency currency) throws SQLException {
        CurrencyDataAccess cda = new CurrencyDataAccess();
        int currencyId = cda.addCurrency(currency);
        Gson gson = new Gson();
        String jsonString = gson.toJson(currencyId);
        return jsonString;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateCurrency(@PathParam("id") int currencyId, Currency currency) throws SQLException {
        CurrencyDataAccess cda = new CurrencyDataAccess();
        cda.updateCurrency(currencyId, currency);

        }
    

    // @DELETE
    // @Path("id")
}