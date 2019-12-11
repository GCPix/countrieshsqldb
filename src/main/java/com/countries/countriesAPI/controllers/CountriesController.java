package com.countries.countriesAPI.controllers;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.countries.countriesAPI.dataAccess.CountryDataAccess;
import com.countries.countriesAPI.models.Filter;
import com.countries.countriesAPI.models.ResponsePaged;
import com.fasterxml.jackson.core.JsonProcessingException;

@Path("countries")
public class CountriesController {

    @Path("/summary")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountriesSummary(@DefaultValue("name") @QueryParam("sortField") String sortField,
            @DefaultValue("30") @QueryParam("pageSize") int pageSize,
            @DefaultValue("1") @QueryParam("pageNumber") int pageNumber,  Filter filter){
   
        ResponsePaged countrySummary = new ResponsePaged();
        CountryDataAccess cda = new CountryDataAccess();
        Response response;
        try {
            // As they can't do anything to sort it I think it should be internal server error, wondering if I should just have one catch?
   
            countrySummary = cda.getCountriesSummary(sortField, pageSize, pageNumber, filter);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        } catch (SQLException e) {
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        }

        if(countrySummary!= null) {
            response = Response.ok(countrySummary).build();
        } else {
            response = Response.status(404).entity("no data returned").build();
        }
        return response;
    }
    
}