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

import com.countries.countriesAPI.models.Country;
import com.countries.countriesAPI.models.Language;

public class LanguageDBDataTransfer {
    private Connection connection;

    public LanguageDBDataTransfer(){

    }
 
    DbConnection dbc = new DbConnection();
    
    public void populateLanguageTable(List<Country> countryList) throws SQLException {
        InputStream is = getClass().getResourceAsStream("sqlScripts/populateLanguageTable.sql");
        Scanner sc = new Scanner(is);
        try {
            connection = dbc.getConnection();
            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()){

                sb.append(sc.nextLine());
            
            }
            PreparedStatement ps = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);

            for(Country c: countryList){
                for (Language l: c.getLanguages()){
                    ps.setString(1, l.getIso639_1());
                    ps.setString(2, l.getIso639_2());
                    ps.setString(3, l.getName());
                    ps.setString(4, l.getNativeName());
                    ps.setString(5, l.getName());
                    ps.execute();
                    if(ps.getUpdateCount() > 0){
                        ResultSet rs = ps.getGeneratedKeys();
                        rs.next();
                        l.setId(rs.getInt("id"));
                        }
                    }
                }
            
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            dbc.closeConnection(connection);
            sc.close();
        }
    }

    public ArrayList<Language> getLanguageList() throws SQLException {
        ArrayList<Language> languageList = new ArrayList<>();
        try{
            connection = dbc.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM language");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Language lan = new Language();
                lan.setId(rs.getInt("id"));
                lan.setIso639_1(rs.getString("iso639_1"));
                lan.setIso639_2(rs.getString("iso639_2"));
                lan.setName(rs.getString("name"));
                lan.setNativeName(rs.getString("nativeName"));
                languageList.add(lan);
            }
        
        }finally {
            dbc.closeConnection(connection);
        }
        return languageList;
    }
// need to tidy this up to get rid of currency and replace with language
    public void populateCountryLanguageTable(List<Country> countryList, List<Language> LanguageList)
            throws SQLException {
        InputStream is = getClass().getResourceAsStream("sqlScripts/populateCountryLanguageTable.sql");
        Scanner sc = new Scanner(is);
        try {
            connection = dbc.getConnection();
            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()){

                sb.append(sc.nextLine());
            
            }
            PreparedStatement ps = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);
            
            for(Country c: countryList){
                for(Language lan: c.getLanguages()){
                    for(Language l: LanguageList){
                        if(lan.getName()!= null && l.getName() != null && l.getName().equalsIgnoreCase(lan.getName())){
                            ps.setInt(1, c.getId());
                            ps.setInt(2, l.getId());
                            ps.execute();
                        }
                    }
                }
            }
            
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            dbc.closeConnection(connection);
            sc.close();
        }     
    }
}