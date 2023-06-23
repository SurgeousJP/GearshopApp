package com.example.gearshop.activity.customer_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.CategoryGridAdapter;
import com.example.gearshop.adapter.ProductRecyclerAdapter;
import com.example.gearshop.model.Product;
import com.example.gearshop.repository.CategoryRepository;
import com.example.gearshop.model.Category;
import com.example.gearshop.repository.CustomerProductRepository;
import com.example.gearshop.utility.ActivityStartManager;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RelativeLayout CartIconLayout;
    private RelativeLayout OptionsLayout;
    private RelativeLayout ReturnHomeLayout;
    private RelativeLayout HomeItem;
    private RelativeLayout CategoryItem;
    private RelativeLayout SearchItem;
    private RelativeLayout AccountItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);


        System.out.println("In Home");

        //Get Random 10 Products
        CustomerProductRepository customerProductRepository = new CustomerProductRepository();
        List<Product> productList = customerProductRepository.getRandomProducts(10);
        Product[] productArray = productList.toArray(new Product[0]);

        // Get Categories
        CategoryRepository categoryRepository = new CategoryRepository();
        List<Category> categoryList = categoryRepository.getCategories();
        Category[] categoryArray = categoryList.toArray(new Category[0]);

        // Populate Category GridView
        GridView categoryGridView = (GridView) findViewById(R.id.show_grid_category);
        categoryGridView.setAdapter(new CategoryGridAdapter(getBaseContext(), categoryList));
        categoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent to start products by category page
                Intent intent = new Intent(getBaseContext(), ProductsInCategoryActivity.class);
                intent.putExtra("categoryId", categoryArray[position].getID());
                intent.putExtra("categoryName", categoryArray[position].getName());
                startActivity(intent);
            }
        });

        RecyclerView productRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_category_main);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        productRecyclerView.setLayoutManager(layoutManager);
        productRecyclerView.setAdapter(new ProductRecyclerAdapter(productList, getBaseContext()));

        RelativeLayout rlMoreCategories = (RelativeLayout)findViewById(R.id.more_category);
        rlMoreCategories.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), CategoryActivity.class);
        });

        CartIconLayout = findViewById(R.id.cart_layout);
        CartIconLayout.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), CartActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getBaseContext().startActivity(intent);
        });

        OptionsLayout = findViewById(R.id.more_info_order_detail);
        OptionsLayout.setOnClickListener(this::showPopupMenu);

        ReturnHomeLayout = findViewById(R.id.escape);
        ReturnHomeLayout.setOnClickListener(view -> {
            // do nothing since in home
        });

        CategoryItem = findViewById(R.id.category_item_category_detail);
        CategoryItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), CategoryActivity.class);
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
                    Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.faq_item) {
                    Intent intent = new Intent(HomeActivity.this, FAQActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
