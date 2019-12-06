package com.countries.countriesAPI.dataAccess;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.countries.Helpers.Filter;
import com.countries.Helpers.Pagination;
import com.countries.Helpers.ResponsePaged;
import com.countries.countriesAPI.models.BasicCountry;
import com.countries.countriesAPI.models.Country;
import com.countries.countriesAPI.models.Currency;
import com.countries.countriesAPI.models.Language;
import com.countries.countriesAPI.models.RegionalBlock;
import com.db.DbConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CountryDataAccess {

    public Country getCountry(int countryId) throws SQLException, ClassNotFoundException, IOException {
        DbConnection dbc = new DbConnection();
        Connection con = dbc.getConnection();
        Country country = new Country();

        String sqlStringCountry = "SELECT * FROM country WHERE id = " + countryId;

        country = this.getBasicCountry(sqlStringCountry, con, country);

        country = this.getAllCurrenciesForCountry(country, con);

        country = this.getAllBorderCountriesForCountry(country, con);

        country = this.getAllLanguagesForCountry(country, con);

        country = this.getAllRegionalBlocsForCountry(country, con);

        return country;
    }

    public ResponsePaged getCountriesSummary(String sortField, int pageSize, int startRecord, Filter filter)
            throws ClassNotFoundException, SQLException, UnsupportedEncodingException, JsonProcessingException {
        BasicCountry c = null;
        List<BasicCountry> countriesSummary = new ArrayList<>();
        String sqlString = "";
        DbConnection dbc = new DbConnection();

        // set initial values for pagination
        Pagination page = new Pagination(pageSize, sortField);
        System.out.print("Sort field" + sortField);

        try (Connection con = dbc.getConnection()) {
            page.setTotalElements(getCountriesCount(con));
            setNumberOfPages(page, con);
            setPageNumber(startRecord, page);
        }

        // get summary list
        try (Connection con = dbc.getConnection()) {
            String sqlBasicCountryQuery = "";
            String sqlStringStart = "SELECT * FROM country c";
            String sqlStringOrderPaged = " ORDER BY " + sortField + " LIMIT " + startRecord + "," + pageSize + ";";
            String sqlLanguageJoin = " JOIN country_language cl ON c.id=cl.COUNTRY_ID";
            String sqlCurrencyJoin = " JOIN country_currency cc ON c.id=cc.COUNTRY_ID";
            String sqlRegionalBlockJoin = " JOIN country_regionalblock crb ON c.id=crb.COUNTRY_ID";

            // if (filterValue.length()==0){
            // sqlString = "SELECT * FROM country ORDER BY " + sortField;
            // } else {
            // sqlString = "SELECT * from country " +
            // "join country_language on country.id = country_language.country_id " +
            // "join language on country_language.language_id = language.id " +
            // "WHERE language.name = " + filterValue + " ORDER BY " + sortField;
            // }

            // gets the field and value relating to Country and checks the db for it
            if (filter.getCountryFilterField() != null) {
                sqlBasicCountryQuery = " WHERE lower(c." + filter.getCountryFilterField() + ") LIKE '%"
                        + filter.getCountryFilterValue().toLowerCase() + "%'";
            }
            sqlString = sqlStringStart + sqlBasicCountryQuery + sqlStringOrderPaged;
            try (PreparedStatement ps = con.prepareStatement(sqlString)) {

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        c = new BasicCountry();
                        c.setId(rs.getInt("id"));
                        c.setName(rs.getString("name"));
                        c.setCapital(rs.getString("capital"));
                        c.setPopulation(rs.getLong("population"));
                        c.setRegion(rs.getString("region"));
                        c.setFlag(rs.getString("flag"));
                        countriesSummary.add(c);
                    }
                }
            }
        }
        this.setFirstPagePath(page, startRecord, filter);
        ResponsePaged rp = new ResponsePaged(page, countriesSummary);
        return rp;
    }

    public int deleteCountry(int countryId) throws SQLException, ClassNotFoundException {
        DbConnection dbc = new DbConnection();
        int noRowsReturned;
        try (Connection con = dbc.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM country WHERE id = " + countryId)) {
            noRowsReturned = ps.executeUpdate();
        }
        return noRowsReturned;
    }

    public int addCountry(Country country) throws ClassNotFoundException, SQLException, IOException {
        int id;
        DbConnection dbc = new DbConnection();
        Connection con = dbc.getConnection();
        // populate country in db
        id = populateCountryTable(country, con);
        country.setId(id);

        // populate country_currency in db

        populateCountryCurrencyTable(country, con);

        // populate country_border in db
        populateCountryBorderTable(country, con);

        // populate country_language in db

        populateCountryLanguageTable(country, con);

        // populate country_regionalblock in db
        populateCountryRegionalBlock(country, con);

        return id;
    }

    public void updateCountry(Country country, List<Integer> deletedCurrencies, List<Integer> deletedLanguages,
            List<Integer> deletedBorders, List<Integer> deletedRegionalBlocks)
            throws ClassNotFoundException, SQLException {
        DbConnection dbc = new DbConnection();
        Connection con = dbc.getConnection();

        // checks for missing relationships and adds them
        populateCountryCurrencyTable(country, con);
        populateCountryBorderTable(country, con);
        populateCountryLanguageTable(country, con);
        populateCountryRegionalBlock(country, con);
        // checks for removed currencies and deletes from relationship table
        int i = -1;
        ArrayList<Integer> dc = new ArrayList<>();
        dc.addAll(deletedCurrencies);
        for (int x : dc) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM country_currency WHERE country_id = "
                    + country.getId() + " AND currency_id = " + x + ";")) {
                i = ps.executeUpdate();
            }
            // System.out.println(i);
        }

        // checks for removed languages and deletes from relationship table
        int j = -1;
        // ArrayList<Integer> dc = new ArrayList<>();
        // dc.addAll(deletedCurrencies);
        for (int x : deletedLanguages) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM country_language WHERE country_id = "
                    + country.getId() + " AND language_id = " + x + ";")) {
                j = ps.executeUpdate();
            }
            // System.out.println(j);
        }

        // checks for removed borders and deletes them from the relationship table
        int k = -1;

        for (int x : deletedBorders) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM border WHERE country_id = " + country.getId()
                    + " AND country_border_id = " + x + ";")) {
                k = ps.executeUpdate();
            }
            // System.out.println(k);
        }

        // checks for removed regional blocks and deletes them from the relationship
        // table
        int l = -1;

        for (int x : deletedRegionalBlocks) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM country_regionalblock WHERE country_id = "
                    + country.getId() + " AND regionalblock_id = " + x + ";")) {
                l = ps.executeUpdate();
            }
            // System.out.println(l);
        }
    }

    private void populateCountryRegionalBlock(Country country, Connection con) throws SQLException {
        InputStream is = getClass().getResourceAsStream("../../../db/sqlScripts/populateCountryRBTable.sql");
        try (Scanner sc = new Scanner(is);) {
            StringBuffer sb = new StringBuffer();
            while (sc.hasNext()) {

                sb.append(sc.nextLine());

            }
            try (PreparedStatement ps = con.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);) {
                for (RegionalBlock rb : country.getRegionalBlocks()) {

                    ps.setInt(1, country.getId());
                    ps.setInt(2, rb.getId());
                    ps.setInt(3, country.getId());
                    ps.setInt(4, rb.getId());
                    ps.execute();

                }
            }
        }
    }

    private Country getAllRegionalBlocsForCountry(Country country, Connection con)
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

    private Country getAllLanguagesForCountry(Country country, Connection con)
            throws SQLException, ClassNotFoundException, IOException {
        String sqlStringCountryLanguage = "SELECT * FROM country_language WHERE country_id = " + country.getId();
        try (PreparedStatement psLanguageRelationship = con.prepareStatement(sqlStringCountryLanguage);
                ResultSet rs2 = psLanguageRelationship.executeQuery();) {

            List<Integer> languageList = new ArrayList<>();
            while (rs2.next()) {

                languageList.add(rs2.getInt("language_id"));
            }
            for (int ll : languageList) {
                Language l = new Language();
                LanguageDataAccess lda = new LanguageDataAccess();
                l = lda.getLanguage(ll);
                country.addLanguage(l);
            }

        }
        return country;
    }

    private Country getAllBorderCountriesForCountry(Country country, Connection con) throws SQLException {
        String sqlStringBorderCountries = "SELECT * FROM border WHERE country_id = " + country.getId();

        try (PreparedStatement psBorder = con.prepareStatement(sqlStringBorderCountries);
                ResultSet rs3 = psBorder.executeQuery()) {

            List<Integer> borderList = new ArrayList<>();
            while (rs3.next()) {

                borderList.add(rs3.getInt("country_border_id"));
            }

            for (int b : borderList) {
                Country border = new Country();
                String borderSqlString = "SELECT * FROM country WHERE id = " + b + ";";
                this.getBasicCountry(borderSqlString, con, border);
                country.addBorderCountry(border);
            }
        }

        return country;
    }

    private Country getBasicCountry(String sqlString, Connection con, Country country) throws SQLException {

        try (PreparedStatement psCountry = con.prepareStatement(sqlString)) {
            try (ResultSet rs = psCountry.executeQuery()) {

                while (rs.next()) {

                    country.setId(rs.getInt("id"));
                    country.setName(rs.getString("name"));
                    country.setCapital(rs.getString("capital"));
                    country.setPopulation(rs.getLong("population"));
                    country.setRegion(rs.getString("region"));
                    country.setFlag(rs.getString("flag"));
                }
            }
        }
        return country;
    }

    private Country getAllCurrenciesForCountry(Country country, Connection con)
            throws ClassNotFoundException, SQLException, IOException {
        List<Integer> currencyIdList = this.getCurrencyIdList(country.getId(), con);

        for (int cl : currencyIdList) {
            Currency c = new Currency();
            CurrencyDataAccess cda = new CurrencyDataAccess();
            c = cda.getCurrency(cl);
            country.addCurrency(c);
        }

        return country;
    }

    private List<Integer> getCurrencyIdList(int countryId, Connection con) throws SQLException {
        String sqlStringCountryCurrency = "SELECT * FROM country_currency WHERE country_id = " + countryId;
        List<Integer> currencyList;
        try (PreparedStatement psCurrencyRelationship = con.prepareStatement(sqlStringCountryCurrency)) {
            try (ResultSet rs1 = psCurrencyRelationship.executeQuery()) {
                currencyList = new ArrayList<>();
                while (rs1.next()) {

                    currencyList.add(rs1.getInt("currency_id"));
                }

            }
        }
        return currencyList;
    }

    private int populateCountryTable(Country country, Connection con) throws SQLException, IOException {
        int id;
        StringBuffer sb = new StringBuffer();
        try (InputStream is = getClass().getResourceAsStream("../../../db/sqlScripts/populateCountryTable.sql");
                Scanner sc = new Scanner(is);) {

            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }
        }
        try (PreparedStatement ps = con.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, country.getName());
            ps.setString(2, country.getCapital());
            ps.setLong(3, country.getPopulation());
            ps.setString(4, country.getRegion());
            ps.setString(5, country.getFlag());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                id = rs.getInt("id");
            }
        }
        return id;
    }

    private void populateCountryCurrencyTable(Country country, Connection con) throws SQLException {
        String sqlScript = "../../../db/sqlScripts/populateCountryCurrencyTable.sql";
        InputStream is = getClass().getResourceAsStream(sqlScript);
        try (Scanner sc = new Scanner(is);) {
            StringBuffer sb = new StringBuffer();
            while (sc.hasNext()) {

                sb.append(sc.nextLine());

            }
            try (PreparedStatement ps = con.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);) {
                for (Currency cocu : country.getCurrencies()) {

                    ps.setInt(1, country.getId());
                    ps.setInt(2, cocu.getId());
                    ps.setInt(3, country.getId());
                    ps.setInt(4, cocu.getId());
                    ps.execute();

                }
            }
        }

    }

    private void populateCountryBorderTable(Country country, Connection con) throws SQLException {
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

    private void populateCountryLanguageTable(Country country, Connection con) throws SQLException {
        InputStream is = getClass().getResourceAsStream("../../../db/sqlScripts/populateCountryLanguageTable.sql");
        try (Scanner sc = new Scanner(is);) {
            StringBuffer sb = new StringBuffer();
            while (sc.hasNext()) {

                sb.append(sc.nextLine());

            }
            try (PreparedStatement ps = con.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);) {
                for (Language l : country.getLanguages()) {

                    ps.setInt(1, country.getId());
                    ps.setInt(2, l.getId());
                    ps.setInt(3, country.getId());
                    ps.setInt(4, l.getId());
                    ps.execute();

                }
            }
        }
    }

    private int getCountriesCount(Connection con) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM country;";
        int count;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery();) {
                rs.next();
                count = rs.getInt("total");
            }

        }

        return count;
    }

    private void setNumberOfPages(Pagination page, Connection con) throws SQLException {

        int totalPages = page.getTotalElements() / page.getPageSize();
        page.setTotalPages(totalPages);

    }

    private void setPageNumber(int startRecord, Pagination page) {
        int pageNumber;

        if (startRecord == 0) {
            pageNumber = 1;
        } else if (startRecord % page.getPageSize() != 0) {
            pageNumber = startRecord / page.getPageSize() + 1;
        } else {
            pageNumber = startRecord / page.getPageSize();
        }

        page.setPageNumber(pageNumber);
    }

    private void setFirstPagePath(Pagination page, int startRecord, Filter filter)
            throws UnsupportedEncodingException, JsonProcessingException {
        String firstPagePath = "";
        int newStartRecord = startRecord + page.getPageSize();

        // Gson gson = new Gson();
        // String jsonFilterString = gson.toJson(filter);
        
        ObjectMapper mapper = new ObjectMapper();
        String jsonFilterString = mapper.writeValueAsString(filter);
        // I don't the workaround for filter but it works for now.  It looks silly and not sure if it will handle full object
        // If I can't find an alternative it will have to be built by hand rather than using Gson
        if (startRecord != 0) firstPagePath = "/countries/summary/?sortField=" + page.getSortBy() 
        + "&pageSize =" + page.getPageSize() + "&startRecord=" + newStartRecord + "&filter=" 
        + URLEncoder.encode(jsonFilterString,"UTF-8");
        
        page.setFirstPagePath(firstPagePath);
    }
}