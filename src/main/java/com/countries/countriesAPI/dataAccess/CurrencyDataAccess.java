package com.countries.countriesAPI.dataAccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.countries.Helpers.SqlScriptParser;
import com.countries.countriesAPI.models.Currency;
import com.db.DbConnection;

public class CurrencyDataAccess {

    public List<Currency> getCurrencies() throws SQLException, IOException, ClassNotFoundException {
        List<Currency> currencyList = null;
        DbConnection dc = new DbConnection();
        
        Connection connection = dc.getConnection();
        try {
            try (PreparedStatement ps = connection.prepareStatement("Select * from currency")) {
                try (ResultSet rs = ps.executeQuery()) {
                    currencyList = new ArrayList<Currency>();
                    while (rs.next()) {
                        Currency c = new Currency();
                        c.setId(rs.getInt("id"));
                        c.setCode(rs.getString("code"));
                        c.setName(rs.getString("name"));
                        c.setSymbol(rs.getString("symbol"));
                        currencyList.add(c);
                    }
                } 
            } 
 
        } finally {
            dc.closeConnection(connection);
        }
        return currencyList;
    }

    public Currency getCurrency(int currencyId) throws SQLException, IOException, ClassNotFoundException {
        DbConnection dbc = new DbConnection();
        
        Connection con = dbc.getConnection();
        Currency c = null;

        try {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM currency WHERE id = " + currencyId)) {
                try (ResultSet rs = ps.executeQuery();) {
                    while (rs.next()) {
                        c = new Currency();
                        c.setId(rs.getInt("id"));
                        c.setCode(rs.getString("code"));
                        c.setName(rs.getString("name"));
                        c.setSymbol(rs.getString("symbol"));
                    }
                } 
            } 
  
        } finally {
            dbc.closeConnection(con);
        }
        return c;
    }

    public int addCurrency(Currency currency) throws SQLException, IOException, ClassNotFoundException {
        DbConnection dbc = new DbConnection();
        String sqlScript = ("../../../db/sqlScripts/populateCurrencyTable.sql");
        int id = -1;

            try(Connection con = dbc.getConnection();){
            	SqlScriptParser  ssp = new SqlScriptParser();
            	String sqlString = ssp.getSqlString(sqlScript);

	            try (PreparedStatement ps = con.prepareStatement(sqlString)) {
	                ps.setString(1, currency.getCode());
	                ps.setString(2, currency.getName());
	                ps.setString(3, currency.getSymbol());
	                ps.setString(4, currency.getName());
	                ps.execute();
	                
	                try (PreparedStatement psid = con.prepareStatement("SELECT * FROM currency WHERE name = '" + currency.getName() + "'")) {
	                    try (ResultSet rs = psid.executeQuery()) {
	                        
	                        while(rs.next()){
	                        id = rs.getInt("id");
	                        }
	                    } 
	                } 
	            } 
 
            } 
		return id;
	}

	public int updateCurrency(int currencyId, Currency currency) throws SQLException, IOException, ClassNotFoundException {
        DbConnection dbc = new DbConnection();
        int noRowsReturned;

        try (Connection con = dbc.getConnection();){
        
            String sqlString = "UPDATE currency SET code = '" + currency.getCode() + "', name = '" + currency.getName()
                    + "', symbol = '" + currency.getSymbol() + "' WHERE id = " + currencyId; 
            
            try (PreparedStatement ps = con.prepareStatement(sqlString);) {
                noRowsReturned = ps.executeUpdate();
            } 
        } 
        return noRowsReturned;
    }
    public int deleteCurrency(int id) throws SQLException, ClassNotFoundException {
        DbConnection dbc = new DbConnection();
        Connection con = dbc.getConnection();
        int noRowsReturned;

        try(PreparedStatement ps = con.prepareStatement("DELETE FROM currency WHERE id = " + id + ";")) {
            noRowsReturned = ps.executeUpdate();
            
        }
        return noRowsReturned;
    }
    
}