package com.countries.countriesAPI;

import java.io.IOException;
import java.sql.SQLException;

import com.countries.countriesAPI.dataAccess.CountryDataAccess;
import com.countries.countriesAPI.models.Country;
import com.db.DbInitialPopulate;

import org.hsqldb.cmdline.SqlToolError;

public class Runner {
    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, SqlToolError {
        DbInitialPopulate dip = new DbInitialPopulate();
        dip.populateDatabase();
  
    }
}