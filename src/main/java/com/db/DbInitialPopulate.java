package com.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.countries.Helpers.HCHandler;
import com.countries.countriesAPI.models.Country;

import org.hsqldb.cmdline.SqlToolError;

public class DbInitialPopulate {

    public DbInitialPopulate() {

    }

    public void populateDatabase() throws IOException, ClassNotFoundException, SQLException, SqlToolError {
        DbConnection dbc = new DbConnection();
        HCHandler hch = new HCHandler();
        String jsonString = hch.getAPIData("https://restcountries.eu/rest/v2/all");
        List<Country> countryList = new ArrayList<Country>();
        countryList = hch.jsonToCountry(jsonString);
        
        try (Connection connection = dbc.getConnection()) {
            dbc.createTables(connection);
            CountryDbDataTransfer cddt = new CountryDbDataTransfer();
            cddt.populateCountryTable(countryList, connection);
            BorderDbDataTransfer bddt = new BorderDbDataTransfer();
            bddt.populateBorderTable(countryList, connection);
            CurrencyDbdataTransfer curddt = new CurrencyDbdataTransfer();
            curddt.populateCurrencyTable(countryList, connection);
            curddt.populateCountryCurrencyTable(countryList, curddt.getCurrencyList(connection), connection);
            LanguageDBDataTransfer lddt = new LanguageDBDataTransfer();
            lddt.populateLanguageTable(countryList, connection);
            lddt.populateCountryLanguageTable(countryList, lddt.getLanguageList(connection), connection);
            RegionalBlockDBDataTransfer rbddt = new RegionalBlockDBDataTransfer();
            rbddt.populateRegionalBlockTable(countryList, connection);
            rbddt.populateCountryRBRelationshipTable(countryList, rbddt.getRegionalBlockList(connection), connection);
        } 
       
        
        
        
    }
}
