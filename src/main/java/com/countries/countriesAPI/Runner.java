package com.countries.countriesAPI;

import java.io.IOException;
import java.sql.SQLException;
import com.db.DbInitialPopulate;
import org.hsqldb.cmdline.SqlToolError;

public class Runner {
    public static void main(String[] args) throws ClassNotFoundException, SqlToolError, IOException, SQLException {
        DbInitialPopulate dip = new DbInitialPopulate();
        dip.populateDatabase();
    }
   
}