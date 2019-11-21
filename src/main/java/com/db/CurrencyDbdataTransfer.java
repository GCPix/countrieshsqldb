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
import com.countries.countriesAPI.models.Currency;

public class CurrencyDbdataTransfer {
    private Connection connection;

    public CurrencyDbdataTransfer(){

    }
 
    DbConnection dbc = new DbConnection();
    
    public void populateCurrencyTable(List<Country> countryList) throws SQLException {
        InputStream is = getClass().getResourceAsStream("sqlScripts/populateCurrencyTable.sql");
        Scanner sc = new Scanner(is);
        try {
            connection = dbc.getConnection();
            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()){

                sb.append(sc.nextLine());
            
            }
            PreparedStatement ps = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);

            for(Country c: countryList){
                for (Currency cu: c.getCurrencies()){
                    ps.setString(1, cu.getCode());
                    ps.setString(2, cu.getName());
                    ps.setString(3, cu.getSymbol());
                    ps.setString(4, cu.getName());
                    ps.execute();
                    if(ps.getUpdateCount() > 0){
                        ResultSet rs = ps.getGeneratedKeys();
                        rs.next();
                        cu.setId(rs.getInt("id"));
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

    public ArrayList<Currency> getCurrencyList() throws SQLException {
        ArrayList<Currency> currencyList = new ArrayList<>();
        try{
            connection = dbc.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM currency");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Currency cur = new Currency();
                cur.setId(rs.getInt("id"));
                cur.setCode(rs.getString("code"));
                cur.setName(rs.getString("name"));
                cur.setSymbol(rs.getString("symbol"));
                currencyList.add(cur);
            }
        
        }finally {
            dbc.closeConnection(connection);
        }
        return currencyList;
    }

    public void populateCountryCurrencyTable(List<Country> countryList, List<Currency> currencyList)
            throws SQLException {
        InputStream is = getClass().getResourceAsStream("sqlScripts/populateCountryCurrencyTable.sql");
        Scanner sc = new Scanner(is);
        try {
            connection = dbc.getConnection();
            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()){

                sb.append(sc.nextLine());
            
            }
            PreparedStatement ps = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);
            
            for(Country c: countryList){
                for(Currency cocu: c.getCurrencies()){
                    for(Currency cu: currencyList){
                        if(cocu.getName()!= null && cu.getName() != null && cu.getName().equalsIgnoreCase(cocu.getName())){
                            ps.setInt(1, c.getId());
                            ps.setInt(2, cu.getId());
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