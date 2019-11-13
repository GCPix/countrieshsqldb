package com.countries.countriesAPI;

import static com.db.DbConnection.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.countries.Helpers.HCHandler;
import com.db.CountryDbDataTransfer;
import com.db.DbConnection;

import org.hsqldb.cmdline.SqlToolError;

public class Runner {
    public static void main(String[] args) throws SqlToolError, SQLException {
        DbConnection dbc = new DbConnection();
        dbc.createDatabase();
        HCHandler hch = new HCHandler();
        String jsonString = hch.getAPIData("https://restcountries.eu/rest/v2/all");
        List<Country> countryList = new ArrayList<Country>();
        countryList = hch.jsonToCountry(jsonString);
        System.out.println(countryList.get(0).getName());
        Connection conn = dbc.getConnection();
        CountryDbDataTransfer cddt = new CountryDbDataTransfer(conn);
        cddt.populateCountryTable(countryList);
        dbc.closeConnection(conn);
    }
}