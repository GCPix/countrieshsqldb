package com.countries.countriesAPI.dataAccess;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.countries.countriesAPI.models.Currency;
import com.db.DbConnection;

public class CurrencyDataAccess {

    public static List<Currency> getCurrencies() throws SQLException {
        List<Currency> currencyList = null;
        DbConnection dc  = new DbConnection();
        dc.loadDriver();
        Connection connection = dc.getConnection();
        try {

            PreparedStatement ps = connection.prepareStatement("Select * from currency");
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                currencyList = new ArrayList<Currency>();
                Currency c = new Currency();
                c.setId(rs.getInt("id"));
                c.setCode(rs.getString("code"));
                c.setName(rs.getString("name"));
                c.setSymbol(rs.getString("symbol"));
                currencyList.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dc.closeConnection(connection);
        }
		return currencyList;
    }

	public Currency getCurrency(int currencyId) throws SQLException {
        DbConnection dbc = new DbConnection();
        dbc.loadDriver();
        Connection con = dbc.getConnection();
        Currency c = null;

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM currency WHERE id = " + currencyId);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                c = new Currency();
                c.setId(rs.getInt("id"));
                c.setCode(rs.getString("code"));
                c.setName(rs.getString("name"));
                c.setSymbol(rs.getString("symbol"));
            }
            


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbc.closeConnection(con);
        }
		return c;
	}

	public int addCurrency(Currency currency) throws SQLException {
        DbConnection dbc = new DbConnection();
        dbc.loadDriver();
        Connection con = dbc.getConnection();
        InputStream is = getClass().getResourceAsStream("../../../db/sqlScripts/populateCurrencyTable.sql");
        Scanner sc = new Scanner(is);
        int id = -1;
        try {
            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()){
                sb.append(sc.nextLine());
            }            
            PreparedStatement ps = con.prepareStatement(sb.toString());
            ps.setString(1, currency.getCode());
            ps.setString(2, currency.getName());
            ps.setString(3, currency.getSymbol());
            ps.setString(4, currency.getName());
            ps.execute();

            PreparedStatement psid = con.prepareStatement("SELECT * FROM currency WHERE name = '" + currency.getName() + "'");
            ResultSet rs = psid.executeQuery();
            while(rs.next()){
                id = rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbc.closeConnection(con);
            sc.close();
        }
		return id;
	}

	public void updateCurrency(int currencyId, Currency currency) throws SQLException {
        DbConnection dbc = new DbConnection();
        Connection con = dbc.getConnection();
        
        try {
        
            String sqlString = "UPDATE currency SET code = '" + currency.getCode() + "', name = '" + currency.getName()
                    + "', symbol = '" + currency.getSymbol() + "' WHERE id = " + currencyId; 

            PreparedStatement ps = con.prepareStatement(sqlString);
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbc.closeConnection(con);
        }
        
	}
    
}