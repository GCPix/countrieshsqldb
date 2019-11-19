package com.countries.countriesAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.db.DbConnection;

public class Language {
    @JsonIgnore
    private int id;
    private String iso639_1;
    private String iso639_2;
    private String name;
    private String nativeName;

    public Language() {

    }

    /**
     * @return the id
     */

    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the iso639_1
     */
    public String getIso639_1() {
        return iso639_1;
    }

    /**
     * @param iso639_1 the iso639_1 to set
     */
    public void setIso639_1(String iso639_1) {
        this.iso639_1 = iso639_1;
    }

    /**
     * @return the iso639_2
     */
    public String getIso639_2() {
        return iso639_2;
    }

    /**
     * @param iso639_2 the iso639_2 to set
     */
    public void setIso639_2(String iso639_2) {
        this.iso639_2 = iso639_2;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the nativeName
     */
    public String getNativeName() {
        return nativeName;
    }

    /**
     * @param nativeName the nativeName to set
     */
    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
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