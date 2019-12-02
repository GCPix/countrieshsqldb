package com.countries.countriesAPI.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.countries.countriesAPI.models.RegionalBlock;

public class RegionalBlockDataAccess {
    public List<RegionalBlock> getRegionalBlocks(Connection con) throws SQLException {
        List<RegionalBlock> regionalBlockList = null;
        try(PreparedStatement ps = con.prepareStatement("SELECT * FROM regionalblock;")){
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

    public RegionalBlock getRegionalBlock(int id, Connection con) throws SQLException {
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
}