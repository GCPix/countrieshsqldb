package com.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.countries.Helpers.HCHandler;
import com.countries.countriesAPI.Country;

import org.hsqldb.cmdline.SqlToolError;

public class DbDataTransfer {

    public DbDataTransfer() {

    }

    public void populateDatabase() throws SqlToolError, SQLException {
        DbConnection dbc = new DbConnection();
        dbc.createDatabase();
        HCHandler hch = new HCHandler();
        String jsonString = hch.getAPIData("https://restcountries.eu/rest/v2/all");
        List<Country> countryList = new ArrayList<Country>();
        countryList = hch.jsonToCountry(jsonString);
        CountryDbDataTransfer cddt = new CountryDbDataTransfer();
        cddt.populateCountryTable(countryList);
        BorderDbDataTransfer bddt = new BorderDbDataTransfer();
        bddt.populateBorderTable(countryList);
        CurrencyDbdataTransfer curddt = new CurrencyDbdataTransfer();
        curddt.populateCurrencyTable(countryList);
        curddt.populateCountryCurrencyTable(countryList, curddt.getCurrencyList());
        LanguageDBDataTransfer lddt = new LanguageDBDataTransfer();
        lddt.populateLanguageTable(countryList);
        lddt.populateCountryLanguageTable(countryList, lddt.getLanguageList());
    }
}
