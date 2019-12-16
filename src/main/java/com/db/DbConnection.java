package com.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.hsqldb.cmdline.SqlToolError;

import com.countries.Helpers.SqlScriptParser;

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
 
        SqlScriptParser ssp = new SqlScriptParser();
        String sqlScript = "../../db/sqlScripts/createTables.sql";
        String sqlString = ssp.getSqlString(sqlScript);
            
        String[] singleQueryList = sqlString.split(";");
                //need to look at making sure this is always valid and doesn't contain stuff it doesn't need
                for (String q: singleQueryList) {
                   
                    try (PreparedStatement ps = connection.prepareStatement(q)) {
                        ps.execute();
                    }
                }
            }
  
        
       
       
    }
