package com.example.gearshop.database;

import com.example.gearshop.model.Discount;
import com.example.gearshop.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
                    Discount newDiscount = new Discount(
                            resultSet.getInt("discount_id"),
                            resultSet.getString("discount_name"),
                            resultSet.getInt("discount_percentage"),
                            resultSet.getDate("start_date_utc"),
                            resultSet.getDate("end_date_utc"));

                    Product newProduct = new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("image_url"),
                            resultSet.getString("description"),
                            resultSet.getString("specs"),
                            resultSet.getDouble("price"),
                            resultSet.getBoolean("status"),
                            resultSet.getInt("category_id"), newDiscount);
                    ProductList.add(newProduct);
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
