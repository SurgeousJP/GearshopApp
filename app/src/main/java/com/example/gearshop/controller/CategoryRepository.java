package com.example.gearshop.controller;

import android.annotation.SuppressLint;

import com.example.gearshop.database.GetCategoryDataFromAzure;
import com.example.gearshop.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoryRepository {
    private final String BASE_GET_CATEGORY_SQL = "SELECT *\n" +
            "FROM product_category\n";
    private final List<Category> CategoryList = new ArrayList<>();
    private final GetCategoryDataFromAzure[] getCategoryDataInAzure;


    @SuppressLint("SimpleDateFormat")
    public CategoryRepository() {
        // Get Category List in Database
        getCategoryDataInAzure = new GetCategoryDataFromAzure[2];
    }

    public List<Category> getCategories() {
        getCategoryDataInAzure[0] = new GetCategoryDataFromAzure();
        getCategoryDataInAzure[0].execute(BASE_GET_CATEGORY_SQL);

        try {
            getCategoryDataInAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (getCategoryDataInAzure[0].getCategoryList() != null){
            CategoryList.addAll(getCategoryDataInAzure[0].getCategoryList());
        }

        return CategoryList;
    }

    public Category getCategoryById(String categoryId){
        getCategoryDataInAzure[1] = new GetCategoryDataFromAzure();
        getCategoryDataInAzure[1].execute(BASE_GET_CATEGORY_SQL +
                        "WHERE id = " + categoryId
        );

        try {
            getCategoryDataInAzure[1].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (getCategoryDataInAzure[1].getCategoryList() != null){
            return getCategoryDataInAzure[1].getCategoryList().get(0);
        }

        return null;
    }

    public int generateNewCategoryId() {
        return CategoryList.size() + 1;
    }
    
}
