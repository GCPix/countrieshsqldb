package com.countries.countriesAPI.dataAccess;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.countries.countriesAPI.models.Language;
import com.db.DbConnection;

public class LanguageDataAccess {

    public LanguageDataAccess(){

    }
    
    public ArrayList<Language> getLanguageList() throws SQLException {
        ArrayList<Language> languageList = new ArrayList<Language>();
        DbConnection dc  = new DbConnection();
        dc.loadDriver();
        Connection connection = dc.getConnection();
        try {

            PreparedStatement ps = connection.prepareStatement("Select * from language");
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Language l = new Language();
                l.setId(rs.getInt("id"));
                l.setIso639_1(rs.getString("iso639_1"));
                l.setIso639_2(rs.getString("iso639_2"));
                l.setName(rs.getString("name"));
                l.setNativeName(rs.getString("nativeName"));
                languageList.add(l);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dc.closeConnection(connection);
        }
       
        
        return languageList;
    }
}