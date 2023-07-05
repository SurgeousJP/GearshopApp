package com.example.gearshop.database;

import com.example.gearshop.model.Admin;
import com.example.gearshop.model.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetAdminDataFromAzure extends AzureSQLDatabase {
    private Admin admin;

    public GetAdminDataFromAzure() {
    }

    public Admin getAdmin() {
        return admin;
    }

    @Override
    protected void handleResultSetItem(ResultSet resultSet){
        try{
            admin = new Admin(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
