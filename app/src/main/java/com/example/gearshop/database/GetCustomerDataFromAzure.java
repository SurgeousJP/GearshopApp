package com.example.gearshop.database;

import com.example.gearshop.model.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetCustomerDataFromAzure extends AzureSQLDatabase{
    private List<Customer> CustomerList;
    public GetCustomerDataFromAzure() {
        this.CustomerList = new ArrayList<>();
    }
    public List<Customer> getCustomerList(){
        return CustomerList;
    }
    @Override
    protected void handleResultSetItem(ResultSet resultSet){
        try{
            Customer newCustomer = new Customer(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("gender"),
                    resultSet.getString("phone_number"),
                    resultSet.getDate("date_of_birth"),
                    resultSet.getInt("address_id"));
            CustomerList.add(newCustomer);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
