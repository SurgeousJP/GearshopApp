package com.example.gearshop.database;

import com.example.gearshop.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetNumberOfProductsInEachCategoryFromAzure extends AzureSQLDatabase{
    private List<Category> CategoryList;
    private List<Integer> NumberOfProductsInEachCategory;

    public GetNumberOfProductsInEachCategoryFromAzure() {
        this.CategoryList = new ArrayList<>();
        NumberOfProductsInEachCategory = new ArrayList<>();
    }
    public List<Category> getCategoryList(){
        return CategoryList;
    }
    public List<Integer> getNumberOfProductsInEachCategory(){
        return NumberOfProductsInEachCategory;
    }

    @Override
    protected void handleResultSetItem(ResultSet resultSet){
        try{
            Category newCategory = new Category(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"));

            NumberOfProductsInEachCategory.add(resultSet.getInt("number_of_products"));

            CategoryList.add(newCategory);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
