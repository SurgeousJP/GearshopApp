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
    protected ResultSet doInBackground(String... sqlCommand) {
        ResultSet resultSet = null;
        try {
            Connection connection = DriverManager.getConnection(AzureConnectionString);
            Statement statement = connection.createStatement();
            if (sqlCommand[0].contains("SELECT")) {
                resultSet = statement.executeQuery(sqlCommand[0]);
            } else {
                statement.execute(sqlCommand[0]);
            }
            while (true) {
                try {
                    assert resultSet != null;
                    if (!resultSet.next()) break;

                    admin = new Admin(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
