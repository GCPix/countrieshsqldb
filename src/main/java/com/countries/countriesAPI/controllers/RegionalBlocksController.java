package com.countries.countriesAPI.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.countries.countriesAPI.dataAccess.RegionalBlockDataAccess;
import com.countries.countriesAPI.models.RegionalBlock;

@Path("regionalblocks")
public class RegionalBlocksController {
    @GET
    @Produces
    public Response getRegionalBlocks() throws ClassNotFoundException, SQLException {
        Response response;
        List<RegionalBlock> regionalBlockList = null;
        RegionalBlockDataAccess rbda = new RegionalBlockDataAccess();
        regionalBlockList = rbda.getRegionalBlocks();
        if (regionalBlockList == null) response = Response.noContent().entity("list was empty").build();

        response = Response.ok(regionalBlockList).build();
        return response;
    }
}