package com.countries.countriesAPI.dataAccess;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.countries.countriesAPI.models.BasicCountry;
import com.countries.countriesAPI.models.Country;
import com.countries.countriesAPI.models.Currency;
import com.countries.countriesAPI.models.Filter;
import com.countries.countriesAPI.models.Language;
import com.countries.countriesAPI.models.Pagination;
import com.countries.countriesAPI.models.RegionalBlock;
import com.countries.countriesAPI.models.ResponsePaged;
import com.db.DbConnection;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CountryDataAccess {

    public Country getCountry(int countryId) throws SQLException, ClassNotFoundException, IOException {
        DbConnection dbc = new DbConnection();
        Connection con = dbc.getConnection();
        Country country = new Country();
        CountryRegionalBlockAccess crbdao = new CountryRegionalBlockAccess();

        String sqlStringCountry = "SELECT * FROM country WHERE id = " + countryId;

        country = this.getBasicCountry(sqlStringCountry, con, country);

        country = this.getAllCurrenciesForCountry(country, con);

        country = this.getAllBorderCountriesForCountry(country, con);

        country = this.getAllLanguagesForCountry(country, con);

        country = crbdao.getAllRegionalBlocsForCountry(country, con);

        return country;
    }

    public ResponsePaged getCountriesSummary(String sortField, int pageSize, int pageNumber, Filter filter)
            throws ClassNotFoundException, SQLException, UnsupportedEncodingException, JsonProcessingException,
            MalformedURLException {
        BasicCountry c;
        List<BasicCountry> countriesSummary = new ArrayList<>();
        
        DbConnection dbc = new DbConnection();
        int startRecord = 0;
        // set initial values for pagination
        Pagination page = new Pagination(pageSize, sortField, pageNumber);
        
        // get summary list
        try (Connection con = dbc.getConnection()) {
            String sqlQuery;

            sqlQuery = createCountrySummaryQuery(filter, page, startRecord);

            try (PreparedStatement ps = con.prepareStatement(sqlQuery)) {

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
        try (Connection con = dbc.getConnection()) {
            page.setTotalElements(getCountriesCount(con, getConditionalBlock(filter)));
            setNumberOfPages(page, con);
            
        }
        this.setPagePaths(page);
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

    private int getCountriesCount(Connection con, String sqlString) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM country c";
        sql = sql + sqlString;
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
        if (page.getTotalElements() % page.getPageSize() !=0){
            totalPages++;
        }
            
        page.setTotalPages(totalPages);

    }

    private void setPagePaths(Pagination page)
            throws UnsupportedEncodingException, JsonProcessingException, MalformedURLException {
        
        
        URL basePath = new URL("http://localhost:8080/countries/summary/");       
        URL firstPageURL;
        String previousPage;
        String nextPage;
        String lastPage;
        String sort = "?sortField=" + page.getSortBy();
        String pageSizeString = "&pageSize=" + page.getPageSize();

        if (page.getPageNumber() != 1) {
            
            String pageNumberFirst = "&pageNumber=1";
            String pageNumberPrevious = "&pageNumber=" + Integer.toString(page.getPageNumber()-1);
            String mainFirstPath = sort + pageSizeString + pageNumberFirst;

            firstPageURL = new URL(basePath, mainFirstPath);
            previousPage = basePath + sort + pageSizeString + pageNumberPrevious;

            page.setFirstPagePath(firstPageURL);
            page.setPreviousPagePath(previousPage);

        }

        if (page.getTotalPages() != page.getPageNumber()){
            String pageNumberNext = "&pageNumber=" + Integer.toString(page.getPageNumber()+1);
            String pageNumberLast = "&pageNumber=" + Integer.toString(page.getTotalPages());

            nextPage = basePath + sort + pageSizeString + pageNumberNext;
            lastPage = basePath + sort + pageSizeString + pageNumberLast;

            page.setNextPagePath(nextPage);
            page.setLastPagePath(lastPage);
        }

        


         
        
        
    }

    private String createCountrySummaryJoinBlock(Filter filter){
        String sqlLanguageJoin;
        String sqlCurrencyJoin;
        String sqlRegionalBlockJoin;
        String joinedConditions = "";

        if (filter.getLanguageFilterList() != null) {
            sqlLanguageJoin = " JOIN country_language cl ON c.id=cl.COUNTRY_ID";
            if (joinedConditions == ""){
                joinedConditions = sqlLanguageJoin;
            }else {
                joinedConditions = joinedConditions + sqlLanguageJoin;
            }
        }
        if (filter.getCurrencyFilterList() != null) {
            sqlCurrencyJoin = " JOIN country_currency cc ON c.id=cc.COUNTRY_ID";
            if (joinedConditions == ""){
                joinedConditions = sqlCurrencyJoin;
            }else {
                joinedConditions = joinedConditions + sqlCurrencyJoin;
            }
        }

        if (filter.getRegionalBlockFilterList() != null) {
            sqlRegionalBlockJoin = " JOIN country_regionalblock crb ON c.id=crb.COUNTRY_ID";
            if (joinedConditions == ""){
                joinedConditions = sqlRegionalBlockJoin;
            }else {
                joinedConditions = joinedConditions + sqlRegionalBlockJoin;
            };
        }
        return joinedConditions;
    }
    
    private String createRegionalBlockList(Filter filter){
        String sqlRegionalBlockJoinCondition = null;
        // String joinedConditions;

        if (filter.getRegionalBlockFilterList() != null) {
           
            String sqlRegionalBlockStart = " AND crb.REGIONALBLOCK_ID IN (";

            //set first value
            sqlRegionalBlockJoinCondition = sqlRegionalBlockStart + filter.getRegionalBlockFilterList().get(0).toString();
            //set remaining language ids 
            if (filter.getRegionalBlockFilterList().size()>1){
                for (int x =1; x<=filter.getCurrencyFilterList().size()-1; x++){
                    sqlRegionalBlockJoinCondition = sqlRegionalBlockJoinCondition + "," + filter.getRegionalBlockFilterList().get(x).toString();
                }
            }
            //close off the in block
            sqlRegionalBlockJoinCondition = sqlRegionalBlockJoinCondition + ")";

        }   
        return sqlRegionalBlockJoinCondition;
    }
    
    private String createCurrencyList(Filter filter){
        String sqlCurrencyJoinCondition = null;
        // String joinedConditions;

        if (filter.getCurrencyFilterList() != null) {
           
            String sqlCurrencyStart = " AND cc.CURRENCY_ID IN (";

            //set first value
            sqlCurrencyJoinCondition = sqlCurrencyStart + filter.getCurrencyFilterList().get(0).toString();
            //set remaining language ids 
            if (filter.getCurrencyFilterList().size()>1){
                for (int x =1; x<=filter.getCurrencyFilterList().size()-1; x++){
                    sqlCurrencyJoinCondition = sqlCurrencyJoinCondition + "," + filter.getCurrencyFilterList().get(x).toString();
                }
            }
            //close off the in block
            sqlCurrencyJoinCondition = sqlCurrencyJoinCondition + ")";

        }   
        return sqlCurrencyJoinCondition;
    }
    
    private String createLanguageList(Filter filter){
        String sqlLanguageJoinCondition = null;
        // String joinedConditions;

        if (filter.getLanguageFilterList() != null) {
           
            String sqlLanguageStart = " AND cl.LANGUAGE_ID IN (";

            //set first value
            sqlLanguageJoinCondition = sqlLanguageStart + filter.getLanguageFilterList().get(0).toString();
            //set remaining language ids 
            if (filter.getLanguageFilterList().size()>1){
                for (int x =1; x<=filter.getLanguageFilterList().size()-1; x++){
                    sqlLanguageJoinCondition = sqlLanguageJoinCondition + "," + filter.getLanguageFilterList().get(x).toString();
                }
            }
            //close off the in block
            sqlLanguageJoinCondition = sqlLanguageJoinCondition + ")";

        }   
        return sqlLanguageJoinCondition;
    }
    
    private String getConditionalBlock(Filter filter){
        String sqlCurrencyJoin;
        String sqlBasicCountryQuery = "";
        String sqlRegionalBlockJoin;
        String sqlLanguageJoinCondition;
        String sqlCurrencyJoinCondition;
        String sqlRegionalBlockJoinCondition;
        String joinDefinition;
        String joinedConditions = "";
        String sqlStringConditions = null;

        // gets the field and value relating to Country and checks the db for it
        if (filter.getCountryFilterField() != null) {
            sqlBasicCountryQuery = " WHERE lower(c." + filter.getCountryFilterField() + ") LIKE '%"
                    + filter.getCountryFilterValue().toLowerCase() + "%'";
        }

        joinDefinition = createCountrySummaryJoinBlock(filter);
        sqlLanguageJoinCondition = createLanguageList(filter);
        sqlCurrencyJoinCondition = createCurrencyList(filter);
        sqlRegionalBlockJoinCondition = createRegionalBlockList(filter);

        if (sqlLanguageJoinCondition != null){
            if(joinedConditions == ""){
                joinedConditions = sqlLanguageJoinCondition;
            }else {
            joinedConditions = joinedConditions + sqlLanguageJoinCondition;
            }
        }
        if (sqlCurrencyJoinCondition != null){
            if(joinedConditions == ""){
                joinedConditions = sqlCurrencyJoinCondition;
            }else {
            joinedConditions = joinedConditions + sqlCurrencyJoinCondition;
            }
        }
        if (sqlRegionalBlockJoinCondition != null){
            if(joinedConditions == ""){
                joinedConditions = sqlRegionalBlockJoinCondition;
            }else {
            joinedConditions = joinedConditions + sqlRegionalBlockJoinCondition;
            }
        }
        if (joinDefinition != null && joinDefinition.length() > 0){
            sqlStringConditions = joinDefinition;
        }

        if (sqlBasicCountryQuery.length() > 0){
            
            if (sqlStringConditions == null) {
                sqlStringConditions = sqlBasicCountryQuery;
            } else {
                sqlStringConditions = sqlStringConditions + sqlBasicCountryQuery;
            }
        } 

        if (joinedConditions != null && joinedConditions.length() > 0){
            if (sqlStringConditions == null) {
                sqlStringConditions = joinedConditions;
            } else {
                sqlStringConditions = sqlStringConditions + joinedConditions;
            }
        }
        return sqlStringConditions;
    }
    
    private String createCountrySummaryQuery(Filter filter, Pagination page, int startRecord) {

        startRecord = setStartRecord(page.getPageNumber(), page.getPageSize());

        String sqlStringConditions;
        String sqlQuery;
        
        String sqlStringStartGetCountry = "SELECT * FROM country c";
        String sqlStringOrderPaged = " ORDER BY " + page.getSortBy() + " LIMIT " + startRecord + "," + page.getPageSize() + ";";
 
        sqlStringConditions = getConditionalBlock(filter);
        sqlQuery = sqlStringStartGetCountry + sqlStringConditions + sqlStringOrderPaged;
        
        return sqlQuery;
    }
    
    private int setStartRecord(int pageNumber, int pageSize) {
        int startRecord = 0;
        if (pageNumber != 1){
            startRecord = ((pageNumber-1)*(pageSize));
        } 
        return startRecord;
    }

}