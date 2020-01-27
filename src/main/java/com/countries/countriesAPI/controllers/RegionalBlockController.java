package com.countries.countriesAPI.controllers;

import java.sql.SQLException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.countries.countriesAPI.dataAccess.RegionalBlockDataAccess;
import com.countries.countriesAPI.models.RegionalBlock;

@Path("regionalblock")
public class RegionalBlockController {
    @POST
    public Response addRegionalBlock(RegionalBlock regionalBlock) {
        Response response;
        RegionalBlockDataAccess rbda = new RegionalBlockDataAccess();
        int id = -1;
        try {
            id = rbda.addRegionalBlock(regionalBlock);
        } catch (ClassNotFoundException | SQLException e) {
            
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        }
        if (id >= 0) {
            response = Response.ok(id).build();
        } else {
            response = Response.status(Status.BAD_REQUEST)
                    .entity("sorry but something went wrong.  Did you enter the correct data?").build();
        }
        return response;
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRegionalBlock(@PathParam("id") int id) {
        Response response;
        RegionalBlockDataAccess rbda = new RegionalBlockDataAccess();
        RegionalBlock rb = null;
        try {
            rb = rbda.getRegionalBlock(id);
        } catch (ClassNotFoundException | SQLException e) {
            
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        }
        if (rb == null) {
        	response = Response.status(Status.NOT_FOUND).entity("There was no data to return for that id").build();
        }else {
        	response = Response.ok(rb).build();
        }
        
        return response;
    }

    @Path("{id}")
    @DELETE
    public Response deleteRegionalBlock(@PathParam("id") int id) {
        Response response;
        RegionalBlockDataAccess rbda = new RegionalBlockDataAccess();
        int noRowsReturned = 0;
        try {
            noRowsReturned = rbda.deleteRegionalBlock(id);
        } catch (ClassNotFoundException | SQLException e) {
           
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        }
        if (noRowsReturned == 0) {
            response = Response.status(Status.BAD_REQUEST).entity("Couldn't find that id").build();
        } else {
            response = Response.ok().build();
        }

        return response;
    }

    @Path("{id}")
    @PUT
    public Response updateRegionalBlock(@PathParam("id") int id, RegionalBlock regionalBlock) {
        Response response;
        RegionalBlockDataAccess rbda = new RegionalBlockDataAccess();
        int noRowsReturned = 0;
        try {
            noRowsReturned = rbda.updateRegionalBlock(id, regionalBlock);
        } catch (ClassNotFoundException | SQLException e) {
           
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        }
        if(noRowsReturned==0) {
            response = Response.status(Status.BAD_REQUEST).entity("Couldn't find that id").build();
        } else {
            response = Response.ok().build();
        }
        
        return response;
    }
}