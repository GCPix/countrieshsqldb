package com.countries.countriesAPI.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.countries.countriesAPI.models.Country;
import com.countries.countriesAPI.models.Currency;
import com.countries.countriesAPI.models.Language;
import com.db.DbConnection;

public class CountryDataAccess {

    public Country getCountry(int countryId) throws Exception {
        DbConnection dbc = new DbConnection();
        dbc.loadDriver();
        Connection con = dbc.getConnection();
        Country country =null;
  
        /*
        get basic country data
        */
        String sqlStringCountry = "SELECT * FROM country WHERE id = " + countryId;
        try (PreparedStatement psCountry = con.prepareStatement(sqlStringCountry)) {
            try (ResultSet rs = psCountry.executeQuery();) {
                
                while(rs.next()){
                    country = new Country();
                    country.setId(rs.getInt("id"));
                    country.setName(rs.getString("name"));
                    country.setCapital(rs.getString("capital"));
                    country.setPopulation(rs.getLong("population"));
                }
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }

        /*
        bring back the currency objects based on the ids
        */
        String sqlStringCountryCurrency = "SELECT * FROM country_currency WHERE country_id = " + countryId;
        try (PreparedStatement psCurrencyRelationship = con.prepareStatement(sqlStringCountryCurrency)) {
            try (ResultSet rs1 = psCurrencyRelationship.executeQuery()) {
                List<Integer> currencyList = new ArrayList<>();
                while(rs1.next()){
                    
                    currencyList.add(rs1.getInt("currency_id"));
                }
                for(int cl: currencyList){
                    Currency c = new Currency();
                    CurrencyDataAccess cda = new CurrencyDataAccess();
                    c = cda.getCurrency(cl);
                    country.addCurrency(c);
                }
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
     
        /*
        bring back the language objects based on the ids
        */
        String sqlStringCountryLanguage = "SELECT * FROM country_language WHERE country_id = " + countryId;
        try (PreparedStatement psLanguageRelationship = con.prepareStatement(sqlStringCountryLanguage);) {
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
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
      	return country;
	}
    
}