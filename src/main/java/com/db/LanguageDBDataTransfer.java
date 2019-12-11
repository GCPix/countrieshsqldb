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
   

    public LanguageDBDataTransfer(){

    }
 
   
    
    public void populateLanguageTable(List<Country> countryList, Connection connection) throws SQLException {
        InputStream is = getClass().getResourceAsStream("sqlScripts/populateLanguageTable.sql");
        Scanner sc = new Scanner(is);
        try {
           
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
            
        } finally {
           
            sc.close();
        }
    }

    public ArrayList<Language> getLanguageList(Connection connection) throws SQLException {
        ArrayList<Language> languageList = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM language");){
        
           try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    Language lan = new Language();
                    lan.setId(rs.getInt("id"));
                    lan.setIso639_1(rs.getString("iso639_1"));
                    lan.setIso639_2(rs.getString("iso639_2"));
                    lan.setName(rs.getString("name"));
                    lan.setNativeName(rs.getString("nativeName"));
                    languageList.add(lan);
                }
            }
        
        }

        return languageList;
    }

    public void populateCountryLanguageTable(List<Country> countryList, List<Language> LanguageList, Connection connection)
            throws SQLException {
        InputStream is = getClass().getResourceAsStream("sqlScripts/populateCountryLanguageTable.sql");
        Scanner sc = new Scanner(is);
        try {
            
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
                            ps.setInt(3, c.getId());
                            ps.setInt(4, l.getId());
                            ps.execute();
                        }
                    }
                }
            }
            
        }finally {
            
            sc.close();
        }     
    }
}