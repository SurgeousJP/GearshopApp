package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.gearshop.R;
import com.example.gearshop.fragment.SearchNotFoundFragment;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.ActivityStartManager;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchNotFoundFragment.OnFragmentViewCreatedListener {
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
            searchNotFoundFragment.setOnFragmentViewCreatedListener(this);
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

    @Override
    public void onFragmentViewCreated(View fragmentView) {
        // Apply constraints to the fragmentView (root view)
        ConstraintLayout searchConstraintLayout = findViewById(R.id.search_constraint_layout);
        ConstraintLayout searchInputLayout = findViewById(R.id.search_input);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) fragmentView.getLayoutParams();
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToBottom = searchInputLayout.getId();
        fragmentView.setLayoutParams(layoutParams);
    }
}