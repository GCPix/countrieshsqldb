package com.countries.countriesAPI.dataAccess;

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

    public LanguageDataAccess(){

    }
    public Language getLanguage(int languageId) throws SQLException {
        Language language = null;
        DbConnection dc = new DbConnection();
        dc.loadDriver();
        Connection con = dc.getConnection();

        try {

            String sqlString = "SELECT * FROM language WHERE id = " + languageId;
            PreparedStatement ps = con.prepareStatement(sqlString);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                language = new Language();
                language.setId(rs.getInt("id"));
                language.setIso639_1(rs.getString("iso639_1"));
                language.setIso639_2(rs.getString("iso639_2"));
                language.setName(rs.getString("name"));
                language.setNativeName(rs.getString("nativeName"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dc.closeConnection(con);
        }
        return language;

    }
    public void deleteLanguage(int id) throws SQLException {
        DbConnection dc = new DbConnection();
        dc.loadDriver();
        Connection  con = dc.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM language where id  = " + id);
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dc.closeConnection(con);
        }

    }
    public void updateLanguage(int id, Language language) throws SQLException {
        DbConnection dc = new DbConnection();
        dc.loadDriver();
        Connection  con = dc.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement("UPDATE language SET iso639_1 = '" + language.getIso639_1() + "', iso639_2 = '" + language.getIso639_2()
            + "', name = '" + language.getName() + "', nativeName = '" + language.getNativeName() + "' WHERE id = " + id );
            ps.execute();
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dc.closeConnection(con);
        }

    }
    public int addLanguage(Language language) throws SQLException {
        DbConnection dc = new DbConnection();
        dc.loadDriver();
        Connection  con = dc.getConnection();
        InputStream is = getClass().getResourceAsStream("../../../db/sqlScripts/populateLanguageTable.sql");
        Scanner sc = new Scanner(is);
        
        
        try {
            

            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()){
                sb.append(sc.nextLine());
            }

            PreparedStatement ps = con.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, language.getIso639_1());
            ps.setString(2, language.getIso639_2());
            ps.setString(3, language.getName());
            ps.setString(4, language.getNativeName());
            ps.setString(5, language.getName());
            ps.execute();
            
            String sqlString = "SELECT * FROM language WHERE name LIKE '" + language.getName() + "'";
            PreparedStatement getpk = con.prepareStatement(sqlString);
            ResultSet rs = getpk.executeQuery();
            rs.next();
            language.setId(rs.getInt("id"));
            



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dc.closeConnection(con);
            sc.close();
            
        }
        return language.getId();
    }

    public ArrayList<Language> getLanguageList() throws SQLException {
        ArrayList<Language> languageList = null;
        DbConnection dc  = new DbConnection();
        dc.loadDriver();
        Connection connection = dc.getConnection();
        Language l;
        try {

            PreparedStatement ps = connection.prepareStatement("Select * from language");
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                languageList = new ArrayList<Language>();
                l = new Language();
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