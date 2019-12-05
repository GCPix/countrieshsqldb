package com.db;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.countries.countriesAPI.models.BasicCountry;

public class HsqldbStoredProcedures {
   
    public List<BasicCountry> getPagedSummary() throws SQLException, ClassNotFoundException {


        DbConnection dbc = new DbConnection();
        Connection conn = dbc.getConnection();
        CallableStatement call = conn.prepareCall("call getSummaries()");
        call.execute();
        ResultSet result = call.getResultSet();
        BasicCountry bc;
        List<BasicCountry> bcList = new ArrayList<>();
        while(result.next()){
            bc = new BasicCountry();
            bc.setId(result.getInt("id"));
            bc.setName(result.getString("name"));
            bcList.add(bc);
        }
        return bcList;
    }
}