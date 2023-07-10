package com.example.gearshop.activity.customer_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gearshop.R;
import com.example.gearshop.adapter.CategoryListViewAdapter;
import com.example.gearshop.model.Category;
import com.example.gearshop.repository.CategoryRepository;
import com.example.gearshop.utility.ActivityStartManager;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RelativeLayout CartIconLayout;
    private RelativeLayout OptionsLayout;
    private RelativeLayout ReturnHomeLayout;
    private RelativeLayout HomeItem;
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

        lvCategories.setOnItemClickListener((parent, view, position, id) -> {
            // Intent to start products by category page
            Intent intent = new Intent(getBaseContext(), ProductsInCategoryActivity.class);
            intent.putExtra("categoryId", categories[position].getID());
            intent.putExtra("categoryName", categories[position].getName());
            startActivity(intent);
        });

        CartIconLayout = findViewById(R.id.cart_layout);
        CartIconLayout.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), CartActivity.class);
        });

        OptionsLayout = findViewById(R.id.more_info_order_detail);
        OptionsLayout.setOnClickListener(this::showPopupMenu);

        ReturnHomeLayout = findViewById(R.id.escape);
        ReturnHomeLayout.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), HomeActivity.class);
        });

        HomeItem = findViewById(R.id.home_item_category_detail);
        HomeItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), HomeActivity.class);
        });

        SearchItem = findViewById(R.id.search_item_category_detail);
        SearchItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), SearchActivity.class);
        });

        AccountItem = findViewById(R.id.account_item_category_detail);
        AccountItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AccountActivity.class);
        });
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.dots_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.logout_item) {
                    Intent intent = new Intent(CategoryActivity.this, SignInActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.faq_item) {
                    Intent intent = new Intent(CategoryActivity.this, FAQActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
