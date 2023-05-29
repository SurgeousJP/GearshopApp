package com.example.gearshop.repository;

import android.annotation.SuppressLint;

import com.example.gearshop.database.GetProductDataFromAzure;
import com.example.gearshop.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductRepository {
    private final String SQL_GET_PRODUCTS_BASE = "SELECT product.*,\n" +
            "\t   discount.id AS discount_id, discount.name AS discount_name, \n" +
            "\t   discount_percentage, start_date_utc, end_date_utc\n" +
            "FROM product\n" +
            "JOIN product_category ON product.category_id = product_category.id\n" +
            "JOIN discount_applied_category ON product_category.id = discount_applied_category.category_id\n" +
            "JOIN discount ON discount.id = discount_applied_category.discount_id\n";
    private final List<Product> ProductList = new ArrayList<>();
    private final GetProductDataFromAzure[] getProductDataInAzure;

    @SuppressLint("SimpleDateFormat")
    public ProductRepository() {
        // Get Product List in Database
        getProductDataInAzure = new GetProductDataFromAzure[2];


    }

    public List<Product> getProductsByCategoryId(String categoryId){
        List<Product> productsByCategory = new ArrayList<>();
        String sql = SQL_GET_PRODUCTS_BASE +
                "WHERE product.category_id = " + categoryId;

        getProductDataInAzure[1] = new GetProductDataFromAzure();
        getProductDataInAzure[1].execute(sql);

        try {
            getProductDataInAzure[1].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (getProductDataInAzure[1].getProductList() != null){
            productsByCategory.addAll(getProductDataInAzure[1].getProductList());
        }

        return productsByCategory;
    }

    public List<Product> getProducts() {
        getProductDataInAzure[0] = new GetProductDataFromAzure();
        getProductDataInAzure[0].execute(SQL_GET_PRODUCTS_BASE);

        try {
            getProductDataInAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (getProductDataInAzure[0].getProductList() != null){
            ProductList.addAll(getProductDataInAzure[0].getProductList());
        }

        return ProductList;
    }

    public int generateNewProductId() {
        return ProductList.size() + 1;
    }
}
