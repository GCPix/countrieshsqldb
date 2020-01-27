package com.countries.countriesAPI.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.countries.countriesAPI.models.RegionalBlock;
import com.db.DbConnection;

public class RegionalBlockDataAccess {
    public List<RegionalBlock> getRegionalBlocks() throws SQLException, ClassNotFoundException {
        List<RegionalBlock> regionalBlockList = null;
        DbConnection dbc = new DbConnection();
        
        try(Connection con = dbc.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM regionalblock;")){
            try(ResultSet rs = ps.executeQuery()){
                regionalBlockList = new ArrayList<>();
                while(rs.next()){
                    RegionalBlock rb = new RegionalBlock();
                    rb.setId(rs.getInt("id"));
                    rb.setName(rs.getString("name"));
                    rb.setAcronym(rs.getString("acronym"));
                    regionalBlockList.add(rb);
                }
                

            }
        }
        return regionalBlockList;
    }

    public RegionalBlock getRegionalBlock(int id) throws SQLException, ClassNotFoundException {
        RegionalBlock regionalBlock = null;
        DbConnection dbc = new DbConnection();
        Connection con = dbc.getConnection();

        try(PreparedStatement ps = con.prepareStatement("SELECT * FROM regionalblock WHERE id = " + id + ";")) {
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                	regionalBlock = new RegionalBlock();
                    regionalBlock.setId(rs.getInt("id"));
                    regionalBlock.setName(rs.getString("name"));
                    regionalBlock.setAcronym(rs.getString("acronym"));
                }
                
            }
        }
        return regionalBlock;
    }
    public RegionalBlock getRegionalBlock(int id, Connection con) throws SQLException, ClassNotFoundException {
        RegionalBlock regionalBlock;
      
        try(PreparedStatement ps = con.prepareStatement("SELECT * FROM regionalblock WHERE id = " + id + ";")) {
            try(ResultSet rs = ps.executeQuery()){
                rs.next();
                regionalBlock = new RegionalBlock();
                regionalBlock.setId(rs.getInt("id"));
                regionalBlock.setName(rs.getString("name"));
                regionalBlock.setAcronym(rs.getString("acronym"));
            }
        }
        return regionalBlock;
    }
    public int deleteRegionalBlock(int id) throws SQLException, ClassNotFoundException {
        DbConnection dbc = new DbConnection();
        Connection con = dbc.getConnection();
        int noRowsReturned;

        try(PreparedStatement ps = con.prepareStatement("DELETE FROM regionalblock WHERE id = " + id + ";")) {
            noRowsReturned = ps.executeUpdate();
            
            
        }
        return noRowsReturned;
    }

    public int updateRegionalBlock(int id, RegionalBlock regionalBlock) throws ClassNotFoundException, SQLException {
        DbConnection dbc = new DbConnection();
        Connection con = dbc.getConnection();
        int noRowsReturned;

        String sqlString = "UPDATE regionalblock SET acronym = '" 
        + regionalBlock.getAcronym() + "', name = '" 
        + regionalBlock.getName() + "' WHERE id = " + id + ";";
        try(PreparedStatement ps = con.prepareStatement(sqlString)) {
            noRowsReturned = ps.executeUpdate();
      
            
        }
        return noRowsReturned;
    }

    public int addRegionalBlock(RegionalBlock regionalBlock) throws SQLException, ClassNotFoundException {
        DbConnection dbc = new DbConnection();
        Connection con = dbc.getConnection();
        int id;
        String sqlString = "INSERT INTO regionalblock(acronym, name) VALUES('" + regionalBlock.getAcronym() + "', '" + regionalBlock.getName() + "');";
        
        try(PreparedStatement ps = con.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS)){
            ps.execute();
            try(ResultSet rs = ps.getGeneratedKeys()){
                rs.next();
                id = rs.getInt("id");
            } 
        }
        return id;
    }


}