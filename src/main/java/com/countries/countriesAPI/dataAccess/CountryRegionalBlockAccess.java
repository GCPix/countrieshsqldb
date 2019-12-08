package com.countries.countriesAPI.dataAccess;

import com.countries.countriesAPI.models.Country;
import com.countries.countriesAPI.models.RegionalBlock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryRegionalBlockAccess {

    public CountryRegionalBlockAccess(){

    }

    protected Country getAllRegionalBlocsForCountry(Country country, Connection con)
            throws SQLException, ClassNotFoundException {
        String sqlStringCountryRegionalBlock = "SELECT * FROM country_regionalblock WHERE country_id = "
                + country.getId();
        try (PreparedStatement ps = con.prepareStatement(sqlStringCountryRegionalBlock)) {
            try (ResultSet rs = ps.executeQuery()) {
                List<Integer> RegionalBlockCountryList = new ArrayList<>();
                while (rs.next()) {
                    RegionalBlockCountryList.add(rs.getInt("regionalblock_id"));
                }
                for (int rbId : RegionalBlockCountryList) {
                    RegionalBlock rb = new RegionalBlock();
                    RegionalBlockDataAccess rbddt = new RegionalBlockDataAccess();
                    rb = rbddt.getRegionalBlock(rbId, con);
                    country.addRegionalBlock(rb);
                }
            }
        }
        return country;
    }
}
