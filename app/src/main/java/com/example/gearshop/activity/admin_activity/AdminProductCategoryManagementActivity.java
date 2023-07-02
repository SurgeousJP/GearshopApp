package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.CategoryListViewAdapter;
import com.example.gearshop.model.Category;
import com.example.gearshop.repository.CategoryRepository;
import com.example.gearshop.utility.ActivityStartManager;

import java.util.List;

public class AdminProductCategoryManagementActivity extends AppCompatActivity {
    private ListView CategoryListView;
    private View CustomerManagementView;
    private View OrderManagementView;
    private View AccountManagementView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_mainscreen_management);
        CategoryListView = findViewById(R.id.listview_category_product_management);

        //Get Categories
        CategoryRepository categoryRepository = new CategoryRepository();
        List<Category> categoriesList = categoryRepository.getCategories();
        Category[] categories = categoriesList.toArray(new Category[0]);

        // Set up Category ListView
        CategoryListView.setAdapter(new
                CategoryListViewAdapter(this, R.layout.list_item_category, categories));

        CategoryListView.setOnItemClickListener((parent, view, position, id) -> {
            // Intent to start products by category page
            Intent intent = new Intent(getBaseContext(), AdminProductsInCategoryManagement.class);
            intent.putExtra("clickedCategory", categories[position]);
            startActivity(intent);
        });

        CustomerManagementView = findViewById(R.id.product_customer_management);
        CustomerManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminCustomerManagementActivity.class);
        });
        OrderManagementView = findViewById(R.id.product_order_management);
        OrderManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminOrderManagementActivity.class);
        });
        AccountManagementView = findViewById(R.id.product_other_management);
        AccountManagementView.setOnClickListener(view -> {

        });
    }
}