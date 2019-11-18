package com.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.countries.countriesAPI.Country;

public class CountryDbDataTransfer {

    private Connection connection;

    public CountryDbDataTransfer() {

    }

    public List<Country> getCountryList() throws SQLException {
        DbConnection dbc = new DbConnection();
        ArrayList<Country> countryList = new ArrayList<>();
        
        try{
            connection = dbc.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM country");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Country country = new Country();
                country.setId(rs.getInt("id"));
                country.setName(rs.getString("name"));
                country.setCapital(rs.getString("capital"));
                country.setPopulation((long)rs.getInt("population"));
                countryList.add(country);
            }
        
        }finally {
            dbc.closeConnection(connection);
        }
        return countryList;
    }

    public void populateCountryTable(List<Country> countryList) throws SQLException {
            DbConnection dbc = new DbConnection();
            InputStream is = getClass().getResourceAsStream("sqlScripts/populateCountryTable.sql");
            
            Scanner sc = new Scanner(is);
        try{
            connection = dbc.getConnection();

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
                sc.close();
            }

    }

    
    
}