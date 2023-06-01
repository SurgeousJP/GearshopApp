package com.example.gearshop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertDataToAzure extends AzureSQLDatabase {

    @Override
    protected ResultSet doInBackground(String... SqlCommand) {
        try {
            Connection connection = DriverManager.getConnection(AzureConnectionString);
            Statement statement = connection.createStatement();

            statement.execute(SqlCommand[0]);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
