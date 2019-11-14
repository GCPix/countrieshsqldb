package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.countries.countriesAPI.Country;

public class BorderDbDataTransfer {
    private Connection connection;

    public BorderDbDataTransfer() {

    }

    public void populateBorderTable(List<Country> countryList) throws SQLException {
            DbConnection dbc = new DbConnection();
        try{
            connection = dbc.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO border (name) SELECT * FROM (VALUES (?)) WHERE NOT EXISTS (SELECT * FROM border  WHERE name =?);");
            
            for(Country c: countryList){
                for(String b: c.getBorders()){
                    ps.setString(1, b);
                    ps.setString(2, b);
                    // ps.setString(3, b);
                    ps.execute();
                }      
            }
    
            } catch (Exception exception) {
    
            }finally {
                dbc.closeConnection(connection);
            }

    }
}