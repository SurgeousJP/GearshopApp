package com.example.gearshop.database;

import com.example.gearshop.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetProductDataFromAzure extends AzureSQLDatabase{
    private List<Product> ProductList;
    public GetProductDataFromAzure() {
        this.ProductList = new ArrayList<>();
    }
    public List<Product> getProductList(){
        return ProductList;
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
