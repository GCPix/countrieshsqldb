package com.countries.countriesAPI.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.countries.countriesAPI.dataAccess.CurrencyDataAccess;
import com.countries.countriesAPI.models.Currency;
import com.google.gson.Gson;

@Path("currencies")
public class CurrenciesController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrencies() throws SQLException {
        List<Currency> currencies = CurrencyDataAccess.getCurrencies();
        final ResponseBuilder response;
        response = Response.ok().entity(currencies);
        return response.build();
    }
}