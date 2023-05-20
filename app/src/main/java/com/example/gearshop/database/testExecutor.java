package com.example.gearshop.database;

import com.example.gearshop.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class testExecutor implements Callable<List<Product>> {
    final String AzureConnectionString =
            "jdbc:jtds:sqlserver://dozlapsoutheastasia.database.windows.net:1433;" +
                    "databaseName=dozlapsoutheastasiaDB;" +
                    "user=sqladmin@dozlapsoutheastasia;" +
                    "password=CodingProject123@;" +
                    "encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;" +
                    "loginTimeout=30;ssl=request"
            ;
    private final String sqlCommand;
    private List<Product> ProductList;
    public List<Product> getProductList(){
        return ProductList;
    }
    public testExecutor(String sqlCommand){
        this.sqlCommand = sqlCommand;
        ProductList = new ArrayList<>();
    }
    @Override
    public List<Product> call() throws Exception {
        ResultSet resultSet = null;
        // Perform your background task here
        try {
            Connection connection = DriverManager.getConnection(AzureConnectionString);
            Statement statement = connection.createStatement();
            if (sqlCommand.contains("SELECT")){
                resultSet = statement.executeQuery(sqlCommand);
            }
            else{
                statement.execute(sqlCommand);
            }
            while (true) {
                try {
                    assert resultSet != null;
                    if (!resultSet.next()) break;
                    ProductList.add(new Product(resultSet.getInt("id"),
                            resultSet.getString("name"), resultSet.getString("image_url"),
                            resultSet.getString("description"), resultSet.getString("specs"),
                            resultSet.getDouble("price"),
                            resultSet.getBoolean("status"), resultSet.getInt("category_id")));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            statement.close();
            connection.close();
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
        return ProductList; // Replace with the appropriate result
    }
    // Call the task using an ExecutorService
}



