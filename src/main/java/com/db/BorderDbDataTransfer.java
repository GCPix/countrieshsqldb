package com.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.countries.Helpers.SqlScriptParser;
import com.countries.countriesAPI.models.Country;

public class BorderDbDataTransfer {

    public BorderDbDataTransfer() {

    }

    public void populateBorderTable(List<Country> countryList, Connection connection) throws SQLException, IOException {
    		String sqlScript = "../../db/sqlScripts/populateBorderTable.sql";
    		SqlScriptParser ssp = new SqlScriptParser();
            String sqlString = ssp.getSqlString(sqlScript);
            PreparedStatement ps = connection.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
            
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
    
            } 
}