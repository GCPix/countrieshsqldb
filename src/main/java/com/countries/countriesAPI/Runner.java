package com.countries.countriesAPI;

import java.sql.SQLException;

import com.countries.countriesAPI.dataAccess.CountryDataAccess;
import com.db.DbInitialPopulate;

import org.hsqldb.cmdline.SqlToolError;

public class Runner {
    public static void main(String[] args) throws SqlToolError, SQLException {
        DbInitialPopulate dip = new DbInitialPopulate();
        dip.populateDatabase();
        // CountryDataAccess cda = new CountryDataAccess();
        // cda.getCountry(0);

    }
}