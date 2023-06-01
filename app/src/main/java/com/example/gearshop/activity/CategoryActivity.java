package com.example.gearshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.CategoryGridAdapter;
import com.example.gearshop.adapter.CategoryListViewAdapter;
import com.example.gearshop.model.Category;
import com.example.gearshop.repository.CategoryRepository;
import com.example.gearshop.utility.ActivityStartManager;

import java.util.List;

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
        List<Category> categoriesList = categoryRepository.getCategories();
        Category[] categories = categoriesList.toArray(new Category[0]);

        // Set up Category ListView
        ListView lvCategories = (ListView)findViewById(R.id.listview_category_layout);
        lvCategories.setAdapter(new
                CategoryListViewAdapter(this, R.layout.list_item_category, categories));

        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent to start products by category page
                Intent intent = new Intent(getBaseContext(), ProductsInCategoryActivity.class);
                intent.putExtra("categoryId", categories[position].getID());
                startActivity(intent);
            }
        });

        RelativeLayout rlMoreCategories = (RelativeLayout)findViewById(R.id.more_category);
        rlMoreCategories.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), CategoryActivity.class);
        });

        CartIconLayout = findViewById(R.id.cart_layout);
        CartIconLayout.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), CartActivity.class);
        });

        MoreInformationLayout = findViewById(R.id.more_info_order_detail);
        MoreInformationLayout.setOnClickListener(view -> {

        });

        EscapeLayout = findViewById(R.id.escape);
        EscapeLayout.setOnClickListener(view -> {

        });

        HomeItem = findViewById(R.id.home_item_category_detail);
        HomeItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), HomeActivity.class);
        });
//        CategoryItem = findViewById(R.id.category_item_category_detail);
//        CategoryItem.setOnClickListener(view -> {
//            ActivityStartManager.startTargetActivity(getBaseContext(), CategoryActivity.class);
//        });
        SearchItem = findViewById(R.id.search_item_category_detail);
        SearchItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), SearchActivity.class);
        });
        AccountItem = findViewById(R.id.account_item_category_detail);
        AccountItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AccountActivity.class);
        });
    }
}
