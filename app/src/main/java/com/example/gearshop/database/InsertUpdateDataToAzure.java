package com.example.gearshop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

<<<<<<<< HEAD:app/src/main/java/com/example/gearshop/database/SQLCommandExecutor.java
public class SQLCommandExecutor extends AzureSQLDatabase {
========
public class InsertUpdateDataToAzure extends AzureSQLDatabase {
>>>>>>>> OrderBranch:app/src/main/java/com/example/gearshop/database/InsertUpdateDataToAzure.java

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
