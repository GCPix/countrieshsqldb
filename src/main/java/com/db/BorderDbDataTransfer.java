package com.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import com.countries.countriesAPI.models.Country;

public class BorderDbDataTransfer {
    // private Connection connection;

    public BorderDbDataTransfer() {

    }

    public void populateBorderTable(List<Country> countryList, Connection connection) throws SQLException {
            // DbConnection dbc = new DbConnection();
            InputStream is = getClass().getResourceAsStream("sqlScripts/populateBorderTable.sql");
            Scanner sc = new Scanner(is);

        try{
            // connection = dbc.getConnection();
            
            StringBuffer sb = new StringBuffer();
            
            while(sc.hasNext()){
                sb.append(sc.nextLine());
            }

            PreparedStatement ps = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);
            
            for(Country c: countryList){
                for(String b: c.getborders()){
                    int cid = 0;
                    for(Country bc: countryList){
                        if (bc.getAlpha3Code().equalsIgnoreCase(b)){
                            cid = bc.getId();
                        }
                    }
                        ps.setInt(1, c.getId());
                        ps.setInt(2, cid);
                        ps.setInt(3, c.getId());
                        ps.setInt(4, cid);
                        ps.execute();   
                }      
            }
    
            } catch (Exception exception) {
                exception.printStackTrace();
    
            }finally {
                // dbc.closeConnection(connection);
                sc.close();
            }

    }
}