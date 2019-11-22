package com.countries.countriesAPI.controllers;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.countries.countriesAPI.dataAccess.CountryDataAccess;
import com.countries.countriesAPI.models.Country;
import com.google.gson.Gson;

@Path("country")
public class CountryController {
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCountry(@PathParam("id") int countryId) throws SQLException {

        CountryDataAccess cda = new CountryDataAccess();
        Country c = cda.getCountry(countryId);
        Gson gson = new Gson();
        String countryString = gson.toJson(c);
        
        return countryString;

    }
}