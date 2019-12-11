package com.countries.countriesAPI.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.countries.countriesAPI.dataAccess.RegionalBlockDataAccess;
import com.countries.countriesAPI.models.RegionalBlock;

@Path("regionalblocks")
public class RegionalBlocksController {
    @GET
    @Produces
    public Response getRegionalBlocks() {
        Response response;
        List<RegionalBlock> regionalBlockList = null;
        RegionalBlockDataAccess rbda = new RegionalBlockDataAccess();
        try {
            regionalBlockList = rbda.getRegionalBlocks();
        } catch (ClassNotFoundException | SQLException e) {
            
            e.printStackTrace();
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong, contact someone who can sort it").build();
        }
        if (regionalBlockList == null) response = Response.noContent().entity("list was empty").build();

        response = Response.ok(regionalBlockList).build();
        return response;
    }
}