package com.example.gearshop.controller;

import android.annotation.SuppressLint;

import com.example.gearshop.database.GetCategoryDataFromAzure;
import com.example.gearshop.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoryRepository {
    private final List<Category> CategoryList = new ArrayList<>();

    @SuppressLint("SimpleDateFormat")
    public CategoryRepository() {
        // Get Category List in Database
        GetCategoryDataFromAzure[] getCategoryDataInAzure = new GetCategoryDataFromAzure[1];
        getCategoryDataInAzure[0] = new GetCategoryDataFromAzure();
        getCategoryDataInAzure[0].execute(
                "SELECT *\n" +
                        "FROM product_category\n"
        );
        System.out.println("Async Task get Category is running");

        try {
            getCategoryDataInAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Async Task get Category ended");



        if (getCategoryDataInAzure[0].getCategoryList() != null){
            CategoryList.addAll(getCategoryDataInAzure[0].getCategoryList());
        }
    }

    public List<Category> getCategories() {
        return CategoryList;
    }

    public int generateNewCategoryId() {
        return CategoryList.size() + 1;
    }
    
}
