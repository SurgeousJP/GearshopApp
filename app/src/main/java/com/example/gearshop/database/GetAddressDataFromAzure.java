package com.example.gearshop.database;

import com.example.gearshop.model.Address;
import com.example.gearshop.model.Category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetAddressDataFromAzure extends AzureSQLDatabase{
    private List<Address> AddressList;
    public GetAddressDataFromAzure() {
        this.AddressList = new ArrayList<>();
    }
    public List<Address> getAddressList(){
        return AddressList;
    }

    @Override
    protected void handleResultSetItem(ResultSet resultSet){
        try{
            Address newAddress = new Address(
                    resultSet.getInt("id"),
                    resultSet.getString("house_number"),
                    resultSet.getString("street"),
                    resultSet.getInt("province_id"));
            AddressList.add(newAddress);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
