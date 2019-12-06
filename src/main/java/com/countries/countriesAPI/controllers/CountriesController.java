package com.countries.countriesAPI.controllers;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.countries.Helpers.Filter;
import com.countries.Helpers.ResponsePaged;
import com.countries.countriesAPI.dataAccess.CountryDataAccess;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("countries")
public class CountriesController {

    @Path("/summary")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountriesSummary(@DefaultValue("name") @QueryParam("sortField") String sortField,
            @DefaultValue("30") @QueryParam("pageSize") int pageSize,
            @DefaultValue("0") @QueryParam("startRecord") int startRecord,
            @DefaultValue("{}") @QueryParam("filter") String filterString) throws ClassNotFoundException, SQLException,
            JsonMappingException, JsonProcessingException, UnsupportedEncodingException {

        Filter filter = new ObjectMapper().readValue(filterString, Filter.class);
        ResponsePaged countrySummary;
        CountryDataAccess cda = new CountryDataAccess();
        countrySummary = cda.getCountriesSummary(sortField, pageSize,startRecord, filter);

        if(countrySummary!= null) {
            return Response.ok(countrySummary).build();
        } else {
            return Response.status(404).entity("no data returned").build();
        }
    }
 
}