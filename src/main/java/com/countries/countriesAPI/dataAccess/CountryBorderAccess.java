package com.countries.countriesAPI.dataAccess;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.countries.countriesAPI.models.BasicCountry;
import com.countries.countriesAPI.models.Country;

public class CountryBorderAccess {
    public void populateCountryBorderTable(Country country, Connection con) throws SQLException {
        InputStream is = getClass().getResourceAsStream("../../../db/sqlScripts/populateBorderTable.sql");

        try (Scanner sc = new Scanner(is)) {

            StringBuffer sb = new StringBuffer();

            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }

            try (PreparedStatement ps = con.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);) {
                for (BasicCountry b : country.getBorderCountriesList()) {

                    ps.setInt(1, country.getId());
                    ps.setInt(2, b.getId());
                    ps.setInt(3, country.getId());
                    ps.setInt(4, b.getId());
                    ps.execute();
                }
            }

        }
    }

}
