package com.example.gearshop.database;

import com.example.gearshop.model.Discount;
import com.example.gearshop.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetProductDataFromAzure extends AzureSQLDatabase{
    private List<Product> ProductList;
    public int CategoryID = 0;
    public GetProductDataFromAzure() {
        this.ProductList = new ArrayList<>();
    }

    public List<Product> getProductList(){
        return ProductList;
    }
    public int getCategoryID() {
        return CategoryID;
    }
    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }
    @Override
    protected ResultSet doInBackground(String... sqlCommand) {
        ResultSet resultSet = null;
        try {
            Connection connection = DriverManager.getConnection(AzureConnectionString);
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sqlCommand[0]);
            if (sqlCommand[0].contains("SELECT")){
                if (sqlCommand[0].contains("?") && getCategoryID() != 0){
                    statement.setInt(1, getCategoryID());
                }
                resultSet = statement.executeQuery();
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
