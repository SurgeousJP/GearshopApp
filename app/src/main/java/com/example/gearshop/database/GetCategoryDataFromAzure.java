package com.example.gearshop.database;

import com.example.gearshop.model.Category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetCategoryDataFromAzure extends AzureSQLDatabase{
    private List<Category> CategoryList;

    public GetCategoryDataFromAzure() {
        this.CategoryList = new ArrayList<>();
    }
    public List<Category> getCategoryList(){
        return CategoryList;
    }

    @Override
    protected void handleResultSetItem(ResultSet resultSet){
        try{
            Category newCategory = new Category(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"));

            CategoryList.add(newCategory);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
