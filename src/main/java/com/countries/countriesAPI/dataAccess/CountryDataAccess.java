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
import com.countries.countriesAPI.models.RegionalBlock;
import com.db.DbConnection;

public class CountryDataAccess {

    public Country getCountry(int countryId) throws SQLException, ClassNotFoundException, IOException {
        DbConnection dbc = new DbConnection();
        Connection con = dbc.getConnection();
        Country country = new Country();
        
            String sqlStringCountry = "SELECT * FROM country WHERE id = " + countryId;
            
            country = this.getBasicCountry(sqlStringCountry, con, country);
           
            country = this.getAllCurrenciesForCountry(country, con);

            country = this.getAllBorderCountriesForCountry(country, con);
                
            country = this.getAllLanguagesForCountry(country, con);
            
            country = this.getAllRegionalBlocsForCountry(country, con);

       
        return country;
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
                            c.setRegion(rs.getString("region"));
                            c.setFlag(rs.getString("flag"));
                            countriesSummary.add(c);
                        }
                    } 
                } 
            } 
            return countriesSummary;
        }

    public void deleteCountry(int countryId) throws SQLException, ClassNotFoundException {
        DbConnection dbc = new DbConnection();
        try(Connection con = dbc.getConnection(); PreparedStatement ps = con.prepareStatement("DELETE FROM country WHERE id = " + countryId)){
            ps.execute();
        }
    }
    private Country getAllRegionalBlocsForCountry(Country country, Connection con) throws SQLException {
        String sqlStringCountryRegionalBlock = "SELECT * FROM country_regionalblock WHERE country_id = " + country.getId();
        try(PreparedStatement ps = con.prepareStatement(sqlStringCountryRegionalBlock)){
            try (ResultSet rs = ps.executeQuery()){
                List<Integer> RegionalBlockCountryList = new ArrayList<>();
                while(rs.next()){
                    RegionalBlockCountryList.add(rs.getInt("regionalblock_id"));
                }
                for(int rbId: RegionalBlockCountryList){
                    RegionalBlock rb = new RegionalBlock();
                    RegionalBlockDataAccess rbddt = new RegionalBlockDataAccess();
                    rb = rbddt.getRegionalBlock(rbId, con);
                    country.addRegionalBlock(rb);
                }
            }
        }
        return country;
    }

    private Country getAllLanguagesForCountry(Country country, Connection con)
            throws SQLException, ClassNotFoundException, IOException {
        String sqlStringCountryLanguage = "SELECT * FROM country_language WHERE country_id = " + country.getId();
        try (PreparedStatement psLanguageRelationship = con.prepareStatement(sqlStringCountryLanguage); 
        ResultSet rs2 = psLanguageRelationship.executeQuery();) {
            
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
        return country;
    }
    

    private Country getAllBorderCountriesForCountry(Country country, Connection con) throws SQLException {
        String sqlStringBorderCountries = "SELECT * FROM border WHERE country_id = " + country.getId();

        try (PreparedStatement psBorder = con.prepareStatement(sqlStringBorderCountries); ResultSet rs3 = psBorder.executeQuery()) {
           
                List<Integer> borderList = new ArrayList<>();
                while(rs3.next()){
                    
                    borderList.add(rs3.getInt("country_border_id"));
                }

                for(int b: borderList){                    
                    Country border = new Country();
                    String borderSqlString = "SELECT * FROM country WHERE id = " + b + ";";
                    this.getBasicCountry(borderSqlString, con, border);
                    country.addBorderCountry(border);
                }
        } 

        return country;
    }

    private Country getBasicCountry(String sqlString, Connection con, Country country) throws SQLException {
    
        
        try (PreparedStatement psCountry = con.prepareStatement(sqlString)) {
            try (ResultSet rs = psCountry.executeQuery()) {
                
                while(rs.next()){
                    
                    country.setId(rs.getInt("id"));
                    country.setName(rs.getString("name"));
                    country.setCapital(rs.getString("capital"));
                    country.setPopulation(rs.getLong("population"));
                    country.setRegion(rs.getString("region"));
                    country.setFlag(rs.getString("flag"));
                }
            } 
        } 
        return country;
    }

    private Country getAllCurrenciesForCountry(Country country, Connection con)
                throws ClassNotFoundException, SQLException, IOException {
        List<Integer> currencyIdList = this.getCurrencyIdList(country.getId(), con);
            
        for (int cl : currencyIdList) {
            Currency c = new Currency();
            CurrencyDataAccess cda = new CurrencyDataAccess();
            c = cda.getCurrency(cl);
            country.addCurrency(c);
        }

        return country;
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