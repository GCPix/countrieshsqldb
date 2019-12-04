package com.countries.countriesAPI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.countries.countriesAPI.dataAccess.CountryDataAccess;
import com.countries.countriesAPI.models.Country;
import com.db.DbInitialPopulate;

import org.hsqldb.cmdline.SqlToolError;

public class Runner {
    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, SqlToolError {
        DbInitialPopulate dip = new DbInitialPopulate();
        dip.populateDatabase();
        
        // List<Integer> deletedCurrencies = new ArrayList<>();
        // deletedCurrencies.add(0);
               
        // CountryDataAccess cda = new CountryDataAccess();
        
        // cda.updateCountry(cda.getCountry(0), deletedCurrencies);
    }
}