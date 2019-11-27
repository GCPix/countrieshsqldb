package com.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DbConnection {

    private Connection connection;
    private final String connectionString = "jdbc:hsqldb:file:db-data/countries";
    private String dbDriver = "org.hsqldb.jdbc.JDBCDriver";
    // private String dbDriver = "";
    // assume the passed in connection is final just because it cannot be changed
    public DbConnection(){

    }
    
    public void loadDriver() throws ClassNotFoundException {
     
            Class.forName(dbDriver);
    }

    public Connection getConnection() throws SQLException {
        try {
            this.loadDriver();
            connection = DriverManager.getConnection(connectionString, "SA", "");
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return connection;
    }

    public void closeConnection(Connection connection) throws SQLException{
        connection.close();
    }
    
    private void createTables(Connection connection) throws IOException, SQLException {
        InputStream is = getClass().getResourceAsStream("sqlScripts/createTables.sql");
        Scanner sc = new Scanner(is);
        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()){
            sb.append(sc.nextLine());
        }
        try (PreparedStatement ps = connection.prepareStatement(sb.toString())) {
            ps.execute();
        } finally {
            is.close();
            sc.close();
            // connection.close();
        }
    }
    public void createDatabase(Connection connection) throws SQLException, IOException, ClassNotFoundException {
            
            this.createTables(connection);
     
    }

}