package com.countries.countriesAPI.controllers;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.countries.countriesAPI.dataAccess.CountryDataAccess;
import com.countries.countriesAPI.models.Country;

@Path("country")
public class CountryController {
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountry(@PathParam("id") int countryId) throws SQLException {

        CountryDataAccess cda = new CountryDataAccess();
        Country c = null;
        c = cda.getCountry(countryId);
        if (c == null) {
            return Response.status(404).entity("no country returned for that id").build();
        }
        return Response.ok(c).build();

    }
}