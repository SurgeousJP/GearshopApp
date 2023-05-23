package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.gearshop.R;
import com.example.gearshop.adapter.SearchNotFoundFragment;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.ActivityStartManager;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private List<Product> ProductList;
    private View SearchIconView;
    private EditText SearchEditText;
    private RelativeLayout CartIconLayout;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    private RelativeLayout HomeItem;
    private RelativeLayout CategoryItem;
    private RelativeLayout AccountItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        ProductList = new ArrayList<>();
        SearchEditText = findViewById(R.id.search_text);
        SearchIconView = findViewById(R.id.search_icon);
        SearchIconView.setOnClickListener(view -> {
            String searchText = SearchEditText.getText().toString();
            SearchNotFoundFragment searchNotFoundFragment = new SearchNotFoundFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.search_constraint_layout, searchNotFoundFragment);
            fragmentTransaction.commit();
        });


        CartIconLayout = findViewById(R.id.cart_order_detail);
        CartIconLayout.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), CartActivity.class);
        });

        MoreInformationLayout = findViewById(R.id.more_info_order_detail);
        MoreInformationLayout.setOnClickListener(view -> {

        });

        EscapeLayout = findViewById(R.id.escape_order_detail);
        EscapeLayout.setOnClickListener(view -> {

        });

        HomeItem = findViewById(R.id.home_item_category_detail);
        HomeItem.setOnClickListener(view -> {

        });
        CategoryItem = findViewById(R.id.category_item_category_detail);
        CategoryItem.setOnClickListener(view -> {

        });
        AccountItem = findViewById(R.id.account_item_category_detail);
        AccountItem.setOnClickListener(view -> {
        });
    }
}