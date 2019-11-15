package com.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import com.countries.countriesAPI.Country;

public class CountryDbDataTransfer {

    private Connection connection;

    public CountryDbDataTransfer() {

    }

    public void populateCountryTable(List<Country> countryList) throws SQLException {
            DbConnection dbc = new DbConnection();
        try{
            connection = dbc.getConnection();
       
            InputStream is = getClass().getResourceAsStream("populateCountryTable.sql");
            
            Scanner sc = new Scanner(is);
            
            StringBuffer sb = new StringBuffer();
            
            while(sc.hasNext()){
                sb.append(sc.nextLine());
            }
            
            PreparedStatement ps = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);
            
            

            for(Country c: countryList){
                ps.setString(1, c.getName());
                ps.setString(2, c.getCapital());
                ps.setLong(3, c.getPopulation());
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int returnedId = rs.getInt("id");
                c.setId(returnedId);
            }
            
            
    
            } catch (Exception exception) {
                exception.printStackTrace();
            }finally {
                dbc.closeConnection(connection);
            }

    }

    
    
}