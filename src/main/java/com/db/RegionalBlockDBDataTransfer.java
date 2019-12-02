package com.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.countries.countriesAPI.models.Country;
import com.countries.countriesAPI.models.RegionalBlock;

public class RegionalBlockDBDataTransfer {
    public void populateRegionalBlockTable(List<Country> countryList, Connection con) throws SQLException, IOException {
        try(InputStream is = getClass().getResourceAsStream("sqlScripts/populateRegionalBlockTable.sql")){
            try (Scanner sc = new Scanner(is)){
                StringBuffer sb = new StringBuffer();
                while(sc.hasNext()){
                    sb.append(sc.nextLine());
                }
                
            try (PreparedStatement ps = con.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS)){
                for(Country c: countryList){
                    for(RegionalBlock rb: c.getRegionalBlocks()){
                        ps.setString(1, rb.getAcronym());
                        ps.setString(2, rb.getName());
                        ps.setString(3, rb.getName());
                        ps.execute();
                        if(ps.getUpdateCount()>0){
                            try(ResultSet rs = ps.getGeneratedKeys()){
                                rs.next();
                                rb.setId(rs.getInt("id"));
                            }
                        }
                    }
                }
            }
            }
        }
    }

    public void populateCountryRBRelationshipTable(List<Country> countryList, List<RegionalBlock> regionalBlockList, Connection con) throws IOException, SQLException {
        StringBuffer sb;
        try(InputStream is = getClass().getResourceAsStream("sqlScripts/populateCountryRBTable.sql")){
            try(Scanner sc = new Scanner(is)){
                sb = new StringBuffer();
                while(sc.hasNext()){
                    sb.append(sc.nextLine());
                }
            }
        try(PreparedStatement ps = con.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS)){
            for(Country c: countryList){
                for(RegionalBlock clrb: c.getRegionalBlocks()){
                    for(RegionalBlock rb: regionalBlockList){
                        if(clrb.getName().equalsIgnoreCase(rb.getName())){
                            ps.setInt(1, c.getId());
                            ps.setInt(2, rb.getId());
                            ps.execute();
                        }
                        
                }
                

                }
            }
        }
        }
    }

	public List<RegionalBlock> getRegionalBlockList(Connection connection) throws SQLException {
       
            ArrayList<RegionalBlock> regionalBlockList = new ArrayList<>();

            try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM regionalblock");){
                
               try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()){
                        RegionalBlock rb = new RegionalBlock();
                        rb.setId(rs.getInt("id"));
                        rb.setAcronym(rs.getString("acronym"));
                        rb.setName(rs.getString("name"));
                        regionalBlockList.add(rb);
                    }
                }
            
            }
    
            return regionalBlockList;
       
	}

}