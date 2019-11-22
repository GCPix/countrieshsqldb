package com.countries.countriesAPI;

import java.sql.SQLException;

import com.countries.countriesAPI.dataAccess.CurrencyDataAccess;
import com.countries.countriesAPI.dataAccess.LanguageDataAccess;
import com.countries.countriesAPI.models.Currency;
import com.countries.countriesAPI.models.Language;
import com.db.DbInitialPopulate;

import org.hsqldb.cmdline.SqlToolError;

public class Runner {
    public static void main(String[] args) throws SqlToolError, SQLException {
        DbInitialPopulate dip = new DbInitialPopulate();
        dip.populateDatabase();

    }
}