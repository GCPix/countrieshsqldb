package com.db;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

public class DbConnection {

    public Connection connection;
    static final String connectionString = "jdbc:hsqldb:file:db-data/countries";
    static String dbDriver = "org.hsqldb.jdbc.JDBCDriver";

    // assume the passed in connection is final just because it cannot be changed
    public DbConnection(){

    }
    
    public void loadDriver() {
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        try {

            connection = DriverManager.getConnection(connectionString, "SA", "");
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return connection;
    }

    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
    private void createTables(Connection connection) throws SQLException, SqlToolError {
        
        try{
            InputStream is = getClass().getResourceAsStream("sqlScripts/createTables.sql");
            SqlFile createTables = new SqlFile(new InputStreamReader(is), "init", System.out, "UTF-8", false, new File("."));
            createTables.setConnection(connection);
            createTables.execute();

        } catch (IOException e) {
  
            e.printStackTrace();
        }
    }
    public void createDatabase() throws SQLException, SqlToolError {
        Connection connection = null;
        this.loadDriver();
        connection = this.getConnection();
        this.createTables(connection);
        this.closeConnection(connection);
    }

}