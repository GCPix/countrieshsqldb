package com.countries.countriesAPI.dataAccess;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.countries.countriesAPI.models.Language;
import com.db.DbConnection;

public class LanguageDataAccess {

    public LanguageDataAccess() {

    }

    public Language getLanguage(int languageId) throws SQLException, IOException, ClassNotFoundException {
        Language language = null;
        DbConnection dc = new DbConnection();

        Connection con = dc.getConnection();

        try {

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
        } finally {
            dc.closeConnection(con);
        }
        return language;

    }

    public void deleteLanguage(int id) throws SQLException, ClassNotFoundException {
        DbConnection dc = new DbConnection();

        Connection con = dc.getConnection();

        try {
            String sqlString = "DELETE FROM language where id  = " + id;
            try (PreparedStatement ps = con.prepareStatement(sqlString)) {
                ps.execute();
            } 
        } finally {
            dc.closeConnection(con);
        }

    }

    public void updateLanguage(int id, Language language) throws SQLException, ClassNotFoundException {
        DbConnection dc = new DbConnection();
        Connection con = dc.getConnection();

        try {
            String sqlString = "UPDATE language SET iso639_1 = '" + language.getIso639_1() + "', iso639_2 = '"
            + language.getIso639_2() + "', name = '" + language.getName() + "', nativeName = '"
            + language.getNativeName() + "' WHERE id = " + id + ";";

            try (PreparedStatement ps = con.prepareStatement(sqlString)) {
                if (ps.executeUpdate() != 1)
                    throw new IllegalArgumentException(id + " is not in the database");
            } 
 
        } finally {
            dc.closeConnection(con);
        }
    }

    public int addLanguage(Language language) throws SQLException, IOException, ClassNotFoundException {
        DbConnection dc = new DbConnection();
        Connection  con = dc.getConnection();
        InputStream is = getClass().getResourceAsStream("../../../db/sqlScripts/populateLanguageTable.sql");
        Scanner sc = new Scanner(is);

        
        try {
            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()){
                sb.append(sc.nextLine());
            }
            try (PreparedStatement ps = con.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);) {
                ps.setString(1, language.getIso639_1());
                ps.setString(2, language.getIso639_2());
                ps.setString(3, language.getName());
                ps.setString(4, language.getNativeName());
                ps.setString(5, language.getName());
                ps.execute();
            } 

            String sqlString = "SELECT * FROM language WHERE name LIKE '" + language.getName() + "'";
            try (PreparedStatement getpk = con.prepareStatement(sqlString)) {
                try (ResultSet rs = getpk.executeQuery()) {
                    rs.next();
                    language.setId(rs.getInt("id"));
                }
            } 
 
        }  finally {
            dc.closeConnection(con);
            sc.close();
            is.close();
        }
        return language.getId();
    }
    
    public ArrayList<Language> getLanguageList() throws SQLException, ClassNotFoundException {
        ArrayList<Language> languageList = null;
        DbConnection dc  = new DbConnection();
        Connection connection = dc.getConnection();
        Language l = null;

        try {
            String sqlString = "Select * from language";
            try (PreparedStatement ps = connection.prepareStatement(sqlString)) {
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

        } finally {
            dc.closeConnection(connection);
        }
        return languageList;
    }
}