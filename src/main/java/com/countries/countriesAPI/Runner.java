package com.countries.countriesAPI;

import java.sql.SQLException;

import com.db.DbDataTransfer;

import org.hsqldb.cmdline.SqlToolError;

public class Runner {
    public static void main(String[] args) throws SqlToolError, SQLException {
        DbDataTransfer ddt = new DbDataTransfer();
        ddt.populateDatabase();
    }
}