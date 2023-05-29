package com.example.gearshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.adapter.CategoryListViewAdapter;
import com.example.gearshop.controller.CategoryRepository;
import com.example.gearshop.model.Category;

public class CategoryActivity extends AppCompatActivity {

    private RelativeLayout CartIconLayout;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    private RelativeLayout HomeItem;
    private RelativeLayout CategoryItem;
    private RelativeLayout SearchItem;
    private RelativeLayout AccountItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.category_layout);


        //Get Categories
        CategoryRepository categoryRepository = new CategoryRepository();
        Category[] categories = categoryRepository.getCategories().toArray(new Category[0]);

        // Set up Category ListView
        ListView lvCategories = (ListView)findViewById(R.id.listview_category_layout);
        lvCategories.setAdapter(new
                CategoryListViewAdapter(this, R.layout.listview_category, categories));

        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent to start products by category page
                Intent intent = new Intent(getBaseContext(), ProductsInCategoryActivity.class);
                intent.putExtra("categoryId", categories[position].getID());
                startActivity(intent);
            }
        });

        CartIconLayout = findViewById(R.id.cart_layout);
        CartIconLayout.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), CartActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getBaseContext().startActivity(intent);
        });

        MoreInformationLayout = findViewById(R.id.more_info_order_detail);
        MoreInformationLayout.setOnClickListener(view -> {

        });

        EscapeLayout = findViewById(R.id.escape);
        EscapeLayout.setOnClickListener(view -> {

        });

        HomeItem = findViewById(R.id.home_item_category_detail);
        HomeItem.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), ProductsInCategoryActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getBaseContext().startActivity(intent);
            finish();
        });

        CategoryItem = findViewById(R.id.category_item_category_detail);
        CategoryItem.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), CategoryActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getBaseContext().startActivity(intent);
        });

        SearchItem = findViewById(R.id.search_item_category_detail);
        SearchItem.setOnClickListener(view -> {

        });

        AccountItem = findViewById(R.id.account_item_category_detail);
        AccountItem.setOnClickListener(view -> {
        });
    }
}