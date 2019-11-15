package com.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import com.countries.countriesAPI.Country;

public class BorderDbDataTransfer {
    private Connection connection;

    public BorderDbDataTransfer() {

    }

    public void populateBorderTable(List<Country> countryList) throws SQLException {
            DbConnection dbc = new DbConnection();
        try{
            connection = dbc.getConnection();
            InputStream is = getClass().getResourceAsStream("populateBorderTable.sql");
            
            Scanner sc = new Scanner(is);
            
            StringBuffer sb = new StringBuffer();
            
            while(sc.hasNext()){
                sb.append(sc.nextLine());
            }
            
            PreparedStatement ps = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);
            
            for(Country c: countryList){
                for(String b: c.getBorders()){
                    int cid = 0;
                    for(Country bc: countryList){
                        if (bc.getAlpha3Code().equalsIgnoreCase(b)){
                            cid = bc.getId();
                        }
                    }
                        ps.setInt(1, c.getId());
                        ps.setInt(2, cid);
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