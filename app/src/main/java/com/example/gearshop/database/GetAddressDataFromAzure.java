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
    protected ResultSet doInBackground(String... sqlCommand) {
        ResultSet resultSet = null;
        try {
            Connection connection = DriverManager.getConnection(AzureConnectionString);
            Statement statement = connection.createStatement();
            if (sqlCommand[0].contains("SELECT")){
                resultSet = statement.executeQuery(sqlCommand[0]);
            }
            else{
                statement.execute(sqlCommand[0]);
            }
            while (true) {
                try {
                    assert resultSet != null;
                    if (!resultSet.next()) break;

                    Address newAddress = new Address(
                            resultSet.getInt("id"),
                            resultSet.getString("house_number"),
                            resultSet.getString("street"),
                            resultSet.getInt("province_id"));
                    AddressList.add(newAddress);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
