package com.db;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

public class DbConnection {

    private Connection connection;
    private final String connectionString = "jdbc:hsqldb:file:db-data/countries";
    private String dbDriver = "org.hsqldb.jdbc.JDBCDriver";

    // private String dbDriver = "";
    // assume the passed in connection is final just because it cannot be changed
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
        InputStream is = getClass().getResourceAsStream("sqlScripts/createTables.sql");
        SqlFile createTables = new SqlFile(new InputStreamReader(is), "init", System.out, "UTF-8", false, new File("."));
            createTables.setConnection(connection);
            createTables.execute();

        // Scanner sc = new Scanner(is);
        // StringBuffer sb = new StringBuffer();
        // while(sc.hasNext()){
        //     sb.append(sc.nextLine());
        // }
        // String sbString = sb.toString();
        // try (PreparedStatement ps = connection.prepareStatement(sbString)) {
        //     ps.execute();
        // } finally {
        //     is.close();
        //     sc.close();
        // }
    }
    

}