package com.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.countries.countriesAPI.Country;

public class CountryDbDataTransfer {

    private Connection connection;

    public CountryDbDataTransfer() {

    }

    public void populateCountryTable(List<Country> countryList) throws SQLException {
            DbConnection dbc = new DbConnection();
        try{
            connection = dbc.getConnection();
            //inputstream not working in preparedstatement need to find a way to handle that if poss?
            // InputStream is = getClass().getResourceAsStream("populateCountryTable.sql");
                
            // SqlFile populateCountries = new SqlFile(new InputStreamReader(is), "init", System.out, "UTF-8", false, new File("."));
            PreparedStatement ps = connection.prepareStatement("INSERT INTO country (name, capital, population) VALUES (?,?,?);");
            // populateCountries.setConnection(connection);
            

            for(Country c: countryList){
                ps.setString(1, c.getName());
                ps.setString(2, c.getCapital());
                ps.setLong(3, c.getPopulation());
                ps.execute();
            }
            
            // populateCountries.execute();
    
            } catch (Exception exception) {
    
            }finally {
                dbc.closeConnection(connection);
            }

    }

    
    
}