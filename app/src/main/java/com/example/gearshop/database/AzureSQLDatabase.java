package com.example.gearshop.database;

import android.os.AsyncTask;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class AzureSQLDatabase extends AsyncTask<String, Void, ResultSet> {
    final String AzureConnectionString =
            "jdbc:jtds:sqlserver://dozlapsoutheastasia.database.windows.net:1433;" +
            "databaseName=dozlapsoutheastasiaDB;" +
            "user=sqladmin@dozlapsoutheastasia;" +
            "password=CodingProject123@;" +
            "encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;" +
            "loginTimeout=30;ssl=request"
            ;
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    @Override
    protected void onPostExecute(ResultSet result) {

    }
}
