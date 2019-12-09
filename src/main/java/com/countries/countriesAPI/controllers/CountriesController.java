package com.countries.countriesAPI.controllers;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.countries.countriesAPI.models.Filter;
import com.countries.countriesAPI.models.ResponsePaged;
import com.countries.countriesAPI.dataAccess.CountryDataAccess;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("countries")
public class CountriesController {

    @Path("/summary")
    // @GET
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountriesSummary(@DefaultValue("name") @QueryParam("sortField") String sortField,
            @DefaultValue("30") @QueryParam("pageSize") int pageSize,
            @DefaultValue("0") @QueryParam("startRecord") int startRecord,  Filter filter) throws ClassNotFoundException, SQLException,
            JsonMappingException, JsonProcessingException, UnsupportedEncodingException, MalformedURLException {
   
        ResponsePaged countrySummary;
        CountryDataAccess cda = new CountryDataAccess();
        countrySummary = cda.getCountriesSummary(sortField, pageSize, startRecord, filter);

        if(countrySummary!= null) {
            return Response.ok(countrySummary).build();
        } else {
            return Response.status(404).entity("no data returned").build();
        }
    } 
}