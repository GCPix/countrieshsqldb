package com.countries.countriesAPI.dataAccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.countries.countriesAPI.models.BasicCountry;
import com.countries.countriesAPI.models.Country;
import com.countries.countriesAPI.models.Currency;
import com.countries.countriesAPI.models.Language;
import com.db.DbConnection;

public class CountryDataAccess {

    public Country getCountry(int countryId) throws SQLException, ClassNotFoundException, IOException {
        DbConnection dbc = new DbConnection();
        Connection con = dbc.getConnection();
        Country country =null;
  
        /*
        get basic country data
        */
        String sqlStringCountry = "SELECT * FROM country WHERE id = " + countryId;
        try (PreparedStatement psCountry = con.prepareStatement(sqlStringCountry)) {
            try (ResultSet rs = psCountry.executeQuery()) {
                
                while(rs.next()){
                    country = new Country();
                    country.setId(rs.getInt("id"));
                    country.setName(rs.getString("name"));
                    country.setCapital(rs.getString("capital"));
                    country.setPopulation(rs.getLong("population"));
                }
            } 
        } 

        /*
        bring back the currency objects based on the ids
        */
        List<Integer> currencyIdList = this.getCurrencyIdList(countryId, con);
        
        for (int cl : currencyIdList) {
            Currency c = new Currency();
            CurrencyDataAccess cda = new CurrencyDataAccess();
            c = cda.getCurrency(cl);
            country.addCurrency(c);
        }

        String sqlStringBorderCountries = "SELECT * FROM border WHERE country_id = " + countryId;
        try (PreparedStatement psBorder = con.prepareStatement(sqlStringBorderCountries)) {
            try (ResultSet rs3 = psBorder.executeQuery()) {
                List<Integer> borderList = new ArrayList<>();
                while(rs3.next()){
                    
                    borderList.add(rs3.getInt("country_border_id"));
                }

                for(int b: borderList){
                    CountryDataAccess cdaBorder = new CountryDataAccess();
                    BasicCountry border = new BasicCountry();
                    border = cdaBorder.getBorderCountry(b, con);
                    
                    country.addBorderCountry(border);
                    
                }
            } 
     
        /*
        bring back the language objects based on the ids
        */
        String sqlStringCountryLanguage = "SELECT * FROM country_language WHERE country_id = " + countryId;
        try (PreparedStatement psLanguageRelationship = con.prepareStatement(sqlStringCountryLanguage)) {
            try (ResultSet rs2 = psLanguageRelationship.executeQuery();) {
                List<Integer> languageList = new ArrayList<>();
                while(rs2.next()){
                    
                    languageList.add(rs2.getInt("language_id"));
                }
                for(int ll: languageList){
                    Language l = new Language();
                    LanguageDataAccess lda = new LanguageDataAccess();
                    l = lda.getLanguage(ll);
                    country.addLanguage(l);
                }
            }
        } 
          return country;
        }
	}
    
     private BasicCountry getBorderCountry(int b, Connection con) throws SQLException {
        BasicCountry border = null;
        String sqlStringCountry = "SELECT * FROM country WHERE id = " + b;
        try (PreparedStatement psBorder = con.prepareStatement(sqlStringCountry)) {
            try (ResultSet rs = psBorder.executeQuery()) {
                
                while(rs.next()){
                    border = new BasicCountry();
                    border.setId(rs.getInt("id"));
                    border.setName(rs.getString("name"));
                    border.setCapital(rs.getString("capital"));
                    border.setPopulation(rs.getLong("population"));
                }
            } 
        } 
        return border;
    }

    public List<BasicCountry> getCountriesSummary(String sortField, String filterField, String filterValue)
            throws ClassNotFoundException, SQLException {
        BasicCountry c = null;
        List<BasicCountry> countriesSummary =  new ArrayList<>();
        String sqlString = "";
        DbConnection dbc = new DbConnection();
        
        try (Connection con = dbc.getConnection()) {
            if (filterValue.length()==0){
                sqlString = "SELECT * FROM country ORDER BY " + sortField;
            } else {
                sqlString = "SELECT * from country " + 
                "join country_language on country.id = country_language.country_id " + 
                "join language  on country_language.language_id = language.id " + 
                "WHERE language.name = " + filterValue + " ORDER BY " + sortField;
            }

            try (PreparedStatement ps = con.prepareStatement(sqlString)) {
                
                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()){
                        c = new BasicCountry();
                        c.setId(rs.getInt("id"));
                        c.setName(rs.getString("name"));
                        c.setCapital(rs.getString("capital"));
                        c.setPopulation(rs.getLong("population"));
                        countriesSummary.add(c);
                    }
                } 
            } 
        } 
        return countriesSummary;
    }

    private List<Integer> getCurrencyIdList(int countryId, Connection con) throws SQLException {
        String sqlStringCountryCurrency = "SELECT * FROM country_currency WHERE country_id = " + countryId;
        List<Integer> currencyList;
        try (PreparedStatement psCurrencyRelationship = con.prepareStatement(sqlStringCountryCurrency)) {
            try (ResultSet rs1 = psCurrencyRelationship.executeQuery()) {
                currencyList = new ArrayList<>();
                while(rs1.next()){
                    
                    currencyList.add(rs1.getInt("currency_id"));
                }
                
            } 
        } 
        return currencyList;
    }
    
}