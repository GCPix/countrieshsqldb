package com.countries.countriesAPI.dataAccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.countries.Helpers.SqlScriptParser;
import com.countries.countriesAPI.models.Language;
import com.db.DbConnection;

public class LanguageDataAccess {

    public LanguageDataAccess() {

    }

    public Language getLanguage(int languageId) throws SQLException, IOException, ClassNotFoundException {
        Language language = null;
        DbConnection dc = new DbConnection();

        

        try (Connection con = dc.getConnection();){

            String sqlString = "SELECT * FROM language WHERE id = " + languageId;
            try (PreparedStatement ps = con.prepareStatement(sqlString);) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        language = new Language();
                        language.setId(rs.getInt("id"));
                        language.setIso639_1(rs.getString("iso639_1"));
                        language.setIso639_2(rs.getString("iso639_2"));
                        language.setName(rs.getString("name"));
                        language.setNativeName(rs.getString("nativeName"));
                    }
                } 
            } 
        } 
        return language;

    }

    public int deleteLanguage(int id) throws SQLException, ClassNotFoundException {
        int noRowsReturned;
        DbConnection dc = new DbConnection();


        try (Connection con = dc.getConnection();){
            String sqlString = "DELETE FROM language where id  = " + id;
            try (PreparedStatement ps = con.prepareStatement(sqlString)) {
                noRowsReturned = ps.executeUpdate();
            } 
        } 
        return noRowsReturned;
    }

    public int updateLanguage(int id, Language language) throws SQLException, ClassNotFoundException {
        int noRowsReturned;
        DbConnection dc = new DbConnection();
        

        try (Connection con = dc.getConnection();){
            String sqlString = "UPDATE language SET iso639_1 = '" + language.getIso639_1() + "', iso639_2 = '"
            + language.getIso639_2() + "', name = '" + language.getName() + "', nativeName = '"
            + language.getNativeName() + "' WHERE id = " + id + ";";

            try (PreparedStatement ps = con.prepareStatement(sqlString)) {

                noRowsReturned = ps.executeUpdate();
            } 
        
        }
        return noRowsReturned;
    }

    public int addLanguage(Language language) throws SQLException, IOException, ClassNotFoundException {
    
        DbConnection dc = new DbConnection();
        String sqlScript = "../../db/sqlScripts/populateLanguageTable.sql";
        try(Connection  con = dc.getConnection();){
        	SqlScriptParser ssp = new SqlScriptParser();
        	String sqlString = ssp.getSqlString(sqlScript);
            try (PreparedStatement ps = con.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);) {
                ps.setString(1, language.getIso639_1());
                ps.setString(2, language.getIso639_2());
                ps.setString(3, language.getName());
                ps.setString(4, language.getNativeName());
                ps.setString(5, language.getName());
                ps.execute();
            } 

            String sqlStringId = "SELECT * FROM language WHERE name LIKE '" + language.getName() + "'";
            try (PreparedStatement getpk = con.prepareStatement(sqlStringId)) {
                try (ResultSet rs = getpk.executeQuery()) {
                    rs.next();
                    language.setId(rs.getInt("id"));
                }
            } 
 
        }  
       
        return language.getId();
    }
    
    public ArrayList<Language> getLanguageList() throws SQLException, ClassNotFoundException {
        ArrayList<Language> languageList = null;
        DbConnection dc  = new DbConnection();
       
        Language l = null;

        try (Connection con = dc.getConnection();){
            String sqlString = "Select * from language";
            try (PreparedStatement ps = con.prepareStatement(sqlString)) {
                try (ResultSet rs = ps.executeQuery()) {
                    languageList = new ArrayList<Language>();
                    while(rs.next()){

                        l = new Language();
                        l.setId(rs.getInt("id"));
                        l.setIso639_1(rs.getString("iso639_1"));
                        l.setIso639_2(rs.getString("iso639_2"));
                        l.setName(rs.getString("name"));
                        l.setNativeName(rs.getString("nativeName"));
                        languageList.add(l);
                    }
                } 
            } 

        } 
        return languageList;
    }
}