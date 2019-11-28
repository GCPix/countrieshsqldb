package com.countries.countriesAPI.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.countries.countriesAPI.dataAccess.CountryDataAccess;
import com.countries.countriesAPI.models.BasicCountry;

@Path("countries")
public class CountriesController {

    @Path("/summary")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountriesSummary(@DefaultValue("name") @QueryParam("sortField") String sortField,
            @DefaultValue("") @QueryParam("filterField") String filterField,
            @DefaultValue("") @QueryParam("filterValue") String filterValue)
            throws ClassNotFoundException, SQLException {

        List<BasicCountry> countrySummary = new ArrayList<BasicCountry>();
        CountryDataAccess cda = new CountryDataAccess();
        countrySummary = cda.getCountriesSummary(sortField, filterField, filterValue);

        if(countrySummary.size()>0) {
            return Response.ok(countrySummary).build();
        } else {
            return Response.status(404).entity("no data returned").build();
        }
    }
 
}