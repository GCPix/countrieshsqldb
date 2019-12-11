package com.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import org.hsqldb.cmdline.SqlToolError;

public class DbConnection {

    private Connection connection;
    private final String connectionString = "jdbc:hsqldb:file:db-data/countries";
    private String dbDriver = "org.hsqldb.jdbc.JDBCDriver";

    public DbConnection() {

    }

    public void loadDriver() throws ClassNotFoundException {

        Class.forName(dbDriver);
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
            this.loadDriver();
            connection = DriverManager.getConnection(connectionString, "SA", "");
            return connection;
    }

    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    void createTables(Connection connection) throws IOException, SQLException, SqlToolError {
        Scanner sc = null;
      
        try (InputStream is = getClass().getResourceAsStream("sqlScripts/createTables.sql")) {
            // should this be throwing something like File not found or class not found exception?
            if (is != null) {
                sc = new Scanner(is);
                StringBuffer sb = new StringBuffer();
                while(sc.hasNext()){
                    sb.append(sc.nextLine());
                }
                String[] singleQueryList = sb.toString().split(";");
                //need to look at making sure this is always valid and doesn't contain stuff it doesn't need
                for (String q: singleQueryList) {
                   
                    try (PreparedStatement ps = connection.prepareStatement(q)) {
                        ps.execute();
                    }
                }
            }
        } finally {
            sc.close();
        }
        
       
       
    }
    

}